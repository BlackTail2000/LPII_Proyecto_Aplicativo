package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Tipo;
import com.smv.mejoracontinua.repositories.ITipoRepository;
import com.smv.mejoracontinua.service.interfaces.ITipoService;

@Service
public class TipoService implements ITipoService {

	@Autowired
	private ITipoRepository tipoRepos;
	
	@Override
	public List<Tipo> encontrarTodosOrdenadosPorNombre() {
		return tipoRepos.findAllByOrderByNomTipo();
	}

	@Override
	public Tipo encontrarPorCodigo(int codTipo) {
		Tipo tipo = null;
		
		Optional<Tipo> optional = tipoRepos.findById(codTipo);
		if(optional.isPresent())
			tipo = optional.get();
		
		return tipo;
	}

}
