var implementadorData1;

var implementadorModelo = {
	codImpl: 0,
	nomImpl: "",
	apeImpl: "",
	emailImpl: "",
	telImpl: "",
	estImpl: 1,
	fecNac: "",
	fecContrato: ""
}

async function ObtenerImplementadores(){
	const response = await fetch("/implementador/mantenimiento/listarImplementadores");
	const responseJson = await response.json();
	return responseJson;
}

function ListarImplementadores(implementadorData){
	$("#tablaImplementadores tbody").html("");
	if(implementadorData.length > 0){
		implementadorData.forEach((implementador) => {
			if(implementador.estImpl == 1){
			$("#tablaImplementadores tbody").append(
				$("<tr>").append(
					$('<th class="text-center">').text(implementador.nomImpl + " " + implementador.apeImpl),
					$('<td class="text-center">').text(implementador.emailImpl),
					$('<td class="text-center">').text(implementador.fecContrato),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-success btn-ver-implementador">')
						.data("dataImplementador", implementador).append(
							$('<i class="bi bi-info-circle">').text(" Detalles")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-primary btn-editar-implementador">')
						.data("dataImplementador", implementador).append(
							$('<i class="bi bi-pencil">').text(" Editar")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-danger btn-eliminar-implementador">')
						.data("dataImplementador", implementador).append(
							$('<i class="bi bi-trash">').text(" Eliminar")
						)
					)
				)
			)}
		})
	} else {
		$("#tablaImplementadores tbody").append(
			$("<tr>").append(
				$('<th colspan="6" class="text-center">').text("No se encontraron implementadores")
			)
		)
	}
}

function AbrirModalImplementador(){
	$("#nomImpl").val(implementadorModelo.nomImpl);
	$("#apeImpl").val(implementadorModelo.apeImpl);
	$("#emailImpl").val(implementadorModelo.emailImpl);
	$("#telImpl").val(implementadorModelo.telImpl);
	$("#fecNac").val(implementadorModelo.fecNac);
	
	$("#msjError").html("").hide();
	$("#modal-implementador").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	implementadorData1 = await ObtenerImplementadores();
	ListarImplementadores(implementadorData1);
	$("#msjError").hide();
})

$(document).on("click", "#btn-nuevo-implementador", function() {
	$("#modal-implementador-label").html("Nuevo Implementador");
	
	$("#btn-guardar-implementador").removeClass("btn-success");
	$("#btn-guardar-implementador").removeClass("btn-primary");
	$("#btn-guardar-implementador").addClass("btn-success")
	
	implementadorModelo.codImpl = 0;
	implementadorModelo.nomImpl = "";
	implementadorModelo.apeImpl = "";
	implementadorModelo.emailImpl = "";
	implementadorModelo.telImpl = "";
	implementadorModelo.estImpl = 1;
	implementadorModelo.fecNac = "";
	implementadorModelo.fecContrato = "";
	
	AbrirModalImplementador();
})

$(document).on("click", ".btn-editar-implementador", function() {
	const implementador = $(this).data("dataImplementador");
	
	$("#modal-implementador-label").html("Editar Implementador");
	
	$("#btn-guardar-implementador").removeClass("btn-success");
	$("#btn-guardar-implementador").removeClass("btn-primary");
	$("#btn-guardar-implementador").addClass("btn-primary");
	
	implementadorModelo.codImpl = implementador.codImpl;
	implementadorModelo.nomImpl = implementador.nomImpl;
	implementadorModelo.apeImpl = implementador.apeImpl;
	implementadorModelo.emailImpl = implementador.emailImpl;
	implementadorModelo.telImpl = implementador.telImpl;
	implementadorModelo.estImpl = implementador.estImpl;
	implementadorModelo.fecNac = implementador.fecNac;
	implementadorModelo.fecContrato = implementador.fecContrato;
	
	AbrirModalImplementador();
})

$(document).on("click", "#btn-guardar-implementador", function() {
	const implementador = {
		codImpl: implementadorModelo.codImpl,
		nomImpl: $("#nomImpl").val().trim(),
		apeImpl: $("#apeImpl").val().trim(),
		emailImpl: $("#emailImpl").val().trim(),
		telImpl: $("#telImpl").val().trim(),
		estImpl: implementadorModelo.estImpl,
		fecNac: $("#fecNac").val().trim(),
		fecContrato: implementadorModelo.fecContrato
	}
	
	if(implementador.codImpl == 0)
	fetch("/implementador/mantenimiento/registrar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(implementador)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Nuevo Implementador registrado", "success");
			$("#modal-implementador").modal("hide");
			return Promise.all([ObtenerImplementadores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se puedo registrar al Nuevo Implementador", "error");
		}
	}).then(([data1]) => {
		implementadorData1 = data1;
		ListarImplementadores(implementadorData1);
	})
	
	if(implementador.codImpl != 0){
		fetch("/implementador/mantenimiento/actualizar", {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(implementador)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Implementador actualizado", "success");
			$("#modal-implementador").modal("hide");
			return Promise.all([ObtenerImplementadores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "No se puedo actualizar al Implementador", "error");
		}
	}).then(([data1]) => {
		implementadorData1 = data1;
		ListarImplementadores(implementadorData1);
	})
	}
})

$(document).on("click", ".btn-ver-implementador", function() {
	const implementador =$(this).data("dataImplementador");
	
	$("#verImplementador").val(implementador.nomImpl + " " + implementador.apeImpl);
	$("#verEmailImpl").val(implementador.emailImpl);
	$("#verTelImpl").val(implementador.telImpl);
	$("#verEstImpl").val(implementador.estImpl == 1 ? "Activo" : "Inactivo");
	$("#verFecNac").val(implementador.fecNac);
	$("#verFecContrato").val(implementador.fecContrato);
	
	$("#modal-ver-implementador").modal("show");
})

$(document).on("click", ".btn-eliminar-implementador", function() {
	const implementador = $(this).data("dataImplementador");
	Swal.fire({
		title: "¿Eliminar Implementador?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/implementador/mantenimiento/eliminar/${implementador.codImpl}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" },
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Implementador eliminado", "success");
			        return Promise.all([ObtenerImplementadores()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "No se puedo eliminar al Implementador", "error");
				}
			}).then(([data1]) => {
				implementadorData1 = data1;
				ListarImplementadores(implementadorData1);
			})
		}
	})
})