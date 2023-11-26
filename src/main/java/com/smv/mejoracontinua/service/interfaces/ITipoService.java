package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Tipo;

public interface ITipoService {

	List<Tipo> encontrarTodosOrdenadosPorNombre();
	
	Tipo encontrarPorCodigo(int codTipo);
}
