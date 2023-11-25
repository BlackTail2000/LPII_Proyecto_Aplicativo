package com.smv.mejoracontinua.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smv.mejoracontinua.models.ControlSeguridad;
import com.smv.mejoracontinua.service.interfaces.IControlSeguridadService;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/controlSeguridad")
public class ControlSeguridadController {
	
	@Autowired
	private IUsuarioService usuarioServ;
	@Autowired
	private IControlSeguridadService controlSeguridadServ;

	@GetMapping("/mantenimiento")
	public String mantenimientoControlSeguridad(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "controlSeguridad/mantenimiento";
	}
	
	@GetMapping("/mantenimiento/listarTodos")
	@ResponseBody
	public List<ControlSeguridad> listarControlesDeSeguridad(){
		return controlSeguridadServ.encontrarTodos();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoControl(@RequestBody ControlSeguridad controlSeguridad){
		controlSeguridad = controlSeguridadServ.registrarControlSeguridad(controlSeguridad);
		return ResponseEntity.ok("Nuevo control registrado");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarControl(@RequestBody ControlSeguridad controlSeguridad){
		controlSeguridad = controlSeguridadServ.actualizarControlSeguridad(controlSeguridad);
		return ResponseEntity.ok("Control de Seguridad actualizado");
	}
	
	@DeleteMapping("/mantenimiento/eliminar/{codControl}")
	public ResponseEntity<String> eliminarControl(@PathVariable("codControl") int codControl){
		controlSeguridadServ.eliminarControlSeguridad(codControl);
		return ResponseEntity.ok("Control de Seguridad eliminado");
	}
}
