package br.com.brunogambim.pdf_repository.core.user_management.entities;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class AuthorizationPolicy {
	
	private UserRepository userRepository;
	
	public AuthorizationPolicy(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void CheckIsAdminOrHasAccessAuthorization(Long userId, Long pdfId) {
		if(!this.userRepository.isAdmin(userId)) {
			Client client = this.userRepository.findClient(userId);
			if(!client.hasAccessToPDF(pdfId)) {
				throw new UnauthorizedUserException();
			}
		}
	}
	
	public void CheckIsAdminOrOwnerAuthorization(Long userId, Long pdfId) {
		if(!this.userRepository.isAdmin(userId)) {
			Client client = this.userRepository.findClient(userId);
			if(!client.ownsPDF(pdfId)) {
				throw new UnauthorizedUserException();
			}
		}
	}
	
	public void CheckIsAdminAuthorization(Long id) {
		if(!this.userRepository.isAdmin(id)) {
			throw new UnauthorizedUserException();
		}
	}
}
