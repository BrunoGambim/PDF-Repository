package br.com.brunogambim.pdf_repository.core.user_management.repositories;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.User;

public interface UserRepository {
	public void save(User user);
	public Client findClient(Long id);
	public boolean isAdmin(Long id);
}