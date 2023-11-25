package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.ControlSeguridad;

public interface IControlSeguridadService {

	List<ControlSeguridad> encontrarTodos();
	
	ControlSeguridad guardarControlSeguridad(ControlSeguridad controlSeguridad);
	ControlSeguridad registrarControlSeguridad(ControlSeguridad controlSeguridad);
	ControlSeguridad actualizarControlSeguridad(ControlSeguridad controlSeguridad);
	ControlSeguridad asignarImplementador(int codControl, int codImpl);
	ControlSeguridad desasignarImplementador(int codControl);
	void eliminarControlSeguridad(int codControl);
	
	ControlSeguridad encontrarPorCodigo(int codControl);
}
