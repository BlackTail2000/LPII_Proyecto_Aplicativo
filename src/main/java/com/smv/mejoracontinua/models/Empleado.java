package com.smv.mejoracontinua.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Empleado {
	
	@Id
	@Column(name = "cod_emp")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codEmp;
	
	@Column(name = "nom_emp", length = 100)
	private String nomEmp;
	
	@Column(name = "ape_emp", length = 100)
	private String apeEmp;
	
	@Column(name = "email_emp", length = 70)
	private String emailEmp;
	
	@Column(name = "tel_emp", length = 30)
	private String telEmp;
	
	@Column(name = "est_emp", columnDefinition = "TINYINT")
	private int estEmp;
	
	@Column(name = "fec_nac", columnDefinition = "DATE")
	private String fecNac;
	
	@Column(name = "fec_contrato", columnDefinition = "DATE")
	private String fecContrato;
}
