package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Implementador;

public interface IImplementadorService {

	List<Implementador> encontrarTodos();
	List<Implementador> encontrarPorNombresOApellidosYPorSuEstado(String nomApe, int estImpl);
	
	Implementador encontrarPorCodigo(int codImpl);
	
	Implementador guardarImplementador(Implementador implementador);
	Implementador registrarNuevoImplementador(Implementador implementador);
	void eliminarImplementador(int codImpl);
}
