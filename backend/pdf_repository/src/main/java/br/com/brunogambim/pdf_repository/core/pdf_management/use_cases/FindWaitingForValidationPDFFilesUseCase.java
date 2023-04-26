package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindWaitingForValidationPDFFilesUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;

	public FindWaitingForValidationPDFFilesUseCase(PDFRepository pdfRepository, UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository);
	}
	
	public List<PDF> execute(Long userId) {
		this.authorizationPolicy.CheckIsAdminAuthorization(userId);
		return this.pdfRepository.findAllWaitingForValidationPDFs();
	}
}
