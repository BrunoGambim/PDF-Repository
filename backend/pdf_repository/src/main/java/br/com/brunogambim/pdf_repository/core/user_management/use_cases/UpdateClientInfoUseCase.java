package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdateClientInfoUseCase {
	private UserRepository userRepository;
	private AuthorizationPolicy authorizationPolicy;
	
	public UpdateClientInfoUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository);
	}
	
	public void execute(Long userId, Long clientId, String username, String email) {
		if(userId != clientId) {
			authorizationPolicy.CheckIsAdminAuthorization(userId);
		}
		Client oldData = this.userRepository.findClient(clientId);
		
		Client result = new Client(clientId, username, oldData.getPassword(), email,
				oldData.getOwnedPDFList(), oldData.getHasAccessPDFList(), oldData.getBalance());
		
		this.userRepository.save(result);
	}
}
