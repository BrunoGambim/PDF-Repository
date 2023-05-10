package br.com.brunogambim.pdf_repository.core.user_management.entities;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidEmptyOrNullUserFieldException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;

public abstract class User {
	private Long id;
	private String username;
	private String password;
	private String email;
	private PasswordUpdateCode code;
	
	public User(Long id, String username, String password, String email) {
		this.id = id;
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
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

	private void setUsername(String username) {
		if(username.equals("") || username == null) {
			throw new InvalidEmptyOrNullUserFieldException("username");
		}
		
		this.username = username;
	}

	public String getPassword() {
		if(password.equals("") || password == null) {
			throw new InvalidEmptyOrNullUserFieldException("password");
		}
		
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	
	public void updatePassword(String code, String password) {
		this.validatePasswordUpdateCode(code);
		this.setPassword(password);
	}

	private void setEmail(String email) {
		if(email.equals("") || email == null) {
			throw new InvalidEmptyOrNullUserFieldException("email");
		}
		
		this.email = email;
	}

	public void setPasswordUpdateCode(PasswordUpdateCode code) {
		this.code = code;
	}

	public PasswordUpdateCode getPasswordUpdateCode() {
		return code;
	}
	
	public void validatePasswordUpdateCode(String code) {
		if(code == null) {
			throw new InvalidUpdatePasswordCodeException();
		}
		
		this.code.validateCode(code);
	}

	public String newPasswordUpdateCode() {
		code = new PasswordUpdateCode();
		return code.getCode();
	}
}
