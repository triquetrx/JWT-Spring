package com.test.springsecuritydemo.securtiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.springsecuritydemo.model.AuthenticationRequest;
import com.test.springsecuritydemo.model.AuthenticationResponse;
import com.test.springsecuritydemo.securtiy.JwtUserDetailsService;
import com.test.springsecuritydemo.util.JWTUtil;

@RestController
public class RESTController {

	@Autowired
	AuthenticationManager authentication;
	
	@Autowired
	JWTUtil jwt;
	
	@Autowired
	JwtUserDetailsService userDetails;

	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}

	@GetMapping("/admin")
	public String admin() {
		return "Hello Admin";
	}

	@GetMapping("/user")
	public String user() {
		return "Hello User";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest request) throws Exception {
		authenticate(request.getUsername(), request.getPassword());
		final UserDetails userRequest = userDetails.loadUserByUsername(request.getUsername());
		final String token = jwt.generateToken(userRequest);
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	private void authenticate(String username,String password) throws Exception {
		try {			
			authentication
			.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch(DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
