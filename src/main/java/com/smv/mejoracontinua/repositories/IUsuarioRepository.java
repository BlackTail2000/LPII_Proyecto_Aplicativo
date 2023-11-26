package com.smv.mejoracontinua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smv.mejoracontinua.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

	@Query("Select u From Usuario u Where u.rol.nomRol != ?1")
	List<Usuario> findAllByNomRolNotOrderByCodUsuaAsc(String nomRol);
	
	@Query("Select Max(u.codUsua) From Usuario u")
	String findMaxCodUsua();
	
	@Query("Select u From Usuario u Order By u.rol.nomRol Asc, u.codUsua Asc")
	List<Usuario> findAllByOrderByNomRolAscAndCodUsuaAsc();
}
