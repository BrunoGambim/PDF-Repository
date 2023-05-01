package br.com.brunogambim.pdf_repository.api.v1.security.entities;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Admin;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public enum Roles {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENT");

	private int code;
	private String description;
	
	private Roles(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Roles fromClass(Object user) {
		if(user.getClass() == Client.class) {
			return Roles.CLIENT;
		} else if(user.getClass() == Admin.class) {
			return Roles.ADMIN;
		}
		throw new IllegalArgumentException("Invalid argument!");
	}
}
