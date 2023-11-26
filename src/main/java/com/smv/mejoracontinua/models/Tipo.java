package com.smv.mejoracontinua.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tipo {

	@Id
	@Column(name = "cod_tipo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codTipo;
	
	@Column(name = "nom_tipo")
	private String nomTipo;
	
	@OneToMany(mappedBy = "tipo")
	@JsonIgnore
	private List<Trabajador> trabajador;
}
