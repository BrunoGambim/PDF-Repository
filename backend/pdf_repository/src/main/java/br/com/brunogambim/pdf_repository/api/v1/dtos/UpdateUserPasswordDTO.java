package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UpdateUserPasswordDTO {
	private String email;
	private String password;
	private String code;
	
	public UpdateUserPasswordDTO() {
	}

	public UpdateUserPasswordDTO(String password, String code, String email) {
		super();
		this.password = password;
		this.code = code;
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
