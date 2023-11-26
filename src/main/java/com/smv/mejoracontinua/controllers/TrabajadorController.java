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

import com.smv.mejoracontinua.models.Tipo;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.service.interfaces.ITipoService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Controller
@RequestMapping("/trabajador")
public class TrabajadorController {

	@Autowired
	private IUsuarioService usuarioServ;
	@Autowired
	private ITrabajadorService trabajadorServ;
	@Autowired
	private ITipoService tipoServ;
	
	@GetMapping("/mantenimiento")
	public String mantenimientoTrabajador(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "trabajador/mantenimiento";
	}
	
	@GetMapping("/mantenimiento/listarTrabajadores")
	@ResponseBody
	public List<Trabajador> listarTrabajadores(){
		return trabajadorServ.encontrarTodos();
	}
	
	@GetMapping("/mantenimiento/cargarTipos")
	@ResponseBody
	public List<Tipo> cargarTipos(){
		return tipoServ.encontrarTodosOrdenadosPorNombre();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoTrabajador(@RequestBody Trabajador trabajador){
		trabajador = trabajadorServ.registrarNuevoTrabajador(trabajador);
		return ResponseEntity.ok("Trabajador registrado");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarNuevoTrabajador(@RequestBody Trabajador trabajador){
		trabajador = trabajadorServ.guardarTrabajador(trabajador);
		return ResponseEntity.ok("Trabajador actualizado");
	}
	
	@PutMapping("/mantenimiento/eliminar/{codTrab}")
	public ResponseEntity<String> eliminarTrabajador(@PathVariable("codTrab") int codTrab){
		trabajadorServ.eliminarTrabajador(codTrab);
		return ResponseEntity.ok("Trabajador eliminado");
	}
}
