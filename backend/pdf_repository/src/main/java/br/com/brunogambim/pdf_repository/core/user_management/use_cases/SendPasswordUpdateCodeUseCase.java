package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.user_management.entities.User;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SendPasswordUpdateCodeUseCase {
	private UserRepository userRepository;
	private EmailSenderGateway emailSenderGateway;
	
	public SendPasswordUpdateCodeUseCase(UserRepository userRepository, EmailSenderGateway emailSenderGateway) {
		this.userRepository = userRepository;
		this.emailSenderGateway = emailSenderGateway;
	}
	
	public void execute(String email) {
		User user = this.userRepository.findUserByEmail(email);
		String code = user.newPasswordUpdateCode();
		this.emailSenderGateway.send(email,"Your code to update your password is " + code, "Upadate Password Code");
		this.userRepository.save(user);
	}
}
