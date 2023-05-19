package br.com.brunogambim.pdf_repository.core.user_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class AuthorizationPolicy {
	
	private UserRepository userRepository;
	private PDFRepository pdfRepository;
	
	public AuthorizationPolicy(UserRepository userRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.pdfRepository = pdfRepository;
	}
	
	public void checkClientHasAccess(Long userId, Long pdfId) {
		PDF pdf = pdfRepository.find(pdfId);
		if(!pdf.canBeAccessedBy(userId)) {
			throw new UnauthorizedUserException();
		}
	}
	
	public void checkIsAdminOrOwner(Long userId, Long pdfId) {
		PDF pdf = pdfRepository.find(pdfId);
		if(!this.userRepository.isAdmin(userId)) {
			if(pdf.getOwner().getId() != userId) {
				throw new UnauthorizedUserException();
			}
		}
	}
	
	public void checkIsAdmin(Long id) {
		if(!this.userRepository.isAdmin(id)) {
			throw new UnauthorizedUserException();
		}
	}
	
	public void checkIsClient(Long id) {
		if(!this.userRepository.isClient(id)) {
			throw new UnauthorizedUserException();
		}
	}
}
