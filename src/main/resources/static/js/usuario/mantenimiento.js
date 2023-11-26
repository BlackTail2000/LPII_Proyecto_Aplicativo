var usuarioData1;

async function ObtenerUsuariosNoAdministradores(){
	const response = await fetch("/usuario/mantenimiento/listarUsuarios");
	const responseJson = await response.json();
	return responseJson;
}

function CargarRoles(){
	fetch("/rol/buscar/todos/ordenar/porNombre").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		if(responseJson.length > 0){
			responseJson.forEach((rol) => {
				$("#nuevoRol").append(
					$("<option>").val(rol.codRol).text(rol.nomRol)
				);
				$("#cambiarRol").append(
					$("<option>").val(rol.codRol).text(rol.nomRol)
				)
			})
		}
	})
}

function ListarUsuarios(usuarioData){
	$("#tablaUsuarios tbody").html("");
	if(usuarioData.length > 0){
		usuarioData.forEach((usuario) => {
			$("#tablaUsuarios tbody").append(
				$("<tr>").append(
					$('<th class="text-center">').text(usuario.codUsua),
					$('<td class="text-center">').text(usuario.nomUsua + " " + usuario.apeUsua),
					$('<td class="text-center">').text(usuario.rol.nomRol),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-info btn-ver-usuario">')
						.data("dataUsuario", usuario).append(
							$('<i class="bi bi-info-circle">').text(" Ver Usuario")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-primary btn-cambiar-rol">')
						.data("dataUsuario", usuario).append(
							$('<i class="bi bi-pencil">').text(" Cambiar rol")
						)
					),
					$('<td class="text-center">').append(
						usuario.estUsua == 1 ?
						$('<button class="btn btn-outline-danger btn-bloquear">')
						.data("dataUsuario", usuario).append(
							$('<i class="bi bi-lock-fill">').text(" Bloquear usuario")
						) :
						$('<button class="btn btn-outline-warning btn-desbloquear">')
						.data("dataUsuario", usuario).append(
							$('<i class="bi bi-unlock-fill">').text(" Desbloquear usuario")
						)
					)
				)
			)
		})
	} else {
		$("#tablaUsuarios tbody").append(
			$("<tr>").append(
				$('<th class="text-center" colspan="6">').text("No se encontraron usuarios")
			)
		)
	}
}

document.addEventListener("DOMContentLoaded", async function() {
	usuarioData1 = await ObtenerUsuariosNoAdministradores();
	ListarUsuarios(usuarioData1);
	CargarRoles();
	
	$("#msjErrorNuevo").hide();
	$("#msjErrorCambiar").hide();
})

$(document).on("click", "#btn-nuevo-usuario", function() {
	$("#nuevoNomUsua").val("");
	$("#nuevoApeUsua").val("");
	$("#nuevoEmailUsua").val("");
	$("#nuevoFecNac").val("");
	$("#nuevoRol").val(-1);
	
	$("#msjErrorNuevo").html("").hide();
	$("#modal-nuevo-usuario").modal("show");
})

$(document).on("click", "#btn-guardar-usuario", function() {
	const usuario = {
		codUsua: "",
		nomUsua: $("#nuevoNomUsua").val().trim(),
		apeUsua: $("#nuevoApeUsua").val().trim(),
		clvUsua: "123",
		emailUsua: $("#nuevoEmailUsua").val().trim(),
		estUsua: 1,
		fecNac: $("#nuevoFecNac").val(),
		fecRegistro: "",
		ultLogin: null,
		rol: {
			codRol: $("#nuevoRol").val()
		}
	}
	
	if(usuario.nomUsua == ""){
		$("#msjErrorNuevo").html("Ingresar nombres").show();
		return;
	} else if(usuario.nomUsua.length > 100){
		$("#msjErrorNuevo").html("Nombres ingresados demasiado largos").show();
		return;
	} else if(usuario.apeUsua == ""){
		$("#msjErrorNuevo").html("Ingresar apellidos").show();
		return;
	} else if(usuario.apeUsua.length > 100){
		$("#msjErrorNuevo").html("Apellidos ingresados demasiado largos").show();
		return;
	} else if(usuario.emailUsua == ""){
		$("#msjErrorNuevo").html("Ingresar email").show();
		return;
	} else if(!validarEmail(usuario.emailUsua)){
		$("#msjErrorNuevo").html("Email ingresado no es válido").show();
		return;
	} else if(usuario.emailUsua.length > 70){
		$("#msjErrorNuevo").html("Email ingresado demasiado largo").show();
		return;
	} else if(usuario.fecNac == ""){
		$("#msjErrorNuevo").html("Ingresar fecha de nacimiento").show();
		return;
	} else if(!validarEdad(usuario.fecNac)){
		$("#msjErrorNuevo").html("El usuario debe ser mayor de 18 años").show();
		return;
	} else if(usuario.rol.codRol == -1){
		$("#msjErrorNuevo").html("Seleccionar un rol").show();
		return;
	}
	
	fetch("/usuario/mantenimiento/registrar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(usuario)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Nuevo Usuario registrado", "success");
			$("#msjErrorNuevo").html("").hide();
			$("#modal-nuevo-usuario").modal("hide");
			return Promise.all([ObtenerUsuariosNoAdministradores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Ocurrió un problema al intentar registrar al nuevo Usuario", "error");
		}
	}).then(([data1]) => {
		usuarioData1 = data1;
		ListarUsuarios(usuarioData1);
	})
})

