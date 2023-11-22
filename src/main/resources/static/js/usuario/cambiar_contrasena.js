$(document).on("click", "#btnGuardar", function() {
	const clvUsuaA = $("#clvUsua1").val().trim();
	const clvUsuaB = $("#clvUsua2").val().trim();
	
	if(clvUsuaA == ""){
		$("#msjError").html("Ingresar nueva contraseña").show();
		return;
	} else if(clvUsuaA.length < 10){
		$("#msjError").html("La nueva contraseña debe contener como mínimo 10 caracteres").show();
		return;
	} else if(clvUsuaB == ""){
		$("#msjError").html("Ingresar de nuevo la nueva contraseña").show();
		return;
	} else if(clvUsuaA != clvUsuaB){
		$("#msjError").html("Las contraseñas no coinciden").show();
		return;
	}
	
	fetch(`/usuario/actualizarContrasena/${clvUsuaA}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok){
			window.location.href = "/";
		}
	})
})

document.addEventListener("DOMContentLoaded", function() {
	$("#msjError").hide();
})