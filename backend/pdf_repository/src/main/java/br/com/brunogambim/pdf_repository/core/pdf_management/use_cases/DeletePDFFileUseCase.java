package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class DeletePDFFileUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;

	public DeletePDFFileUseCase(PDFRepository pdfRepository, UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository);
	}
	
	public void execute(Long userId, Long pdfId) {
		this.authorizationPolicy.CheckIsAdminOrOwnerAuthorization(userId, pdfId);
		this.pdfRepository.delete(pdfId);
	}
}
