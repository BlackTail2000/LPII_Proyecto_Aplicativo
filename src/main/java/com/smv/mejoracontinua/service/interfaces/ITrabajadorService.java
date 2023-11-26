package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Trabajador;

public interface ITrabajadorService {

	List<Trabajador> encontrarTodos();
	List<Trabajador> busquedaTrabajadores_1(String nomApe, int estTrab, String nomTipo);
	
	List<Trabajador> buscarSolounTipoTrabajador(String nomApe, int estTrab, String nomTipo);
	
	Trabajador encontrarPorCodigo(int codTrab);
	
	Trabajador guardarTrabajador(Trabajador trabajador);
	Trabajador registrarNuevoTrabajador(Trabajador trabajador);
	void eliminarTrabajador(int codTrab);
}
