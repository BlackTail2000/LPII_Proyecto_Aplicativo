package com.smv.mejoracontinua.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trabajador {

	@Id
	@Column(name = "cod_trab")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codTrab;
	
	@Column(name = "nom_trab", length = 100)
	private String nomTrab;
	
	@Column(name = "ape_trab", length = 100)
	private String apeTrab;
	
	@Column(name = "email_trab", length = 70)
	private String emailTrab;
	
	@Column(name = "tel_trab", length = 30)
	private String telTrab;
	
	@Column(name = "est_trab", columnDefinition = "TINYINT")
	private int estTrab;
	
	@Column(name = "fec_nac", columnDefinition = "DATE")
	private String fecNac;
	
	@Column(name = "fec_contrato", columnDefinition = "DATE")
	private String fecContrato;
	
	@ManyToOne
	@JoinColumn(name = "cod_tipo")
	private Tipo tipo;
	
	@OneToMany(mappedBy = "implementador")
	@JsonIgnore
	private List<InformeGeneralMejoras> informesGeneralesMejoras;
	
	@OneToMany(mappedBy = "solicitante")
	@JsonIgnore
	private List<SolicitudAccionMejoras> solicitudesAccionesMejorasSolicitante;
	
	@OneToMany(mappedBy = "responsable")
	@JsonIgnore
	private List<SolicitudAccionMejoras> solicitudesAccionesMejorasResponsable;
}
