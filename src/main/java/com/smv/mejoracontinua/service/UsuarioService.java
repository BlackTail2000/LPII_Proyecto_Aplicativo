package com.smv.mejoracontinua.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.smv.mejoracontinua.models.Usuario;
import com.smv.mejoracontinua.repositories.IUsuarioRepository;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepos;
	
	@Autowired
	@Lazy
	private UserDetailsManager userDetailsManager;

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

}
