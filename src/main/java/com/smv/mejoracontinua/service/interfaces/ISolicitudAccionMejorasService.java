package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.SolicitudAccionMejoras;

import net.sf.jasperreports.engine.JRException;

public interface ISolicitudAccionMejorasService {
	
	List<SolicitudAccionMejoras> encontrarTodos();
	List<SolicitudAccionMejoras> encontrarTodosPorNumSoli(int numSoli);
	List<SolicitudAccionMejoras> encontrarTodosOrdenadosPorNumSoli();
	
	SolicitudAccionMejoras encontrarPorNumSoli(int numSoli);

	SolicitudAccionMejoras guardarSolicitud(SolicitudAccionMejoras solicitud);
	SolicitudAccionMejoras generarNuevaSolicitud(SolicitudAccionMejoras solicitud);
	
	byte[] generarSolicitudMejora(int numSoli) throws JRException;
}
