var trabajadorData1;

var trabajadorModelo = {
	codTrab: 0,
	nomTrab: "",
	apeTrab: "",
	emailTrab: "",
	telTrab: "",
	estTrab: 1,
	fecNac: "",
	fecContrato: "",
	codTipo: -1
}

async function ObtenerTrabajadores(){
	const response = await fetch("/trabajador/mantenimiento/listarTrabajadores");
	const responseJson = await response.json();
	return responseJson;
}

function ListarTrabajadores(trabajadorData){
	$("#tablaTrabajadores tbody").html("");
	if(trabajadorData.length > 0){
		trabajadorData.forEach((trabajador) => {
			if(trabajador.estTrab == 1){
			$("#tablaTrabajadores tbody").append(
				$("<tr>").append(
					$('<th class="text-center">').text(trabajador.nomTrab + " " + trabajador.apeTrab),
					$('<td class="text-center">').text(trabajador.emailTrab),
					$('<td class="text-center">').text(trabajador.fecContrato),
					$('<td class="text-center">').text(trabajador.tipo.nomTipo),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-success btn-ver-trabajador">')
						.data("dataTrabajador", trabajador).append(
							$('<i class="bi bi-info-circle">').text(" Detalles")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-primary btn-editar-trabajador">')
						.data("dataTrabajador", trabajador).append(
							$('<i class="bi bi-pencil">').text(" Editar")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-danger btn-eliminar-trabajador">')
						.data("dataTrabajador", trabajador).append(
							$('<i class="bi bi-trash">').text(" Eliminar")
						)
					)
				)
			)}
		})
	} else {
		$("#tablaTrabajadores tbody").append(
			$("<tr>").append(
				$('<th colspan="6" class="text-center">').text("No se encontraron trabajadores")
			)
		)
	}
}

function CargarTipos(){
	fetch("/trabajador/mantenimiento/cargarTipos").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		if(responseJson.length > 0){
			responseJson.forEach((tipo) => {
				$("#tipo").append(
					$("<option>").val(tipo.codTipo).text(tipo.nomTipo)
				)
			})
		}
	})
}

function AbrirModalTrabajador(){
	$("#nomTrab").val(trabajadorModelo.nomTrab);
	$("#apeTrab").val(trabajadorModelo.apeTrab);
	$("#emailTrab").val(trabajadorModelo.emailTrab);
	$("#telTrab").val(trabajadorModelo.telTrab);
	$("#fecNac").val(trabajadorModelo.fecNac);
	$("#tipo").val(trabajadorModelo.codTipo);
	
	$("#msjError").html("").hide();
	$("#modal-trabajador").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	trabajadorData1 = await ObtenerTrabajadores();
	ListarTrabajadores(trabajadorData1);
	CargarTipos();
	$("#msjError").hide();
})

$(document).on("click", "#btn-nuevo-trabajador", function() {
	$("#modal-trabajador-label").html("Nuevo Trabajador");
	
	$("#btn-guardar-trabajador").removeClass("btn-success");
	$("#btn-guardar-trabajador").removeClass("btn-primary");
	$("#btn-guardar-trabajador").addClass("btn-success")
	
	trabajadorModelo.codTrab = 0;
	trabajadorModelo.nomTrab = "";
	trabajadorModelo.apeTrab = "";
	trabajadorModelo.emailTrab = "";
	trabajadorModelo.telTrab = "";
	trabajadorModelo.estTrab = 1;
	trabajadorModelo.fecNac = "";
	trabajadorModelo.fecContrato = "";
	trabajadorModelo.codTipo = -1;
	
	AbrirModalTrabajador();
})

$(document).on("click", ".btn-editar-trabajador", function() {
	const trabajador = $(this).data("dataTrabajador");
	
	$("#modal-trabajador-label").html("Editar Trabajador");
	
	$("#btn-guardar-trabajador").removeClass("btn-success");
	$("#btn-guardar-trabajador").removeClass("btn-primary");
	$("#btn-guardar-trabajador").addClass("btn-primary");
	
	trabajadorModelo.codTrab = trabajador.codTrab;
	trabajadorModelo.nomTrab = trabajador.nomTrab;
	trabajadorModelo.apeTrab = trabajador.apeTrab;
	trabajadorModelo.emailTrab = trabajador.emailTrab;
	trabajadorModelo.telTrab = trabajador.telTrab;
	trabajadorModelo.estTrab = trabajador.estTrab;
	trabajadorModelo.fecNac = trabajador.fecNac;
	trabajadorModelo.fecContrato = trabajador.fecContrato;
	trabajadorModelo.codTipo = trabajador.tipo.codTipo;
	
	AbrirModalTrabajador();
})

$(document).on("click", "#btn-guardar-trabajador", function() {
	const trabajador = {
		codTrab: trabajadorModelo.codTrab,
		nomTrab: $("#nomTrab").val().trim(),
		apeTrab: $("#apeTrab").val().trim(),
		emailTrab: $("#emailTrab").val().trim(),
		telTrab: $("#telTrab").val().trim(),
		estTrab: trabajadorModelo.estTrab,
		fecNac: $("#fecNac").val().trim(),
		fecContrato: trabajadorModelo.fecContrato,
		tipo: {
			codTipo: $("#tipo").val()
		}
	}
	
	if(trabajador.codTrab == 0)
	fetch("/trabajador/mantenimiento/registrar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(trabajador)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Nuevo Trabajador registrado", "success");
			$("#modal-trabajador").modal("hide");
			return Promise.all([ObtenerTrabajadores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se puedo registrar al Nuevo Trabajador", "error");
		}
	}).then(([data1]) => {
		trabajadorData1 = data1;
		ListarTrabajadores(trabajadorData1);
	})
	
	if(trabajador.codTrab != 0){
		fetch("/trabajador/mantenimiento/actualizar", {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(trabajador)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Trabajador actualizado", "success");
			$("#modal-trabajador").modal("hide");
			return Promise.all([ObtenerTrabajadores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se puedo actualizar al Trabajador", "error");
		}
	}).then(([data1]) => {
		trabajadorData1 = data1;
		ListarTrabajadores(trabajadorData1);
	})
	}
})

$(document).on("click", ".btn-ver-trabajador", function() {
	const trabajador = $(this).data("dataTrabajador");
	
	$("#verTrabajador").val(trabajador.nomTrab + " " + trabajador.apeTrab);
	$("#verEmailTrab").val(trabajador.emailTrab);
	$("#verTelTrab").val(trabajador.telTrab);
	$("#verEstTrab").val(trabajador.estTrab == 1 ? "Activo" : "Inactivo");
	$("#verFecNac").val(trabajador.fecNac);
	$("#verFecContrato").val(trabajador.fecContrato);
	
	$("#modal-ver-trabajador").modal("show");
})

$(document).on("click", ".btn-eliminar-trabajador", function() {
	const trabajador = $(this).data("dataTrabajador");
	Swal.fire({
		title: "¿Eliminar Trabajador?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/trabajador/mantenimiento/eliminar/${trabajador.codTrab}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" },
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Trabajador eliminado", "success");
			        return Promise.all([ObtenerTrabajadores()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "No se puedo eliminar al Trabajador", "error");
				}
			}).then(([data1]) => {
				trabajadorData1 = data1;
				ListarTrabajadores(trabajadorData1);
			})
		}
	})
})