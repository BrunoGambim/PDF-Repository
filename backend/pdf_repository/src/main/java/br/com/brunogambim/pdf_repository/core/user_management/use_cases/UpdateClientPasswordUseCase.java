package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncriptGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdateClientPasswordUseCase {
	private UserRepository userRepository;
	private PasswordEncriptGateway passwordEncriptGateway;
	private AuthorizationPolicy authorizationPolicy;
	
	public UpdateClientPasswordUseCase(UserRepository userRepository, PasswordEncriptGateway passwordEncriptGateway) {
		this.userRepository = userRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository);
		this.passwordEncriptGateway = passwordEncriptGateway;
	}
	
	public void execute(Long userId, Long clientId, String password) {
		if(userId != clientId) {
			authorizationPolicy.CheckIsAdminAuthorization(userId);
		}
		Client oldData = this.userRepository.findClient(clientId);
		
		Client result = new Client(clientId, oldData.getUsername(),
				passwordEncriptGateway.encript(password), oldData.getEmail(),
				oldData.getOwnedPDFList(), oldData.getHasAccessPDFList(), oldData.getBalance());
		
		this.userRepository.save(result);
	}
}
