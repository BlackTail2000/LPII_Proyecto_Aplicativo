package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Trabajador;

@Repository
public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

	List<Trabajador> findAllByEstTrabOrderByCodTrab(int estTrab);
	List<Trabajador> findAllByNomTrabContainingOrApeTrabContainingAndEstTrabAndTipo_NomTipo(String nomTrab, String apeTrab, int estTrab, String nomTipo);
}
