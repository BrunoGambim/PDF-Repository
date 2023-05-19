package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.EmailIsAlreadyBeingUsedException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdateClientInfoUseCase {
	private UserRepository userRepository;
	private AuthorizationPolicy authorizationPolicy;
	
	public UpdateClientInfoUseCase(UserRepository userRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public void execute(Long userId, Long clientId, String username, String email) {
		if(userId != clientId) {
			authorizationPolicy.checkIsAdmin(userId);
		}
		if(userRepository.emailIsBeingUsed(email)) {
			throw new EmailIsAlreadyBeingUsedException();
		}
		Client oldData = this.userRepository.findClient(clientId);
		
		Client result = new Client(clientId, username, oldData.getPassword(), email, oldData.getBalance());
		
		this.userRepository.save(result);
	}
}
