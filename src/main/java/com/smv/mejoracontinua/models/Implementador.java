package com.smv.mejoracontinua.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Implementador {

	@Id
	@Column(name = "cod_impl")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codImpl;
	@Column(name = "nom_impl", length = 100)
	private String nomImpl;
	@Column(name = "ape_impl", length = 100)
	private String apeImpl;
	@Column(name = "email_impl", length = 70)
	private String emailImpl;
	@Column(name = "tel_impl", length = 30)
	private String telImpl;
	@Column(name = "est_impl", columnDefinition = "TINYINT")
	private int estImpl;
	@Column(name = "fec_nac", columnDefinition = "DATE")
	private String fecNac;
	@Column(name = "fec_contrato", columnDefinition = "DATE")
	private String fecContrato;
	@OneToMany(mappedBy = "responsable")
	@JsonIgnore
	private List<ControlSeguridad> controlesSeguridad;
}
