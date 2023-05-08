package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.User;
import br.com.brunogambim.pdf_repository.core.user_management.entities.UserRoles;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindUserRoleByEmailUseCase {
	private UserRepository userRepository;
	private AuthorizationPolicy authorizationPolicy;
	
	public FindUserRoleByEmailUseCase(UserRepository userRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public UserRoles execute(Long userId, String email) {
		System.out.println("aqui");
		User user = this.userRepository.findUserEmail(email);
		if(userId != user.getId()) {
			authorizationPolicy.CheckIsAdminAuthorization(userId);
		}
		return UserRoles.fromClass(user);
	}
}
