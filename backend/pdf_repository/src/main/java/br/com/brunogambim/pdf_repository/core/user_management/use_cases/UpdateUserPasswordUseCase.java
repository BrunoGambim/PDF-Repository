package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.User;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdateUserPasswordUseCase {
	private UserRepository userRepository;
	private PasswordEncripterGateway passwordEncripterGateway;
	
	public UpdateUserPasswordUseCase(UserRepository userRepository, PasswordEncripterGateway passwordEncripterGateway) {
		this.userRepository = userRepository;
		this.passwordEncripterGateway = passwordEncripterGateway;
	}
	
	public void execute(String email, String password, String code) {
		User user = this.userRepository.findUserByEmail(email);
		user.validatePasswordUpdateCode(code);
		user.updatePassword(code, passwordEncripterGateway.encript(password));
		this.userRepository.save(user);
	}
}
