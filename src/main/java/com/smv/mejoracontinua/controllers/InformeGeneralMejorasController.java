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
@RequestMapping("/informeGeneralMejoras")
public class InformeGeneralMejorasController {

	@Autowired
	private IUsuarioService usuarioServ;
	@Autowired
	private ISolicitudAccionMejorasService solicitudServ;
	@Autowired
	private IInformeGeneralMejorasService informeServ;
	@Autowired
	private ITrabajadorService trabajadorServ;
	
	@GetMapping("/generar")
	public String generarInformeGeneralMejoras(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "informeGeneralMejoras/generar";
	}
	
	@GetMapping("/generar/listarSolicitudes")
	@ResponseBody
	public List<SolicitudAccionMejoras> listarSolicitudes(){
		return solicitudServ.encontrarTodosOrdenadosPorNumSoli();
	}
	
	@GetMapping("/generar/buscar/informe/{numSoli}")
	public ResponseEntity<String> buscarInformeGeneralMejoras(@PathVariable("numSoli") int numSoli){
		InformeGeneralMejoras informe = informeServ.encontrarPorNumSoli(numSoli);
		
		if(informe == null)
			return ResponseEntity.ok("Informe no encontrado");
		else
			return ResponseEntity.ok("Informe encontrado");
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
	
	@PostMapping("/generar")
	public ResponseEntity<String> generarInformeGeneralMejoras(@RequestBody InformeGeneralMejoras informe){
		informe = informeServ.generarNuevoInforme(informe);
		return ResponseEntity.ok("Informe generado");
	}
	
	@GetMapping("/generar/buscar/implementadores/{busqueda}")
	@ResponseBody
	public List<Trabajador> buscarImplementadores(@PathVariable("busqueda") String busqueda){
		return trabajadorServ.buscarSolounTipoTrabajador(busqueda, 1, "Implementador");
	}
	
	@GetMapping("/generar/verInforme/{numSoli}")
	public ResponseEntity<InputStreamResource> generarInformeGeneralMejoras(@PathVariable("numSoli") int numSoli) throws IOException, JRException {
		byte[] bytesDeInforme = informeServ.generarInformeGeneralMejora(numSoli);
		
		return ResponseEntity
				.ok()
				.contentLength(bytesDeInforme.length)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(new ByteArrayInputStream(bytesDeInforme)));
	}
}
