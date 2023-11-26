package com.smv.mejoracontinua.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.SolicitudAccionMejoras;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.repositories.ISolicitudAccionMejorasRepository;
import com.smv.mejoracontinua.service.interfaces.ISolicitudAccionMejorasService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;
import com.smv.mejoracontinua.util.FechaUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SolicitudAccionMejorasService implements ISolicitudAccionMejorasService {

	@Autowired
	private ISolicitudAccionMejorasRepository solicitudRepos;
	@Autowired
	private ITrabajadorService trabajadorServ;
	@Autowired
	private FechaUtil fechaUtil;
	
	@Override
	public SolicitudAccionMejoras generarNuevaSolicitud(SolicitudAccionMejoras solicitud) {
		
		Trabajador solicitante = trabajadorServ.encontrarPorCodigo(solicitud.getSolicitante().getCodTrab());
		Trabajador responsable = trabajadorServ.encontrarPorCodigo(solicitud.getResponsable().getCodTrab());
		solicitud.setSolicitante(solicitante);
		solicitud.setResponsable(responsable);
		solicitud.setFecSoli(fechaUtil.obtenerFechaActual());
		
		return this.guardarSolicitud(solicitud);
	}

	@Override
	public SolicitudAccionMejoras guardarSolicitud(SolicitudAccionMejoras solicitud) {
		return solicitudRepos.save(solicitud);
	}

	@Override
	public List<SolicitudAccionMejoras> encontrarTodos() {
		return solicitudRepos.findAll();
	}

	@Override
	public byte[] generarSolicitudMejora(int numSoli) throws JRException {
		List<SolicitudAccionMejoras> solicitudes = this.encontrarTodosPorNumSoli(numSoli);
		
		InputStream inputStream = getClass().getResourceAsStream("/reportes/ReporteSolicitudDeMejora.jrxml");
		
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(solicitudes);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nroSoli", numSoli);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	@Override
	public SolicitudAccionMejoras encontrarPorNumSoli(int numSoli) {
		SolicitudAccionMejoras solicitud = null;
		
		Optional<SolicitudAccionMejoras> optional = solicitudRepos.findById(numSoli);
		if(optional.isPresent())
			solicitud = optional.get();
		
		return solicitud;
	}

	@Override
	public List<SolicitudAccionMejoras> encontrarTodosPorNumSoli(int numSoli) {
		return solicitudRepos.findAllByNumSoli(numSoli);
	}

	@Override
	public List<SolicitudAccionMejoras> encontrarTodosOrdenadosPorNumSoli() {
		return solicitudRepos.findAllByOrderByNumSoli();
	}

}
