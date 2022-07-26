package com.test.springsecuritydemo.securtiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity https) throws Exception{
		https.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.anyRequest().authenticated().and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		https.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
		
		return https.build();
		
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
