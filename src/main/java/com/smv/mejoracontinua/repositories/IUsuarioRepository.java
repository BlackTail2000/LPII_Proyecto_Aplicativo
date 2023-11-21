package com.smv.mejoracontinua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

}
