package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncriptGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewClientUseCase {
	private UserRepository userRepository;
	private PasswordEncriptGateway passwordEncriptGateway;
	private int STARTING_BALANCE_VALUE = 30;
	
	public SaveNewClientUseCase(UserRepository userRepository, PasswordEncriptGateway passwordEncriptGateway) {
		this.userRepository = userRepository;
		this.passwordEncriptGateway = passwordEncriptGateway;
	}
	
	public void execute(String username, String email, String password) {
		String encriptedPassword = this.passwordEncriptGateway.encript(password);
		this.userRepository.save(new Client(username, encriptedPassword, email, STARTING_BALANCE_VALUE));
	}
}
