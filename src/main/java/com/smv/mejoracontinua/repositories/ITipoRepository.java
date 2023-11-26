package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Tipo;

@Repository
public interface ITipoRepository extends JpaRepository<Tipo, Integer> {

	List<Tipo> findAllByOrderByNomTipo();
}
