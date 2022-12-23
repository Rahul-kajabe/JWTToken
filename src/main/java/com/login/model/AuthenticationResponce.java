package com.login.model;

public class AuthenticationResponce {

	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public AuthenticationResponce(String jwt) {
		this.jwt=jwt;
		
	}
	
}
