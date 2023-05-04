package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UpdatePasswordCodeDTO {
	private String email;
	
	public UpdatePasswordCodeDTO() {
	}

	public UpdatePasswordCodeDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
