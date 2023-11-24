package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Usuario;

import net.sf.jasperreports.engine.JRException;

public interface IUsuarioService {

	//Obtener Listados de Usuarios
	List<Usuario> encontrarTodos();
	List<Usuario> encontrarTodosExceptoUnRol(String nomRol);
	List<Usuario> encontrarTodosOrdenadosPorRolYCodigo();
	
	//Obtener un único Usuario
	Usuario encontrarPorCodigo(String codUsua);
	Usuario obtenerUsuarioLogueado();
	
	//Operaciones CRUD
	Usuario guardarUsuario(Usuario usuario);
	Usuario registrarNuevoUsuario(Usuario usuario);
	void cambiarRolDeUsuario(String codUsua, int codRol);
	void cambiarEstadoDeUsuario(String codUsua);
	
	//Operaciones de UserDetailsManager
	Usuario crearNuevoUserDetails(Usuario usuario);
	Usuario actualizarUserDetails(Usuario usuario);
	void eliminarUserDetails(Usuario usuario);
	
	//Generar Reportes
	byte[] generarReporteUsuarios() throws JRException;
	
	//Otros
	String obtenerCodUsuaAutoIncrement();
}
