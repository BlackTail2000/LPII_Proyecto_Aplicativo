$(document).on("click", "#btnEditar", function() {
	$("#nomUsua").prop("disabled", false);
	$("#apeUsua").prop("disabled", false);
	$("#emailUsua").prop("disabled", false);
	$("#fecNac").prop("disabled", false);
	
	$("#btnAceptar").addClass("btn-success");
	$("#btnAceptar").removeClass("btn-primary");
	$("#btnAceptar").prop("id", "btnGuardar");
	$("#btnGuardar").text("Guardar");
	
	$("#btnCancelar").show();
})

$(document).on("click", "#btnAceptar, #btnCancelar", function() {
	window.location.href = "/";
})

$(document).on("click", "#btnGuardar", function() {
	const usuario = {
		nomUsua: $("#nomUsua").val().trim(),
		apeUsua: $("#apeUsua").val().trim(),
		emailUsua: $("#emailUsua").val().trim(),
		fecNac: $("#fecNac").val()
	}
	
	if(usuario.nomUsua == ""){
		$("#msjError").html("Ingresar nombres").show();
		return;
	} else if(usuario.nomUsua.length > 100){
		$("#msjError").html("Nombres demasiado largos").show();
		return;
	} else if(usuario.apeUsua == ""){
		$("#msjError").html("Ingresar apellidos").show();
		return;
	} else if(usuario.apeUsua.length > 100){
		$("#msjError").html("Apellidos demasiado largos").show();
		return;
	} else if(usuario.emailUsua == ""){
		$("#msjError").html("Ingresar email").show();
		return;
	} else if(!validarEmail(usuario.emailUsua)){
		$("#msjError").html("Email no válido").show();
		return;
	} else if(usuario.fecNac == ""){
		$("#msjError").html("Ingresar una fecha de nacimiento válida").show();
		return;
	} else if(!validarEdad(usuario.fecNac)){
		$("#msjError").html("Usuario debe ser mayor a 18 años").show();
		return;
	}
	
	$("#msjError").html("").hide();
	fetch("/usuario/actualizarCuenta", {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(usuario)
	}).then(response => {
		if(response.ok){
			window.location.href = "/";
		} else {
			$("#msjError").html("No se pudo actualizar tu cuenta").show();
		    return;
		}
	})
})

document.addEventListener("DOMContentLoaded", function(){
	$("#btnCancelar").hide();
	$("#msjError").hide();
})

