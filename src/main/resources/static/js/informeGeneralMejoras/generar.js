var solicitudData1;

async function ObtenerSolicitudes(){
	const response = await fetch("/informeGeneralMejoras/generar/listarSolicitudes");
	const responseJson = await response.json();
	return responseJson;
}

function ListarSolicitudes(solicitudData){
	$("#tablaSolicitudes tbody").html("");
	if(solicitudData.length > 0){
		solicitudData.forEach((solicitud) => {
			
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
						$(`<a class="btn btn-outline-danger" href="/informeGeneralMejoras/generar/verSolicitud/${solicitud.numSoli}" target="_blank">`).append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					),
					$('<td class="text-center">').text(solicitud.estSoli),
					solicitud.estSoli == "Finalizado" ?
					$('<td class="text-center">').append(
						$(`<a class="btn btn-outline-danger" href="/informeGeneralMejoras/generar/verInforme/${solicitud.numSoli}" target="_blank">`).append(
							$('<i class="bi bi-file-earmark-pdf">')
						)
					) :
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-success btn-nuevo-informe">')
						.data("dataSolicitud", solicitud).append(
							$('<i class="bi bi-file-earmark-plus">')
						)
					)
				)
			)
		})
	} else {
		$("#tablaSolicitudes tbody").append(
			$("<tr>").append(
				$('<th colspan="7" class="text-center">').text("No se encontraron solicitudes")
			)
		)
	}
}

function MostrarTrabajador(trabajador, tipo){
	if(tipo == "Solicitante")
        $("#modal-ver-trabajador-label").html("Solicitante");
	else 
		$("#modal-ver-trabajador-label").html("Responsable del Ánalisis");
	
	$("#verTrabajador").val(trabajador.nomTrab + " " + trabajador.apeTrab);
	$("#verEmailTrab").val(trabajador.emailTrab);
	$("#verTelTrab").val(trabajador.telTrab);
	$("#verEstTrab").val(trabajador.estTrab);
	$("#modal-ver-trabajador").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	solicitudData1 = await ObtenerSolicitudes();
	ListarSolicitudes(solicitudData1);
})

$(document).on("click", ".btn-ver-solicitante", function() {
	const solicitante = $(this).data("dataSolicitante");
	MostrarTrabajador(solicitante, "Solicitante");
})

$(document).on("click", ".btn-ver-responsable", function() {
	const responsable = $(this).data("dataResponsable");
	MostrarTrabajador(responsable, "Responsable");
})

$(document).on("click", ".btn-nuevo-informe", function() {
	const solicitud = $(this).data("dataSolicitud");
	
	$("#numSoli").val(solicitud.numSoli);
	$("#objetivo").val("");
	$("#areaEspecifica").val("");
	$("#mejoraImplementadas").val("");
	$("#descripcionImplementacion").val("");
	$("#resultadosPrueba").val("");
	$("#recomendacionesAdicionales").val("");
	$("#conclusiones").val("");
	$("#buscarImpl").val("");
	
	$("#tablaImplementadores tbody").html("");
	$("#tablaImplementadores tbody").append(
		$("<tr>").append(
			$('<th colspan="2" class="text-center">').text("No se encontraron Implementadores")
		)
	)
	
	$("#codImpl").val("");
	$("#verImpl").val("");
	$("#verEmailImpl").val("");
	$("#verTelImpl").val("");
	$("#verEstImpl").val("");
	$("#verFecNacImpl").val("");
	$("#verFecContratoImpl").val("");
	
	$("#msjError").html("").hide();
	
	$("#modal-generar-informe").modal("show");
})

$(document).on("click", ".btn-seleccionar-implementador", function() {
	const implementador = $(this).data("dataImplementador");
	
	$("#codImpl").val(implementador.codTrab);
	$("#verImpl").val(implementador.nomTrab + " " + implementador.apeTrab);
	$("#verEmailImpl").val(implementador.emailTrab);
	$("#verTelImpl").val(implementador.telTrab);
	$("#verEstImpl").val(implementador.estTrab == 1 ? "Activo": "Inactivo");
	$("#verFecNacImpl").val(implementador.fecNac);
	$("#verFecContratoImpl").val(implementador.fecContrato);
})

