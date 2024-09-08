package com.example.Quizz.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Quizz.DAO.utilisateurRepository;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
	
	@Autowired
	private utilisateurRepository userRepo;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean 
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((requests)->requests.requestMatchers("/utilisateur/authentifier").permitAll()
				.requestMatchers("/utilisateur/creer").permitAll()
				.requestMatchers("/utilisateur/changer_mdp").permitAll()
				//.requestMatchers("/utilisateur/afficheAll").hasAuthority("admin")
				.anyRequest().authenticated());
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
		http.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions
						.sameOrigin()
					)
				);
		http.authenticationProvider(authenticationProvider());//itoooooo
		http.csrf(csrf -> csrf.disable());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//http.httpBasic();
		return http.build();
	}
	
	/*@Bean
	UserDetailsService userDetailsService() {
		UserDetails user1=User.withUsername("user1")
				.password("{noop}12345")
				.roles("USER")
				.build();
		UserDetails admin=User.withUsername("admin")
				.password("{noop}12345")
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user1, admin);
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepo.findByMail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		//authProvider.setPasswordEncoder(null);
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
}
