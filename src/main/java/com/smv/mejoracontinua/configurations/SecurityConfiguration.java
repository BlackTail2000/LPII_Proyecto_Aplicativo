package com.smv.mejoracontinua.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.smv.mejoracontinua.models.Usuario;
import com.smv.mejoracontinua.service.interfaces.IUsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private IUsuarioService usuarioServ;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> {
			csrf.disable();
		});
		
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("usuario/mantenimiento/**").hasRole("Administrador");
			auth.requestMatchers("implementador/mantenimiento/**").hasRole("Activador");
			auth.requestMatchers("controlSeguridad/mantenimiento/**").hasRole("Activador");
			auth.anyRequest().authenticated();
		});
		
		http.formLogin(login -> {
			login.loginPage("/login");
			login.usernameParameter("codUsua");
			login.passwordParameter("clvUsua");
			login.loginProcessingUrl("/do-login");
			login.defaultSuccessUrl("/usuario/registrarLogin", true).permitAll();
		});
		
		http.logout(logout -> {
			logout.logoutUrl("/logout");
			logout.logoutSuccessUrl("/login?logout").permitAll();
		});
		
		return http.build();
	}
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		List<UserDetails> listaUserDetails = new ArrayList<UserDetails>();
		
		for(Usuario item: usuarioServ.encontrarTodos()) {
			if(item.getEstUsua() == 1) {
				UserDetails userDetails = User.builder()
						.username(item.getCodUsua())
						.password(item.getClvUsua())
						.roles(item.getRol().getNomRol())
						.build();
				
				listaUserDetails.add(userDetails);
			}
		}
		
		return new InMemoryUserDetailsManager(listaUserDetails);
	}
}