$(document).on("input", "#buscarImpl", function() {
	const busqueda = $("#buscarImpl").val().trim();
	
	if(busqueda != ""){
		fetch(`/informeGeneralMejoras/generar/buscar/implementadores/${busqueda}`, {
			method: "GET",
			headers: { "ContentType": "application/json;charset=utf-8" }
		}).then(response => {
			return response.ok ? response.json() : Promise.reject(response);
		}).then(responseJson => {
			$("#tablaImplementadores tbody").html("");
			if(responseJson.length > 0){
				responseJson.forEach((impl) => {
					$("#tablaImplementadores tbody").append(
						$("<tr>").append(
							$('<td class="text-center">').text(impl.nomTrab + " " + impl.apeTrab),
							$('<td class="text-center">').append(
								$('<button class="btn btn-outline-warning btn-seleccionar-implementador">')
								.data("dataImplementador", impl).append(
									$('<i class="bi bi-person-plus">')
								)
							)
						)
					)
				})
			} else {
				$("#tablaImplementadores tbody").append(
					$("<tr>").append(
						$('<th colspan="2" class="text-center">').text("No se encontraron Implementadores")
					)
				)
			}
		})
	} else {
		$("#tablaImplementadores tbody").html("");
		$("#tablaImplementadores tbody").append(
			$("<tr>").append(
				$('<th colspan="2" class="text-center">').text("No se encontraron Implementadores")
			)
		)
	}
})

$(document).on("click", "#btn-generar-informe", function() {
	const informe = {
		numInforme: 0,
		objetivo: $("#objetivo").val().trim(),
		areaEspecifica: $("#areaEspecifica").val().trim(),
		mejoraImplementada: $("#mejoraImplementada").val().trim(),
		descripcionImplementacion: $("#descripcionImplementacion").val().trim(),
		resultadosPrueba: $("#resultadosPrueba").val().trim(),
		recomendacionesAdicionales: $("#recomendacionesAdicionales").val().trim(),
		conclusiones: $("#conclusiones").val().trim(),
		implementador: {
			codTrab: $("#codImpl").val()
		},
		solicitudAccionMejoras: {
			numSoli: $("#numSoli").val()
		}
	}
	
	if(informe.objetivo == "" || informe.objetivo.length < 100){
		$("#msjError").html("Describir el objetivo en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.areaEspecifica == "" || informe.areaEspecifica.length < 100){
		$("#msjError").html("Describir el área específica en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.mejoraImplementada == "" || informe.mejoraImplementada.length < 100){
		$("#msjError").html("Describir la mejora implementada en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.descripcionImplementacion == "" || informe.descripcionImplementacion.length < 100){
		$("#msjError").html("Describir la implementación en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.resultadosPrueba == "" || informe.resultadosPrueba.length < 100){
		$("#msjError").html("Describir los resultados de la prueba en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.recomendacionesAdicionales == "" || informe.recomendacionesAdicionales.length < 100){
		$("#msjError").html("Describir las recomendaciones adicionales en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.conclusiones == "" || informe.conclusiones.length < 100){
		$("#msjError").html("Describir las conclusiones finales en un mínimo de 100 caracteres").show();
		return;
	} else if(informe.implementador.codTrab == "") {
		$("#msjError").html("Seleccionar a un implementador").show();
		return;
	}
	
	fetch(`/informeGeneralMejoras/generar`, {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(informe)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Informe General de Mejoras generado", "success");
			$("#modal-generar-informe").modal("hide");
			return Promise.all([ObtenerSolicitudes()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Ocurrión un error al intentar generar el Informe", "error");
		}
	}).then(([data1]) => {
		solicitudData1 = data1;
		ListarSolicitudes(solicitudData1);
	})
})