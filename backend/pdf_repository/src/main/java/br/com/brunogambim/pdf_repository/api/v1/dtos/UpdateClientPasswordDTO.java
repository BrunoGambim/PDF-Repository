package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class UpdateClientPasswordDTO {
	private String password;
	private String code;
	
	public UpdateClientPasswordDTO() {
	}

	public UpdateClientPasswordDTO(String password, String code) {
		super();
		this.password = password;
		this.code = code;
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
}
