package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Implementador;
import com.smv.mejoracontinua.repositories.IImplementadorRepository;
import com.smv.mejoracontinua.service.interfaces.IImplementadorService;
import com.smv.mejoracontinua.util.FechaUtil;

@Service
public class ImplementadorService implements IImplementadorService {

	@Autowired
	private IImplementadorRepository implementadorRepos;
	
	@Autowired
	private FechaUtil fechaUtil;
	
	@Override
	public List<Implementador> encontrarTodos() {
		return implementadorRepos.findAllByEstImpl(1);
	}

	@Override
	public Implementador guardarImplementador(Implementador implementador) {
		return implementadorRepos.save(implementador);
	}

	@Override
	public Implementador registrarNuevoImplementador(Implementador implementador) {
		implementador.setFecContrato(fechaUtil.obtenerFechaActual());
		return this.guardarImplementador(implementador);
	}

	@Override
	public Implementador encontrarPorCodigo(int codImpl) {
		Implementador implementador = null;
		
		Optional<Implementador> optional = implementadorRepos.findById(codImpl);
		if(optional.isPresent())
			implementador = optional.get();
		
		return implementador;
	}

	@Override
	public void eliminarImplementador(int codImpl) {
		Implementador implementador = this.encontrarPorCodigo(codImpl);
		implementador.setEstImpl(0);
		this.guardarImplementador(implementador);
	}

	@Override
	public List<Implementador> encontrarPorNombresOApellidosYPorSuEstado(String nomApe, int estImpl) {
		return implementadorRepos.findAllByNomImplContainingOrApeImplContainingAndEstImpl(nomApe, nomApe, estImpl);
	}

}
