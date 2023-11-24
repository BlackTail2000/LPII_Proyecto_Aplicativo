package com.smv.mejoracontinua.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smv.mejoracontinua.models.Rol;
import com.smv.mejoracontinua.service.interfaces.IRolService;

@Controller
@RequestMapping("/rol")
public class RolController {

	@Autowired
	private IRolService rolServ;
	
	@GetMapping("/buscar/todos/ordenar/porNombre")
	@ResponseBody
	public List<Rol> listarRolesOrdenadosPorNombre(){
		return rolServ.listarRolesOrdenadosPorNombre();
	}
}
