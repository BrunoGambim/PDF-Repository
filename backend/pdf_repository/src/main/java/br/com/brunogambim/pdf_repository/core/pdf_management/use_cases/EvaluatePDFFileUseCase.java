package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class EvaluatePDFFileUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;
	private UserRepository userRepository;

	public EvaluatePDFFileUseCase(PDFRepository pdfRepository, UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
		this.userRepository = userRepository;
	}
	
	public void execute(Long userId, Long pdfId, double evaluationValue) {
		this.authorizationPolicy.CheckIsAdminOrHasAccessAuthorization(userId, pdfId);
		PDF pdf = this.pdfRepository.find(pdfId);
		Client client = this.userRepository.findClient(userId);
		pdf.addEvaluation(client, evaluationValue);
		this.pdfRepository.save(pdf);
	}
}
