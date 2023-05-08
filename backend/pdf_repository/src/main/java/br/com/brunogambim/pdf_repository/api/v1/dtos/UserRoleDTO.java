package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UserRoleDTO {
	private String role;

	public UserRoleDTO(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
