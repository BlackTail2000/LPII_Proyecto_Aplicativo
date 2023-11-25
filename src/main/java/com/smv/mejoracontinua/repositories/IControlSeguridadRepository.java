package com.smv.mejoracontinua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.ControlSeguridad;

@Repository
public interface IControlSeguridadRepository extends JpaRepository<ControlSeguridad, Integer> {

}
