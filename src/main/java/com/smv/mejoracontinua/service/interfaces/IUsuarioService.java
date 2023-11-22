package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Usuario;

public interface IUsuarioService {

	//Obtener Listados de Usuarios
	List<Usuario> encontrarTodos();
	
	//Obtener un único Usuario
	Usuario encontrarPorCodigo(String codUsua);
	Usuario obtenerUsuarioLogueado();
	
	//Operaciones CRUD
	Usuario guardarUsuario(Usuario usuario);
	
	//Operaciones de UserDetailsManager
	Usuario actualizarUserDetails(Usuario usuario);
}
