package br.com.brunogambim.pdf_repository.core.user_management.entities;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;

public abstract class User {
	private Long id;
	private String username;
	private String password;
	private String email;
	private UpdatePasswordCode code;
	
	public User(Long id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.code = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUpdatePasswordCode(UpdatePasswordCode code) {
		this.code = code;
	}

	public UpdatePasswordCode getUpdatePasswordCode() {
		return code;
	}
	
	public void validateUpdatePasswordCode(String code) {
		if(code == null) {
			throw new InvalidUpdatePasswordCodeException();
		}
		this.code.validateCode(code);
	}

	public String newUpdatePasswordCode() {
		code = new UpdatePasswordCode();
		return code.getCode();
	}
}
