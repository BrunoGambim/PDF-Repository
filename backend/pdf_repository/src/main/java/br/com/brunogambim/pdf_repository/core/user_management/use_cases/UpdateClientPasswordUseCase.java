package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdateClientPasswordUseCase {
	private UserRepository userRepository;
	private PasswordEncripterGateway passwordEncripterGateway;
	
	public UpdateClientPasswordUseCase(UserRepository userRepository, PasswordEncripterGateway passwordEncripterGateway) {
		this.userRepository = userRepository;
		this.passwordEncripterGateway = passwordEncripterGateway;
	}
	
	public void execute(String email, String password, String code) {
		Client oldData = this.userRepository.findClientByEmail(email);
		oldData.validateUpdatePasswordCode(code);
		Client result = new Client(oldData.getId(), oldData.getUsername(),
				passwordEncripterGateway.encript(password), oldData.getEmail(), oldData.getBalance());
		
		this.userRepository.save(result);
	}
}
