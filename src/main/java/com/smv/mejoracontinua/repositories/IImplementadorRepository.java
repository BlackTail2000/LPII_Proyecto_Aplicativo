package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Implementador;

@Repository
public interface IImplementadorRepository extends JpaRepository<Implementador, Integer> {

	List<Implementador> findAllByEstImpl(int estImpl);
}
