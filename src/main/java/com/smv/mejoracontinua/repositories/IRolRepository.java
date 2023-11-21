package com.smv.mejoracontinua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {

}
