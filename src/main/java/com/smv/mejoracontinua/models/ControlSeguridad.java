package com.smv.mejoracontinua.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Control_Seguridad")
public class ControlSeguridad {

	@Id
	@Column(name = "cod_control")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codControl;
	
	@Column(name = "nom_control", length = 100)
	private String nomControl;
	
	@Column(name = "des_control", columnDefinition = "LONGTEXT")
	private String desControl;
	
	@Column(length = 50)
	private String categoria;
	
	@Column(name = "fecha_impl", columnDefinition = "DATE")
	private String fechaImpl;
	
	@Column(name = "est_control", length = 20)
	private String estControl;
	
	@ManyToOne
	@JoinColumn(name = "cod_implementador")
	private Trabajador trabajador;
}
