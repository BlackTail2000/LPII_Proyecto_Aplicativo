package com.smv.mejoracontinua.service.interfaces;

import java.util.List;

import com.smv.mejoracontinua.models.Rol;

public interface IRolService {
	
	List<Rol> listarRolesOrdenadosPorNombre();
	Rol encontrarPorCodigo(int codRol);
}