$(document).on("click", ".btn-ver-usuario", function() {
	const usuario = $(this).data("dataUsuario");
	
	$("#verCodUsua").val(usuario.codUsua);
	$("#verUsuario").val(usuario.nomUsua + " " + usuario.apeUsua);
	$("#verEmailUsua").val(usuario.emailUsua);
	$("#verEstUsua").val(usuario.estUsua == 1 ? "Activo" : "Inactivo");
	$("#verFecNac").val(usuario.fecNac);
	$("#verUltLogin").val(usuario.ultLogin == null ? "No hay inicios de sesión recientes" : usuario.ultLogin);
	$("#verRol").val(usuario.rol.nomRol);
	
	$("#modal-ver-usuario-label").text("Usuario Nro. " + usuario.codUsua.substring(4, 7));
	
	$("#modal-ver-usuario").modal("show");
})

$(document).on("click", ".btn-cambiar-rol", function() {
	const usuario = $(this).data("dataUsuario");
	
	$("#cambiarCodUsua").val(usuario.codUsua);
	$("#cambiarUsuario").val(usuario.nomUsua + " " + usuario.apeUsua);
	$("#cambiarRol").val(usuario.rol.codRol)
	
	$("#msjErrorCambiar").html("").hide();
	
	$("#modal-cambiar-rol").modal("show");
})

$(document).on("click", "#btn-cambiar-rol", function() {
	const codUsua = $("#cambiarCodUsua").val();
	const nuevoRol = $("#cambiarRol").val();
	
	if(nuevoRol == -1){
		$("#msjErrorCambiar").html("Seleccionar un nuevo rol").show();
		return;
	}
	
	fetch(`/usuario/mantenimiento/cambiarRol/${codUsua}/${nuevoRol}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok) {
			Swal.fire("¡Operación Exitosa!", "Rol de Usuario actualizado", "success");
			$("#msjErrorCambiar").html("").hide();
			$("#modal-cambiar-rol").modal("hide");
			return Promise.all([ObtenerUsuariosNoAdministradores()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Ocurrió un problema al intentar actualizar el rol del usuario", "error");
		}
	}).then(([data1]) => {
		usuarioData1 = data1;
		ListarUsuarios(usuarioData1);
	})
})

$(document).on("click", ".btn-bloquear, .btn-desbloquear", function() {
	const usuario = $(this).data("dataUsuario");
	Swal.fire({
		title: usuario.estUsua == 1 ? 
		       "¿Bloquear al usuario?" : 
		       "¿Desbloquear al usuario?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/usuario/mantenimiento/cambiarEstado/${usuario.codUsua}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", usuario.estUsua == 1 ? "Usuario Bloqueado" : "Usuario desbloqueado" , "success");
			        $("#msjErrorCambiar").html("").hide();
			        return Promise.all([ObtenerUsuariosNoAdministradores()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", usuario.estUsua == 1 ? "Ocurrió un problema al intentar bloquear al usuario" :
					                                                           "Ocurrió un problema al intentar desbloquear al usuario", "error");
				}
			}).then(([data1]) => {
				usuarioData1 = data1;
				ListarUsuarios(usuarioData1);
			})
		}
	})
})