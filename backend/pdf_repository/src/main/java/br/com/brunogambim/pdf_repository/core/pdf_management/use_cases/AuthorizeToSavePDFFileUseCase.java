package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class AuthorizeToSavePDFFileUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;

	public AuthorizeToSavePDFFileUseCase(PDFRepository pdfRepository, UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public void execute(Long userId, Long pdfId) {
		this.authorizationPolicy.CheckIsAdminAuthorization(userId);
		PDF pdf = this.pdfRepository.find(pdfId);
		pdf.setStatus(PDFStatus.VALIDATED);
		this.pdfRepository.save(pdf);
	}
}
