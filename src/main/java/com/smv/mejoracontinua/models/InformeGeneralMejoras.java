package com.smv.mejoracontinua.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InformeGeneralMejoras {

	@Id
	@Column(name = "num_informe")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numInforme;
	
	@Column(columnDefinition = "LONGTEXT")
	private String objetivo;
	
	@Column(name = "area_especifica", columnDefinition = "LONGTEXT")
	private String areaEspecifica;
	
	@Column(name = "mejora_implementada", columnDefinition = "LONGTEXT")
	private String mejoraImplementada;
	
	@Column(name = "descripcion_implementacion", columnDefinition = "LONGTEXT")
	private String descripcionImplementacion;
	
	@Column(name = "resultados_prueba", columnDefinition = "LONGTEXT")
	private String resultadosPrueba;
	
	@Column(name = "recomendaciones_adicionales", columnDefinition = "LONGTEXT")
	private String recomendacionesAdicionales;
	
	@Column(columnDefinition = "LONGTEXT")
	private String conclusiones;
	
	@ManyToOne
	@JoinColumn(name = "cod_implementador")
	private Trabajador implementador;
	
	@ManyToOne
	@JoinColumn(name = "num_soli")
	private SolicitudAccionMejoras solicitudAccionMejoras;
}
