package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.EmailIsAlreadyBeingUsedException;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewClientUseCase {
	private UserRepository userRepository;
	private PasswordEncripterGateway passwordEncripterGateway;
	private int STARTING_BALANCE_VALUE = 30;
	
	public SaveNewClientUseCase(UserRepository userRepository, PasswordEncripterGateway passwordEncripterGateway) {
		this.userRepository = userRepository;
		this.passwordEncripterGateway = passwordEncripterGateway;
	}
	
	public Long execute(String username, String email, String password) {
		if(userRepository.emailIsBeingUsed(email)) {
			throw new EmailIsAlreadyBeingUsedException();
		}
		String encriptedPassword = this.passwordEncripterGateway.encript(password);
		return this.userRepository.save(new Client(username, encriptedPassword, email, STARTING_BALANCE_VALUE));
	}
}
