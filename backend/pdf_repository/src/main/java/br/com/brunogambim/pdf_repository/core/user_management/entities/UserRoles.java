package br.com.brunogambim.pdf_repository.core.user_management.entities;

public enum UserRoles {
	ADMIN(1, "ADMIN"),
	CLIENT(2, "CLIENT");

	private int code;
	private String description;
	
	private UserRoles(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static UserRoles fromClass(Object user) {
		if(user.getClass() == Client.class) {
			return UserRoles.CLIENT;
		} else if(user.getClass() == Admin.class) {
			return UserRoles.ADMIN;
		}
		throw new IllegalArgumentException("Invalid argument!");
	}
}
