package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.time.LocalDateTime;

import br.com.brunogambim.pdf_repository.core.user_management.entities.PasswordUpdateCode;
import jakarta.persistence.Embeddable;

@Embeddable
public class UpdatePasswordCodeModel {
	private String code;
	private LocalDateTime createdAt;
	
	public UpdatePasswordCodeModel() {
	}
	
	public UpdatePasswordCodeModel(PasswordUpdateCode code) {
		if(code != null) {
			this.code = code.toString();
			this.createdAt = code.getCreatedAt();
		}
	}
	
	public UpdatePasswordCodeModel(String code, LocalDateTime createdAt) {
		this.code = code;
		this.createdAt = createdAt;
	}
	
	public PasswordUpdateCode toEntity() {
		return new PasswordUpdateCode(code, createdAt);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
