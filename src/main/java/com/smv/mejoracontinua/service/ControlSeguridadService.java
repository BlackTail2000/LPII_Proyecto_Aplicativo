package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.ControlSeguridad;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.repositories.IControlSeguridadRepository;
import com.smv.mejoracontinua.service.interfaces.IControlSeguridadService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;
import com.smv.mejoracontinua.util.FechaUtil;

@Service
public class ControlSeguridadService implements IControlSeguridadService {

	@Autowired
	private IControlSeguridadRepository controlSeguridadRepos;
	@Autowired
	private ITrabajadorService trabajadorServ;
	
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
		controlSeguridad.setTrabajador(null);
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
	public ControlSeguridad asignarImplementador(int codControl, int codTrab) {
		ControlSeguridad controlSeguridad = this.encontrarPorCodigo(codControl);
		Trabajador trabajador = trabajadorServ.encontrarPorCodigo(codTrab);
		controlSeguridad.setTrabajador(trabajador);
		return this.guardarControlSeguridad(controlSeguridad);
	}

	@Override
	public ControlSeguridad desasignarImplementador(int codControl) {
		ControlSeguridad controlSeguridad = this.encontrarPorCodigo(codControl);
		controlSeguridad.setTrabajador(null);
		return this.guardarControlSeguridad(controlSeguridad);
	}

}
