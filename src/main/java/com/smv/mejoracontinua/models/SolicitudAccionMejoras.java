package com.smv.mejoracontinua.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Solicitud_Accion_Mejoras")
public class SolicitudAccionMejoras {

	@Id
	@Column(name = "num_soli")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numSoli;
	
	@Column(name = "fec_soli", columnDefinition = "DATE")
	private String fecSoli;
	
	@ManyToOne
	@JoinColumn(name = "cod_solicitante")
	private Trabajador solicitante;
	
	@Column(name = "nom_proceso", length = 100)
	private String nomProceso;
	
	@Column(name = "fuente_sam", length = 100)
	private String fuenteSam;
	
	@Column(name = "descripcion_no_conformidad", columnDefinition = "LONGTEXT")
	private String descripcionNoConformidad;
	
	@Column(name = "causas_no_conformidad", columnDefinition = "LONGTEXT")
	private String causasNoConformidad;
	
	@Column(name = "acciones_a_tomar", columnDefinition = "LONGTEXT")
	private String accionesATomar;
	
	@ManyToOne
	@JoinColumn(name = "cod_responsable")
	private Trabajador responsable;
	
	@Column(name = "plazo_dias")
	private int plazoDias;

	@Column(name = "est_soli", length = 30)
	private String estSoli;
	
	@OneToMany(mappedBy = "solicitudAccionMejoras")
	@JsonIgnore
	private List<InformeGeneralMejoras> informesGeneralesMejoras;
}
