package com.smv.mejoracontinua.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smv.mejoracontinua.models.InformeGeneralMejoras;
import com.smv.mejoracontinua.models.SolicitudAccionMejoras;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.service.interfaces.IInformeGeneralMejorasService;
import com.smv.mejoracontinua.service.interfaces.ISolicitudAccionMejorasService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/solicitudAccionMejoras")
public class SolicitudAccionMejorasController {

	@Autowired
	private IUsuarioService usuarioServ;
	@Autowired
	private ITrabajadorService trabajadorServ;
	@Autowired
	private ISolicitudAccionMejorasService solicitudServ;
	@Autowired
	private IInformeGeneralMejorasService informeServ;
	
	@GetMapping("/generar")
	public String generarSolicitudAccionMejoras(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "solicitudAccionMejoras/generar";
	}
	
	@GetMapping("/generar/buscar/solicitante/{busqueda}")
	@ResponseBody
	public List<Trabajador> buscarSolicitantes(@PathVariable("busqueda") String busqueda){
		return trabajadorServ.buscarSolounTipoTrabajador(busqueda, 1, "Oficial de Seguridad de la Información");
	}
	
	@GetMapping("/generar/buscar/responsable/{busqueda}")
	@ResponseBody
	public List<Trabajador> buscarResponsable(@PathVariable("busqueda") String busqueda){
		return trabajadorServ.buscarSolounTipoTrabajador(busqueda, 1, "Responsable del Ánalisis");
	}
	
	@PostMapping("/generar")
	public ResponseEntity<String> generarSolicitudAccionMejoras(@RequestBody SolicitudAccionMejoras solicitud){
		solicitud = solicitudServ.generarNuevaSolicitud(solicitud);
		return ResponseEntity.ok("Solicitud de Acción de Mejoras generada.");
	}
	
	@GetMapping("/generar/listarSolicitudes")
	@ResponseBody
	public List<SolicitudAccionMejoras> listarSolicitudes(){
		return solicitudServ.encontrarTodos();
	}
	
	@GetMapping("/generar/buscarInforme/{numSoli}")
	@ResponseBody
	public ResponseEntity<String> buscarInformeGenerarlMejoras(@PathVariable("numSoli") int numSoli) {
		InformeGeneralMejoras informe = null;
		informe = informeServ.encontrarPorNumSoli(numSoli);
		System.out.println(informe);
		
		if(informe == null) {
			return ResponseEntity.badRequest().body("Informe no encontrado");
		} else {
			return ResponseEntity.ok("Informe encontrado");
		}
	}
	
	@GetMapping("/generar/verSolicitud/{numSoli}")
	public ResponseEntity<InputStreamResource> generarSolicitudMejora(@PathVariable("numSoli") int numSoli) throws IOException, JRException {
		byte[] bytesDeReportes = solicitudServ.generarSolicitudMejora(numSoli);
		
		return ResponseEntity
				.ok()
				.contentLength(bytesDeReportes.length)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(new ByteArrayInputStream(bytesDeReportes)));
		
	}
}
