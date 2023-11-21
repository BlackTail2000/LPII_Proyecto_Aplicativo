package com.smv.mejoracontinua.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rol {

	@Id
	@Column(name = "cod_rol")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRol;
	
	@Column(name = "nom_rol", length = 50, nullable = false)
	private String nomRol;
	
	@OneToMany(mappedBy = "rol")
	@JsonIgnore
	private List<Usuario> usuarios;
}
