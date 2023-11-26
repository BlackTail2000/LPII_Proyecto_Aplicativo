var controlData1;

async function ObtenerTodosLosControlesDeSeguridad(){
	const response = await fetch("/controlSeguridad/mantenimiento/listarTodos");
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
					control.trabajador != null ?
					$('<td class="text-center">').text(control.trabajador.nomTrab + " " + control.trabajador.apeTrab) :
					$('<td class="text-center">').text("Sin Implementador"),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-control">')
						.data("dataControl", control).append(
							$('<i class="bi bi-info-circle">').text(" Detalles")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-primary btn-editar-control">')
						.data("dataControl", control).append(
							$('<i class="bi bi-pencil">').text(" Editar")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-danger btn-eliminar-control">')
						.data("dataControl", control).append(
							$('<i class="bi bi-trash">').text(" Eliminar")
						)
					)
				)
			)
		})
	} else {
		$("#tablaControles tbody").append(
			$("<tr>").append(
				$('<th colspan="7" class="text-center">').text("No se encontraron controles")
			)
		)
	}
}

$(document).on("click", "#btn-nuevo-control", function() {
	$("#nomControl").val("");
	$("#desControl").val("");
	$("#categoria").val("");
	
	$("#msjErrorNuevo").html("").hide();
	$("#modal-nuevo-control").modal("show");
})

$(document).on("click", "#btn-guardar-nuevo", function() {
	const control = {
		codControl: 0,
		nomControl: $("#nomControl").val().trim(),
		desControl: $("#desControl").val().trim(),
		categoria: $("#categoria").val().trim(),
		fechaImpl: "",
		estControl: "Activo"
	}
	
	if(control.nomControl == ""){
		$("#msjErrorNuevo").html("Ingresar nombre de control").show();
		return;
	} else if(control.nomControl.length > 100){
		$("#msjErrorNuevo").html("Nombre de control ingresado demasiado largo").show();
		return;
	} else if(controlData1.some(c => c.nomControl == control.nomControl)){
		$("#msjErrorNuevo").html("Ya existe un control de seguridad registrado con ese nombre").show();
		return;
	} else if(control.desControl == ""){
		$("#msjErrorNuevo").html("Ingresar una descripción para el control").show();
		return;
	} else if(control.categoria == ""){
		$("#msjErrorNuevo").html("Ingresar una categoría para el control").show();
		return;
	}
	
	fetch("/controlSeguridad/mantenimiento/registrar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8"},
		body: JSON.stringify(control)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Nuevo Control registrado", "success");
			$("#modal-nuevo-control").modal("hide");
			return Promise.all([ObtenerTodosLosControlesDeSeguridad()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se pudo registrar el Nuevo Control", "error");
		}
	}).then(([data1]) => {
		controlData1 = data1;
		ListarControles(controlData1);
	})
})

$(document).on("click", ".btn-ver-control", function() {
	const control = $(this).data("dataControl");
	
	$("#modal-ver-control-label").text("Control Nro. " + control.codControl);
	
	$("#verNomControl").val(control.nomControl);
	$("#verDesControl").val(control.desControl);
	$("#verCategoria").val(control.categoria);
	$("#verFechaImpl").val(control.fechaImpl);
	$("#verEstControl").val(control.estControl);
	
	$("#verImplementador").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.nomTrab + " " + control.trabajador.apeTrab);
	$("#verEmailImpl").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.emailTrab);
	$("#verTelImpl").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.telTrab);
	$("#verEstImpl").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.estTrab == 1 ? "Activo" : "Inactivo");
	$("#verFecNac").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.fecNac);
	$("#verFecContrato").val(control.trabajador == null ? "Sin Implementador" : control.trabajador.fecContrato);
	
	$("#modal-ver-control").modal("show");
})

document.addEventListener("DOMContentLoaded", async function() {
	controlData1 = await ObtenerTodosLosControlesDeSeguridad();
	ListarControles(controlData1);
	
	$("#msjErrorNuevo").html("").hide();
	$("#msjErrorEditar").html("").hide();
})

$(document).on("click", ".btn-editar-control", function() {
	const control = $(this).data("dataControl");
	
	$("#modal-actualizar-control-label").text("Control Nro. " + control.codControl);
	
	$("#editarCodControl").val(control.codControl)
	$("#editarNomControl").val(control.nomControl);
	$("#editarDesControl").val(control.desControl);
	$("#editarCategoria").val(control.categoria);
	$("#editarFechaImpl").val(control.fechaImpl);
	$("#editarEstControl").val(control.estControl);
	
	$("#modal-actualizar-control").modal("show");
	$("#msjErrorEditar").html("").hide();
})

$(document).on("click", "#btn-actualizar-control", function() {
	const control = {
		codControl: $("#editarCodControl").val(),
		nomControl: $("#editarNomControl").val().trim(),
		desControl: $("#editarDesControl").val().trim(),
		categoria: $("#editarCategoria").val().trim(),
		fechaImpl: $("#editarFechaImpl").val(),
		estControl: $("#editarEstControl").val()
	}
	
	if(control.nomControl == ""){
		$("#msjErrorEditar").html("Ingresar nombre de control").show();
		return;
	} else if(control.nomControl.length > 100){
		$("#msjErrorEditar").html("Nombre de control ingresado demasiado largo").show();
		return;
	} else if(controlData1.some(c => c.nomControl == control.nomControl && c.codControl != control.codControl)){
		$("#msjErrorEditar").html("Ya existe un control de seguridad registrado con ese nombre").show();
		return;
	} else if(control.desControl == ""){
		$("#msjErrorEditar").html("Ingresar una descripción para el control").show();
		return;
	} else if(control.categoria == ""){
		$("#msjErrorEditar").html("Ingresar una categoría para el control").show();
		return;
	}
	
	fetch("/controlSeguridad/mantenimiento/actualizar", {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(control)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Control actualizado", "success");
			$("#modal-actualizar-control").modal("hide");
			return Promise.all([ObtenerTodosLosControlesDeSeguridad()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se pudo actualizar el Control", "error");
		}
	}).then(([data1]) => {
		controlData1 = data1;
		ListarControles(controlData1);
	})
})

$(document).on("click", ".btn-eliminar-control", function() {
	const control = $(this).data("dataControl");
	
	Swal.fire({
		title: "¿Eliminar Control de Seguridad?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/controlSeguridad/mantenimiento/eliminar/${control.codControl}`, {
				method: "DELETE",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Control eliminado", "success");
			        return Promise.all([ObtenerTodosLosControlesDeSeguridad()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "No se pudo eliminar el Control", "error");
				}
			}).then(([data1]) => {
				controlData1 = data1;
				ListarControles(controlData1);
			})
		}
	})
})