package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Rol;
import com.smv.mejoracontinua.repositories.IRolRepository;
import com.smv.mejoracontinua.service.interfaces.IRolService;

@Service
public class RolService implements IRolService {
	
	@Autowired
	private IRolRepository rolRepos;

	@Override
	public List<Rol> listarRolesOrdenadosPorNombre() {
		return rolRepos.findAllByOrderByNomRol();
	}

	@Override
	public Rol encontrarPorCodigo(int codRol) {
		Rol rol = null;
		
		Optional<Rol> optional = rolRepos.findById(codRol);
		if(optional.isPresent())
			rol = optional.get();
		
		return rol;
	}

}
