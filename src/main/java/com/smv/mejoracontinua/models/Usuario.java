package com.smv.mejoracontinua.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

	@Id
	@Column(name = "cod_usua", columnDefinition = "CHAR(7)")
	private String codUsua;
	
	@Column(name = "nom_usua", length = 100)
	private String nomUsua;
	
	@Column(name = "ape_usua", length = 100)
	private String apeUsua;
	
	@Column(name = "clv_usua", columnDefinition = "CHAR(60)")
	private String clvUsua;
	
	@Column(name = "email_usua", length = 70)
	private String emailUsua;
	
	@Column(name = "est_usua", columnDefinition = "TINYINT(1)")
	private int estUsua;
	
	@Column(name = "fec_nac", columnDefinition = "DATE")
	private String fecNac;
	
	@Column(name = "fec_registro", columnDefinition = "DATETIME")
	private String fecRegistro;
	
	@Column(name = "ult_login", columnDefinition = "DATETIME")
	private String ultLogin;
	
	@ManyToOne
	@JoinColumn(name = "cod_rol", nullable = false)
	private Rol rol;
}
