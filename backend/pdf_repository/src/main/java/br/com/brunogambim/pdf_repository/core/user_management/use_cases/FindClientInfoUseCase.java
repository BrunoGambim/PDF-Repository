package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindClientInfoUseCase {
	private UserRepository userRepository;

	public FindClientInfoUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public ClientInfo execute(String email) {
		return this.userRepository.findClientByEmail(email).getClientInfo();
	}
}
