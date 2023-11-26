package com.smv.mejoracontinua.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.InformeGeneralMejoras;
import com.smv.mejoracontinua.models.SolicitudAccionMejoras;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.repositories.IInformeGeneralMejorasRepository;
import com.smv.mejoracontinua.service.interfaces.IInformeGeneralMejorasService;
import com.smv.mejoracontinua.service.interfaces.ISolicitudAccionMejorasService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class InformeGeneralMejorasService implements IInformeGeneralMejorasService {

	@Autowired
	private IInformeGeneralMejorasRepository informeRepos;
	@Autowired
	private ITrabajadorService trabajadorServ;
	@Autowired
	private ISolicitudAccionMejorasService solicitudServ;
	
	@Override
	public InformeGeneralMejoras encontrarPorNumSoli(int numSoli) {
		return informeRepos.findBySolicitudAccionMejoras_numSoli(numSoli);
	}

	@Override
	public InformeGeneralMejoras guardarInforme(InformeGeneralMejoras informe) {
		return informeRepos.save(informe);
	}

	@Override
	public InformeGeneralMejoras generarNuevoInforme(InformeGeneralMejoras informe) {
		Trabajador implementador = trabajadorServ.encontrarPorCodigo(informe.getImplementador().getCodTrab());
		SolicitudAccionMejoras solicitud = solicitudServ.encontrarPorNumSoli(informe.getSolicitudAccionMejoras().getNumSoli());
		solicitud.setEstSoli("Finalizado");
		solicitud = solicitudServ.guardarSolicitud(solicitud);
		informe.setImplementador(implementador);
		informe.setSolicitudAccionMejoras(solicitud);
		return this.guardarInforme(informe);
	}

	@Override
	public byte[] generarInformeGeneralMejora(int numSoli) throws JRException {
		List<InformeGeneralMejoras> informes = this.encontrarTodosPorNumInforme(numSoli);
		
		InputStream inputStream = getClass().getResourceAsStream("/reportes/ReporteInformeGeneralDeMejoras.jrxml");
		
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(informes);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("numSoli", numSoli);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	@Override
	public List<InformeGeneralMejoras> encontrarTodosPorNumInforme(int numInforme) {
		return informeRepos.findAllBySolicitudAccionMejoras_numSoli(numInforme);
	}

}
