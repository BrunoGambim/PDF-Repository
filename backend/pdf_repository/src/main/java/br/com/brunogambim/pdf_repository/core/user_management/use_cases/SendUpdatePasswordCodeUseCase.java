package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SendUpdatePasswordCodeUseCase {
	private UserRepository userRepository;
	private EmailSenderGateway emailSenderGateway;
	
	public SendUpdatePasswordCodeUseCase(UserRepository userRepository, EmailSenderGateway emailSenderGateway) {
		this.userRepository = userRepository;
		this.emailSenderGateway = emailSenderGateway;
	}
	
	public void execute(String email) {
		Client client = this.userRepository.findClientByEmail(email);
		String code = client.newUpdatePasswordCode();
		this.emailSenderGateway.send(email,"Your code to update your password is " + code, "Upadate Password Code");
		this.userRepository.save(client);
	}
}
