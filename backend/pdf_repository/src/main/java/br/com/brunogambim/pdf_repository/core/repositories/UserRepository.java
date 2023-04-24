package br.com.brunogambim.pdf_repository.core.repositories;

import br.com.brunogambim.pdf_repository.core.entities.Client;
import br.com.brunogambim.pdf_repository.core.entities.User;

public interface UserRepository {
	public void save(User user);
	public Client getClient(Long id);
}
