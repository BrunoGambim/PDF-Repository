package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class ReportPDFFileUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;

	public ReportPDFFileUseCase(PDFRepository pdfRepository, UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public void execute(Long userId, Long pdfId) {
		this.authorizationPolicy.CheckIsAdminOrHasAccessAuthorization(userId, pdfId);
		PDF pdf = this.pdfRepository.find(pdfId);
		pdf.setStatus(PDFStatus.REPORTED);
		this.pdfRepository.save(pdf);
	}
}
