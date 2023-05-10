package br.com.brunogambim.pdf_repository.database.mysql.models;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Admin;
import br.com.brunogambim.pdf_repository.core.user_management.entities.User;
import jakarta.persistence.Entity;

@Entity(name = "admin")
public class AdminModel extends UserModel{

	public AdminModel() {
	}
	
	public AdminModel(Admin admin) {
		this(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getEmail(),
				new UpdatePasswordCodeModel(admin.getPasswordUpdateCode()));
	}
	
	public AdminModel(Long id, String username, String password, String email, UpdatePasswordCodeModel codeModel) {
		super(id, username, password, email, codeModel);
	}

	public User toEntity() {
		Admin admin = new Admin(getId(), getUsername(), getPassword(), getEmail());
		admin.setPasswordUpdateCode(this.getCode().toEntity());
		return admin;
	}
}
