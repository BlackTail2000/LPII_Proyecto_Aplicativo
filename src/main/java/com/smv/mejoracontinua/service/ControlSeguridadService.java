package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.ControlSeguridad;
import com.smv.mejoracontinua.models.Implementador;
import com.smv.mejoracontinua.repositories.IControlSeguridadRepository;
import com.smv.mejoracontinua.service.interfaces.IControlSeguridadService;
import com.smv.mejoracontinua.service.interfaces.IImplementadorService;
import com.smv.mejoracontinua.util.FechaUtil;

@Service
public class ControlSeguridadService implements IControlSeguridadService {

	@Autowired
	private IControlSeguridadRepository controlSeguridadRepos;
	@Autowired
	private IImplementadorService implementadorServ;
	
	@Autowired
	private FechaUtil fechaUtil;
	
	@Override
	public List<ControlSeguridad> encontrarTodos() {
		return controlSeguridadRepos.findAll();
	}

	@Override
	public ControlSeguridad guardarControlSeguridad(ControlSeguridad controlSeguridad) {
		return controlSeguridadRepos.save(controlSeguridad);
	}

	@Override
	public ControlSeguridad registrarControlSeguridad(ControlSeguridad controlSeguridad) {
		controlSeguridad.setFechaImpl(fechaUtil.obtenerFechaActual());
		controlSeguridad.setResponsable(null);
		return this.guardarControlSeguridad(controlSeguridad);
	}

	@Override
	public ControlSeguridad actualizarControlSeguridad(ControlSeguridad controlSeguridad) {
		ControlSeguridad controlActualizado = this.encontrarPorCodigo(controlSeguridad.getCodControl());
		controlActualizado.setNomControl(controlSeguridad.getNomControl());
		controlActualizado.setCategoria(controlSeguridad.getCategoria());
		controlActualizado.setDesControl(controlSeguridad.getDesControl());
		return this.guardarControlSeguridad(controlActualizado);
	}

	@Override
	public ControlSeguridad encontrarPorCodigo(int codControl) {
		ControlSeguridad control = null;
		
		Optional<ControlSeguridad> optional = controlSeguridadRepos.findById(codControl);
		if(optional.isPresent())
			control = optional.get();
		
		return control;
	}

	@Override
	public void eliminarControlSeguridad(int codControl) {
		controlSeguridadRepos.deleteById(codControl);
	}

	@Override
	public ControlSeguridad asignarImplementador(int codControl, int codImpl) {
		ControlSeguridad controlSeguridad = this.encontrarPorCodigo(codControl);
		Implementador implementador = implementadorServ.encontrarPorCodigo(codImpl);
		controlSeguridad.setResponsable(implementador);
		return this.guardarControlSeguridad(controlSeguridad);
	}

	@Override
	public ControlSeguridad desasignarImplementador(int codControl) {
		ControlSeguridad controlSeguridad = this.encontrarPorCodigo(codControl);
		controlSeguridad.setResponsable(null);
		return this.guardarControlSeguridad(controlSeguridad);
	}

}
