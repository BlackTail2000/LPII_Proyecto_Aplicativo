package com.smv.mejoracontinua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Controller
public class HomeController {
	
	@Autowired
	private IUsuarioService usuarioServ;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", usuarioServ.obtenerUsuarioLogueado());
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
