package br.com.brunogambim.pdf_repository.api.v1.security.entities;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTSubject {
	String email;
	String role;
	
	public JWTSubject() {
	}
	
	public JWTSubject(String email, String role) {
		this.email = email;
		this.role = role;
	}
	
	public static JWTSubject fromString(String obj)  {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(obj, JWTSubject.class);
		}catch (IOException e) {
	          throw new RuntimeException(e);
	    }
	}
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		}catch (IOException e) {
	          throw new RuntimeException(e);
	    }
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
