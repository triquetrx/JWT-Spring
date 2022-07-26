package com.test.springsecuritydemo.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	

	private static final long serialVersionUID = 8807674648730238680L;
	private final String jwttoken;
	
	public String getJwttoken() {
		return jwttoken;
	}

	public AuthenticationResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}
	
}
