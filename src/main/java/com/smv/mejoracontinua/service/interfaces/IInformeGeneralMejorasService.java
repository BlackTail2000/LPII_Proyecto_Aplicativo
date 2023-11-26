package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.InformeGeneralMejoras;

import net.sf.jasperreports.engine.JRException;

public interface IInformeGeneralMejorasService {
	
	List<InformeGeneralMejoras> encontrarTodosPorNumInforme(int numInforme);

	InformeGeneralMejoras encontrarPorNumSoli(int numSoli);
	InformeGeneralMejoras guardarInforme(InformeGeneralMejoras informe);
	InformeGeneralMejoras generarNuevoInforme(InformeGeneralMejoras informe);
	
	byte[] generarInformeGeneralMejora(int numSoli) throws JRException;
}
