var solicitudData1;

async function ObtenerSolicitudes(){
	const response = await fetch("/solicitudAccionMejoras/generar/listarSolicitudes");
	const responseJson = await response.json();
	return responseJson;
}

function ListarSolicitudes(solicitudData){
	$("#tablaSolicitudes tbody").html("");
	if(solicitudData.length > 0){
		solicitudData.forEach((solicitud) => {
			
			
			fetch(`/solicitudAccionMejoras/generar/buscarInforme/${solicitud.numSoli}`, {
				method: "GET",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					$("#tablaSolicitudes tbody").append(
				$("<tr>").append(
					$('<th class="text-center">').text(solicitud.numSoli),
					$('<td class="text-center">').text(solicitud.fecSoli),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-solicitante">')
						.data("dataSolicitante", solicitud.solicitante).append(
							$('<i class="bi bi-person-arms-up">')
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-responsable">')
						.data("dataResponsable", solicitud.responsable).append(
							$('<i class="bi bi-person-arms-up">')
						)
					),
					$('<td class="text-center">').append(
						$(`<a class="btn btn-outline-danger" href="/solicitudAccionMejoras/generar/verSolicitud/${solicitud.numSoli}" target="_blank">`)
						.append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					),
					$('<td class="text-center">').text(solicitud.estSoli),
					$('<td class="text-center">').append(
						$(`<a class="btn btn-outline-danger" href="/solicitudAccionMejoras/generar/verInforme/${solicitud.numSoli}" target="_blank">`)
						.append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					)
				)
			)
				} else {
					
			
			$("#tablaSolicitudes tbody").append(
				$("<tr>").append(
					$('<th class="text-center">').text(solicitud.numSoli),
					$('<td class="text-center">').text(solicitud.fecSoli),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-solicitante">')
						.data("dataSolicitante", solicitud.solicitante).append(
							$('<i class="bi bi-person-arms-up">')
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-responsable">')
						.data("dataResponsable", solicitud.responsable).append(
							$('<i class="bi bi-person-arms-up">')
						)
					),
					$('<td class="text-center">').append(
						$(`<a class="btn btn-outline-danger" href="/solicitudAccionMejoras/generar/verSolicitud/${solicitud.numSoli}" target="_blank">`)
						.append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					),
					$('<td class="text-center">').text(solicitud.estSoli),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-secondary" disabled>').append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					)
				)
			)
				}
			})
		})
	} else {
		$("#tablaSolicitudes tbody").append(
			$("<tr>").append(
				$('<th colspan="7" class="text-center">').text("No se encontraron solicitudes")
			)
		)
	}
}

$(document).on("click", "#btn-nueva-solicitud", function() {
	$("#buscarSolicitante").val("");
	$("#tablaSolicitantes tbody").html("");
	$("#tablaSolicitantes tbody").append(
		$("<tr>").append(
			$('<th colspan="2" class="text-center">').text("No se encontraron Solicitantes")
		)
	);
	$("#verSolicitante").val("");
	$("#verEmailTrab").val("");
	$("#verTelTrab").val("");
	$("#buscarResponsable").val("");
	$("#tablaResponsables tbody").html("");
	$("#tablaResponsables tbody").append(
		$("<tr>").append(
			$('<th colspan="2" class="text-center">').text("No se encontraron Responsables del Ánalisis")
		)
	);
	$("#verResponsable").val("");
	$("#verEmailResponsable").val("");
	$("#verTelResponsable").val("");
	$("#nomProceso").val("");
	$("#fuenteSam").val(-1);
	$("#otros").val("");
	$("#otros").prop("disabled", true);
	$("#descripcionNoConformidad").val("");
	$("#causasNoConformidad").val("");
	$("#accionesATomar").val("");
	$("#plazoDias").val(0);
	$("#msjError").html("").hide();
	
	$("#modal-nueva-solicitud").modal("show");
})

$(document).on("input", "#buscarSolicitante", function() {
	const busqueda = $("#buscarSolicitante").val();
	fetch(`/solicitudAccionMejoras/generar/buscar/solicitante/${busqueda}`, {
		method: "GET",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		$("#tablaSolicitantes tbody").html("");
		if(responseJson.length > 0){
			responseJson.forEach((solicitante) => {
				$("#tablaSolicitantes tbody").append(
					$("<tr>").append(
						$('<td class="text-center">').text(solicitante.nomTrab + " " + solicitante.apeTrab),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-warning btn-seleccionar-solicitante">')
							.data("dataSolicitante", solicitante).append(
								$('<i class="bi bi-person-check">')
							)
						)
					)
				)
			})
		} else {
			$("#tablaSolicitantes tbody").append(
				$("<tr>").append(
					$('<th colspan="2" class="text-center">').text("No se encontraron solicitantes")
				)
			)
		}
	})
})

$(document).on("click", ".btn-seleccionar-solicitante", function() {
	const solicitante = $(this).data("dataSolicitante");
	
	$("#codSolicitante").val(solicitante.codTrab);
	$("#verSolicitante").val(solicitante.nomTrab + " " + solicitante.apeTrab);
	$("#verEmailTrab").val(solicitante.emailTrab);
	$("#verTelTrab").val(solicitante.telTrab);
})

