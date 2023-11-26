package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.InformeGeneralMejoras;

@Repository
public interface IInformeGeneralMejorasRepository extends JpaRepository<InformeGeneralMejoras, Integer> {

	InformeGeneralMejoras findBySolicitudAccionMejoras_numSoli(int numSoli);
	
	List<InformeGeneralMejoras> findAllBySolicitudAccionMejoras_numSoli(int numInforme);
}
