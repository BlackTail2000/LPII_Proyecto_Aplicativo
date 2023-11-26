package com.smv.mejoracontinua.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Tipo;
import com.smv.mejoracontinua.models.Trabajador;
import com.smv.mejoracontinua.repositories.ITrabajadorRepository;
import com.smv.mejoracontinua.service.interfaces.ITipoService;
import com.smv.mejoracontinua.service.interfaces.ITrabajadorService;
import com.smv.mejoracontinua.util.FechaUtil;

@Service
public class TrabajadorService implements ITrabajadorService {
	
	@Autowired
	private ITrabajadorRepository trabajadorRepos;
	@Autowired
	private ITipoService tipoServ;
	
	@Autowired
	private FechaUtil fechaUtil;

	@Override
	public List<Trabajador> encontrarTodos() {
		return trabajadorRepos.findAllByEstTrabOrderByCodTrab(1);
	}

	@Override
	public Trabajador encontrarPorCodigo(int codTrab) {
		Trabajador trabajador = null;
		
		Optional<Trabajador> optional = trabajadorRepos.findById(codTrab);
		if(optional.isPresent())
			trabajador = optional.get();
		
		return trabajador;
	}

	@Override
	public Trabajador guardarTrabajador(Trabajador trabajador) {
		Tipo tipo = tipoServ.encontrarPorCodigo(trabajador.getTipo().getCodTipo());
		trabajador.setTipo(tipo);
		return trabajadorRepos.save(trabajador);
	}

	@Override
	public Trabajador registrarNuevoTrabajador(Trabajador trabajador) {
		trabajador.setFecContrato(fechaUtil.obtenerFechaActual());
		Tipo tipo = tipoServ.encontrarPorCodigo(trabajador.getTipo().getCodTipo());
		trabajador.setTipo(tipo);
		return this.guardarTrabajador(trabajador);
	}

	@Override
	public void eliminarTrabajador(int codTrab) {
		Trabajador trabajador = this.encontrarPorCodigo(codTrab);
		trabajador.setEstTrab(0);
		this.guardarTrabajador(trabajador);
	}

	@Override
	public List<Trabajador> busquedaTrabajadores_1(String nomApe, int estTrab, String nomTipo) {
		return trabajadorRepos.findAllByNomTrabContainingOrApeTrabContainingAndEstTrabAndTipo_NomTipo(nomApe, nomApe, estTrab, nomTipo);
	}

	@Override
	public List<Trabajador> buscarSolounTipoTrabajador(String nomApe, int estTrab, String nomTipo) {
		List<Trabajador> implementadores = this.busquedaTrabajadores_1(nomApe, estTrab, nomTipo);
		
		Iterator<Trabajador> iterator = implementadores.iterator();
		while(iterator.hasNext()) {
			Trabajador trabajador = iterator.next();
			if(!trabajador.getTipo().getNomTipo().equals(nomTipo))
				iterator.remove();
		}
		
		return implementadores;
	}

}
