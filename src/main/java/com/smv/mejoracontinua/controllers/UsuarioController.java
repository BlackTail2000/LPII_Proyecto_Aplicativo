package com.smv.mejoracontinua.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smv.mejoracontinua.models.Usuario;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;
import com.smv.mejoracontinua.util.FechaUtil;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private FechaUtil fechaUtil;
	
	@Autowired
	private IUsuarioService usuarioServ;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/registrarLogin")
	public String registrarLogin() {
		
		Usuario usuario = usuarioServ.obtenerUsuarioLogueado();
		usuario.setUltLogin(fechaUtil.obtenerFechaConHoraActual());
		usuario = usuarioServ.guardarUsuario(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/tuCuenta")
	public String tuCuenta(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "tu_cuenta";
	}
	
	@PutMapping("/actualizarCuenta")
	public ResponseEntity<Usuario> actualizarCuenta(@RequestBody Usuario usuarioEditado){
		try {
			Usuario usuarioLogueado = usuarioServ.obtenerUsuarioLogueado();
			usuarioLogueado.setNomUsua(usuarioEditado.getNomUsua());
			usuarioLogueado.setApeUsua(usuarioEditado.getApeUsua());
			usuarioLogueado.setEmailUsua(usuarioEditado.getEmailUsua());
			usuarioLogueado.setFecNac(usuarioEditado.getFecNac());
			usuarioServ.guardarUsuario(usuarioLogueado);
			
			return ResponseEntity.ok(usuarioEditado);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(usuarioEditado);
		}
	}
	
	@GetMapping("/cambiarContrasena")
	public String cambiarContrasena(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "cambiar_contrasena";
	}
	
	@PutMapping("/actualizarContrasena/{clvUsua}")
	public ResponseEntity<String> actualizarContrasena(@PathVariable("clvUsua") String clvUsua){
		try {
			Usuario usuarioLogueado = usuarioServ.obtenerUsuarioLogueado();
			usuarioLogueado.setClvUsua(passwordEncoder.encode(clvUsua));
			usuarioLogueado = usuarioServ.actualizarUserDetails(usuarioLogueado);
			
			return ResponseEntity.ok("Contraseña actualizada");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo actualizar la contraseña.");
		}
	}
	
	@GetMapping("/mantenimiento")
	public String mantenimientoUsuario(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "usuario/mantenimiento";
	}
	
	@GetMapping("/mantenimiento/listarUsuarios")
	@ResponseBody
	public List<Usuario> listarUsuariosNoAdministradores(){
		return usuarioServ.encontrarTodosExceptoUnRol("Administrador");
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoUsuario(@RequestBody Usuario usuario){
		usuarioServ.registrarNuevoUsuario(usuario);
		return ResponseEntity.ok("Usuario registrado");
	}
	
	@PutMapping("/mantenimiento/cambiarRol/{codUsua}/{codRol}")
	public ResponseEntity<String> cambiarRol(@PathVariable("codUsua") String codUsua, @PathVariable("codRol") int codRol){
		usuarioServ.cambiarRolDeUsuario(codUsua, codRol);
		return ResponseEntity.ok("Rol de usuario actualizado");
	}
	
	@PutMapping("/mantenimiento/cambiarEstado/{codUsua}")
	public ResponseEntity<String> cambiarEstado(@PathVariable("codUsua") String codUsua){
		usuarioServ.cambiarEstadoDeUsuario(codUsua);
		return ResponseEntity.ok("Estado de usuario actualizado.");
	}
	
	@GetMapping("/mantenimiento/verReporteUsuarios")
	public ResponseEntity<InputStreamResource> generarReporteDeUsuarios() throws IOException, JRException {
		byte[] bytesDeReportes = usuarioServ.generarReporteUsuarios();
		
		return ResponseEntity
				.ok()
				.contentLength(bytesDeReportes.length)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(new ByteArrayInputStream(bytesDeReportes)));
	}
}