$(document).on("input", "#buscarResponsable", function() {
	const busqueda = $("#buscarResponsable").val();
	fetch(`/solicitudAccionMejoras/generar/buscar/responsable/${busqueda}`, {
		method: "GET",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		$("#tablaResponsables tbody").html("");
		if(responseJson.length > 0){
			responseJson.forEach((responsable) => {
				$("#tablaResponsables tbody").append(
					$("<tr>").append(
						$('<td class="text-center">').text(responsable.nomTrab + " " + responsable.apeTrab),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-warning btn-seleccionar-responsable">')
							.data("dataResponsable", responsable).append(
								$('<i class="bi bi-person-check">')
							)
						)
					)
				)
			})
		} else {
			$("#tablaResponsables tbody").append(
				$("<tr>").append(
					$('<th colspan="2" class="text-center">').text("No se encontraron responsables")
				)
			)
		}
	})
})

$(document).on("click", ".btn-seleccionar-responsable", function() {
	const responsable = $(this).data("dataResponsable");
	
	$("#codResponsable").val(responsable.codTrab);
	$("#verResponsable").val(responsable.nomTrab + " " + responsable.apeTrab);
	$("#verEmailResponsable").val(responsable.emailTrab);
	$("#verTelResponsable").val(responsable.telTrab);
})

$(document).on("click", "#fuenteSam", function() {
	const fuenteSam = $("#fuenteSam").val();
	
	if(fuenteSam == "Otros"){
		$("#otros").prop("disabled", false);
		$("#otros").val("");
	} else {
		$("#otros").prop("disabled", true);
		$("#otros").val("");
	}
})

$(document).on("click", "#btn-guardar-solicitud", function() {
	const solicitud = {
		numSoli: 0,
		fecSoli: "",
		solicitante: {
			codTrab: $("#codSolicitante").val()
		},
		nomProceso: $("#nomProceso").val().trim(),
		fuenteSam: $("#fuenteSam").val() == "Otros" ? $("#otros").val().trim() : $("#fuenteSam").val(),
		descripcionNoConformidad: $("#descripcionNoConformidad").val(),
		causasNoConformidad: $("#causasNoConformidad").val().trim(),
		accionesATomar: $("#accionesATomar").val().trim(),
		responsable: {
			codTrab: $("#codResponsable").val()
		},
		plazoDias: $("#plazoDias").val(),
		estSoli: "En Proceso",
	}
	
	if(solicitud.solicitante.codTrab == ""){
		$("#msjError").html("Seleccionar a un Solicitante").show();
		return;
	} else if(solicitud.responsable.codTrab == ""){
		$("#msjError").html("Seleccionar a un Responsable del Ánalisis").show();
		return;
	} else if(solicitud.nomProceso == "" || solicitud.nomProceso > 100){
		$("#msjError").html("Ingresar nombre del proceso a mejorar").show();
		return;
	}
	
	if($("#fuenteSam").val() != "Otros"){
		if(solicitud.fuenteSam == -1){
			$("#msjError").html("Seleccionar una fuente de Solicitud de Acción de Mejora").show();
		    return;
		}
	} else {
		if(solicitud.fuenteSam == "" || solicitud.fuenteSam.length > 100){
			$("#msjError").html("Ingresar una fuente de Solicitud de Acción de Mejora").show();
		    return;
		}
	}
	
	if(solicitud.descripcionNoConformidad == "" || solicitud.descripcionNoConformidad.length < 100){
		$("#msjError").html("Detallar la descripción de la no conformidad de mínimo 100 caracteres").show();
		return;
	} else if(solicitud.causasNoConformidad == "" || solicitud.causasNoConformidad.length < 100) {
		$("#msjError").html("Detallar las causas de la no conformidad en un mínimo de 100 caracteres").show();
		return;
	} else if(solicitud.accionesATomar == "" || solicitud.accionesATomar.length < 100) {
		$("#msjError").html("Detallar las acciones a tomar en un mínimo de 100 caracteres").show();
		return;
	} else if(solicitud.plazoDias <= 0){
		$("#msjError").html("Ingresar un plazo válido en días para la solicitud de Acción de Mejora").show();
		return;
	}
	
	$("#msjError").html("").hide();
	fetch("/solicitudAccionMejoras/generar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(solicitud)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Solicitud de Acción de Mejora Generada", "success");
			$("#modal-nueva-solicitud").modal("hide");
			return Promise.all([ObtenerSolicitudes()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Solicitud de Acción de Mejora no fue Generada", "error");
		}
	}).then(([data1]) => {
		solicitudData1 = data1;
		ListarSolicitudes(solicitudData1);
	})
})

document.addEventListener("DOMContentLoaded", async function() {
	solicitudData1 = await ObtenerSolicitudes();
	ListarSolicitudes(solicitudData1);
	$("#msjError").html("").hide();
})

function MostrarTrabajador(trabajador){
	$("#verTrabajador").val(trabajador.nomTrab + " " + trabajador.apeTrab);
	$("#verEmailTrab2").val(trabajador.emailTrab);
	$("#verTelTrab2").val(trabajador.telTrab);
	$("#verEstTrab2").val(trabajador.estTrab == 1 ? "Activo": "Inactivo");
	
	$("#modal-ver-trabajador").modal("show");
}

$(document).on("click", ".btn-ver-solicitante", function() {
	const solicitante = $(this).data("dataSolicitante");
	
	$("#modal-ver-trabajador-label").html("Solicitante");
	$("#labelTrabajador").html("Solicitante");
	MostrarTrabajador(solicitante);
})

$(document).on("click", ".btn-ver-responsable", function() {
	const responsable = $(this).data("dataResponsable");
	
	$("#modal-ver-trabajador-label").html("Responsable del Ánalisis");
	$("#labelTrabajador").html("Responsable del Ánalisis");
	MostrarTrabajador(responsable);
})