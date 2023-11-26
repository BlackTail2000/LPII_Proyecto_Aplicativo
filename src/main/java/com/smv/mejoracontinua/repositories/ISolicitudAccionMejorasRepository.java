package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.SolicitudAccionMejoras;

@Repository
public interface ISolicitudAccionMejorasRepository extends JpaRepository<SolicitudAccionMejoras, Integer> {

	List<SolicitudAccionMejoras> findAllByNumSoli(int numSoli);
	List<SolicitudAccionMejoras> findAllByOrderByNumSoli();
}
