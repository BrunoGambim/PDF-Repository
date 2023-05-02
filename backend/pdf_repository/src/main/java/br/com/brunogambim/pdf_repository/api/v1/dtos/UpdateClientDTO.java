package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UpdateClientDTO {
	private String username;
	private String email;
	
	public UpdateClientDTO() {
	}
	
	public UpdateClientDTO(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
