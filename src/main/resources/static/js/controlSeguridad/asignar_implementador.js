var controlData1;

async function ObtenerTodosLosControles(){
	const response = await fetch("/controlSeguridad/asignarImplementador/listarTodos");
	const responseJson = await response.json();
	return responseJson;
}

function ListarControles(controlData){
	$("#tablaControles tbody").html("");
	if(controlData.length > 0){
		controlData.forEach((control) => {
			$("#tablaControles tbody").append(
				$("<tr>").append(
					$('<td class="text-center">').text(control.nomControl),
					$('<td class="text-center">').text(control.categoria),
					$('<td class="text-center">').text(control.fechaImpl),
					$('<td class="text-center">').text(control.responsable == null ? "Sin responsable" : control.responsable.nomImpl + " " + control.responsable.apeImpl),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-control">')
						.data("dataControl", control).append(
							$('<i class="bi bi-info-circle">').text(" Ver Detalles")
						)
					),
					$('<td class="text-center">').append(
						control.responsable == null ?
						$('<button class="btn btn-outline-success btn-asignar-implementador">')
						.data("dataControl", control).append(
							$('<i class="bi bi-person-add">').text(" Asignar")
						) :
						$('<button class="btn btn-outline-danger btn-desasignar-implementador">')
						.data("dataControl", control).append(
							$('<i class="bi bi-person-dash">').text(" Desasignar")
						)
					)
				)
			)
		})
	} else {
		$("#tablaControles tbody").append(
			$("<tr>").append(
				$('<th colspan="5" class="text-center">').text("No se encontraron controles")
			)
		)
	}
}

function ListarImplementadores(implementadorData){
	$("#tablaImplementadores tbody").html("");
	if(implementadorData.length > 0){
		implementadorData.forEach((implementador) => {
			$("#tablaImplementadores tbody").append(
				$("<tr>").append(
					$('<td class="text-center">').text(implementador.nomImpl + " " + implementador.apeImpl),
					$('<td class="text-center">').append(
						$('<button class="btn btn-warning btn-seleccionar">')
						.data("dataImplementador", implementador).append(
							$('<i class="bi bi-person-check">').text(" Seleccionar")
						)
					)
				)
			)
		})
	} else {
		$("#tablaImplementadores tbody").append(
			$("<tr>").append(
				$('<th colspan="2" class="text-center">').text("No se encontraron implementadores")
			)
		)
	}
}

$(document).on("click", ".btn-asignar-implementador", function() {
	const control = $(this).data("dataControl");
	
	$("#codControl").val(control.codControl);
	$("#nomControl").val(control.nomControl);
	$("#desControl").val(control.desControl);
	
	$("#buscarImpl").val("");
	$("#tablaImplementadores tbody").html("");
	$("#tablaImplementadores tbody").append(
		$("<tr>").append(
			$('<th colspan="2" class="text-center">').text("No se encontraron implementadores")
		)
	);
	$("#codImpl").val("");
	$("#implementador").val("");
	$("#emailImpl").val("");
	$("#telImpl").val("");
	$("#fecNac").val("");
	$("#fecContrato").val("");
	
	$("#msjErrorAsignar").html("").hide();
	$("#modal-asignar-implementador").modal("show");	
})

$(document).on("input keyup", "#buscarImpl", function() {
	let busqueda = $("#buscarImpl").val();
	
	fetch(`/controlSeguridad/asignarImplementador/buscar/implementadores/${busqueda}`, {
		method: "GET",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		ListarImplementadores(responseJson);
	})
})

$(document).on("click", ".btn-seleccionar", function() {
	const implementador = $(this).data("dataImplementador");
	
	$("#codImpl").val(implementador.codImpl);
	$("#implementador").val(implementador.nomImpl + " " + implementador.apeImpl);
	$("#emailImpl").val(implementador.emailImpl);
	$("#telImpl").val(implementador.telImpl);
	$("#fecNac").val(implementador.fecNac);
	$("#fecContrato").val(implementador.fecContrato);
})

$(document).on("click", "#btn-asignar", function() {
	const codControl = $("#codControl").val();
	const codImpl = $("#codImpl").val();
	
	if(codImpl == ""){
		$("#msjErrorAsignar").html("Seleccionar un implementador").show();
		return;
	}
	
	fetch(`/controlSeguridad/asignarImplementador/${codControl}/${codImpl}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Implementador Asignado", "success");
			$("#modal-asignar-implementador").modal("hide");
			return Promise.all([ObtenerTodosLosControles()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se pudo Asignar a este Implementador", "error");
		}
	}).then(([data1]) => {
		controlData1 = data1;
		ListarControles(controlData1);
	})
})

$(document).on("click", ".btn-desasignar-implementador", function() {
	const control = $(this).data("dataControl");
	
	Swal.fire({
		title: "¿Desasignar Implementador?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Canceltar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/controlSeguridad/asignarImplementador/desasignar/${control.codControl}`, {
				method: "PUT",
				headers : { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Implementador desasignado", "success");
					return Promise.all([ObtenerTodosLosControles()])
				} else {
					Swal.fire("¡Operación No Exitosa!", "No se pudo desasignar al implementador", "error");
				}
			}).then(([data1]) => {
				controlData1 = data1;
				ListarControles(controlData1);
			})
		}
	})
})

$(document).on("click", ".btn-ver-control", function() {
	const control = $(this).data("dataControl");
	
	$("#verNomControl").val(control.nomControl);
	$("#verDesControl").val(control.desControl);
	$("#verCategoria").val(control.categoria);
	$("#verFechaImpl").val(control.fechaImpl);
	$("#verEstControl").val(control.estControl);
	$("#verImplementador").val(control.responsable == null ? "Sin Implementador asignado" : control.responsable.nomImpl + " " + control.responsable.apeImpl);
	$("#verEmailImpl").val(control.responsable == null ? "Sin Implementador asignado" : control.responsable.emailImpl);
	$("#verTelImpl").val(control.responsable == null ? "Sin Implementador asignado" : control.responsable.telImpl);
	$("#verFecNac").val(control.responsable == null ? "Sin Implementador asignado" : control.responsable.fecNac);
	$("#verFecContrato").val(control.responsable == null ? "Sin Implementador asignado" : control.responsable.fecContrato);
	
	$("#modal-ver-control-label").html("Control Nro. " + control.codControl);
	$("#modal-ver-control").modal("show");
})

document.addEventListener("DOMContentLoaded", async function() {
	controlData1 = await ObtenerTodosLosControles();
	ListarControles(controlData1);
})