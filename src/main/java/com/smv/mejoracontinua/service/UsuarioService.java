package com.smv.mejoracontinua.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Rol;
import com.smv.mejoracontinua.models.Usuario;
import com.smv.mejoracontinua.repositories.IUsuarioRepository;
import com.smv.mejoracontinua.service.interfaces.IRolService;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;
import com.smv.mejoracontinua.util.FechaUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepos;
	@Autowired
	private IRolService rolServ;
	
	@Autowired
	@Lazy
	private UserDetailsManager userDetailsManager;
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FechaUtil fechaUtil;

	@Override
	public List<Usuario> encontrarTodos() {
		return usuarioRepos.findAll();
	}
	
	@Override
	public Usuario encontrarPorCodigo(String codUsua) {
		Usuario usuario = null;
		Optional<Usuario> optional = usuarioRepos.findById(codUsua);
		if(optional.isPresent())
			usuario = optional.get();
		
		return usuario;
	}

	@Override
	public Usuario obtenerUsuarioLogueado() {
		Usuario usuario = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		if(userDetails!=null)
			usuario = this.encontrarPorCodigo(userDetails.getUsername());
			
		return usuario;
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepos.save(usuario);
	}

	@Override
	public Usuario actualizarUserDetails(Usuario usuario) {
		UserDetails userDetails = userDetailsManager.loadUserByUsername(usuario.getCodUsua());
		
		userDetails = User.withUserDetails(userDetails)
				.password(usuario.getClvUsua())
				.roles(usuario.getRol().getNomRol())
				.build();
		
		userDetailsManager.updateUser(userDetails);
		this.guardarUsuario(usuario);
		
		return usuario;
	}

	@Override
	public String obtenerCodUsuaAutoIncrement() {
		String codUsua = "USUA001";
		if(usuarioRepos.findMaxCodUsua() != null) {
			int codUsuaInt = Integer.parseInt(usuarioRepos.findMaxCodUsua().substring(4, 7));
			codUsuaInt++;
			if(codUsuaInt < 10)
				codUsua = "USUA00";
			else if(codUsuaInt < 100)
				codUsua = "USUA0";
			else
				codUsua = "USUA";
			
			codUsua+=codUsuaInt;
		}
		
		return codUsua;
	}

	@Override
	public List<Usuario> encontrarTodosExceptoUnRolOrdenadosPorCodigo(String nomRol) {
		return usuarioRepos.findAllByNomRolNotOrderByCodUsuaAsc(nomRol);
	}

	@Override
	public Usuario registrarNuevoUsuario(Usuario usuario) {
		usuario.setCodUsua(this.obtenerCodUsuaAutoIncrement());
		usuario.setClvUsua(passwordEncoder.encode(usuario.getClvUsua()));
		usuario.setFecRegistro(fechaUtil.obtenerFechaConHoraActual());
		
		usuario = this.guardarUsuario(usuario);
		usuario = this.crearNuevoUserDetails(usuario);
		
		return usuario;
	}

	@Override
	public Usuario crearNuevoUserDetails(Usuario usuario) {
		UserDetails userDetails = User.builder()
				.username(usuario.getCodUsua())
				.password(usuario.getClvUsua())
				.roles(usuario.getRol().getNomRol())
				.build();
		
		userDetailsManager.createUser(userDetails);
		
		return usuario;
	}

	@Override
	public void cambiarRolDeUsuario(String codUsua, int codRol) {
		Usuario usuario = this.encontrarPorCodigo(codUsua);
		Rol rol = rolServ.encontrarPorCodigo(codRol);
		usuario.setRol(rol);
		usuario = this.actualizarUserDetails(usuario);
	}

	@Override
	public void cambiarEstadoDeUsuario(String codUsua) {
		Usuario usuario = this.encontrarPorCodigo(codUsua);
		if(usuario.getEstUsua() == 1) {
			usuario.setEstUsua(0);
			this.eliminarUserDetails(usuario);
		} else {
			usuario.setEstUsua(1);
			usuario = this.crearNuevoUserDetails(usuario);
		}
		
		usuario = this.guardarUsuario(usuario);
	}

	@Override
	public void eliminarUserDetails(Usuario usuario) {
		userDetailsManager.deleteUser(usuario.getCodUsua());
	}

	@Override
	public List<Usuario> encontrarTodosOrdenadosPorRolYCodigo() {
		return usuarioRepos.findAllByOrderByNomRolAscAndCodUsuaAsc();
	}

	@Override
	public byte[] generarReporteUsuarios() throws JRException {
		List<Usuario> usuarios = this.encontrarTodosOrdenadosPorRolYCodigo();
		
		InputStream inputStream = getClass().getResourceAsStream("/reportes/Reporte_Usuarios.jrxml");
		
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usuarios);
		
		Map<String, Object> parametros = new HashMap<>();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

}
