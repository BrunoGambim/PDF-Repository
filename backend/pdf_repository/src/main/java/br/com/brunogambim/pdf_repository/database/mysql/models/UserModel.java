package br.com.brunogambim.pdf_repository.database.mysql.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@Column(unique=true)
	private String email;
	
	@Embedded
	private UpdatePasswordCodeModel code;
	
	public UserModel() {
	}
	
	public UserModel(Long id, String username, String password, String email, UpdatePasswordCodeModel code) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.code = code;
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
	
	public UpdatePasswordCodeModel getCode() {
		return code;
	}
	
	public void setCode(UpdatePasswordCodeModel code) {
		this.code = code;
	}
}
