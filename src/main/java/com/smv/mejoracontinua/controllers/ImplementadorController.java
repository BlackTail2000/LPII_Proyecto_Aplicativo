package com.smv.mejoracontinua.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smv.mejoracontinua.models.Implementador;
import com.smv.mejoracontinua.service.interfaces.IImplementadorService;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/implementador")
public class ImplementadorController {

	@Autowired
	private IUsuarioService usuarioServ;
	@Autowired
	private IImplementadorService implementadorServ;
	
	@GetMapping("/mantenimiento")
	public String mantenimientoImplementadores(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "implementador/mantenimiento";
	}
	
	@GetMapping("/mantenimiento/listarImplementadores")
	@ResponseBody
	public List<Implementador> listarImplementadores(){
		return implementadorServ.encontrarTodos();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoImplementador(@RequestBody Implementador implementador){
		implementador = implementadorServ.registrarNuevoImplementador(implementador);
		return ResponseEntity.ok("Nuevo Implementador registrado");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarImplementador(@RequestBody Implementador implementador){
		implementador = implementadorServ.guardarImplementador(implementador);
		return ResponseEntity.ok("Implementador Actualizado");
	}
	
	@PutMapping("/mantenimiento/eliminar/{codImpl}")
	public ResponseEntity<String> eliminarImplementador(@PathVariable("codImpl") int codImpl){
		implementadorServ.eliminarImplementador(codImpl);
		return ResponseEntity.ok("Implementador eliminado");
	}
}
