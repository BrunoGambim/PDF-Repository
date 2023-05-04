package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.ClientTransactionReport;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class GenerateClientTransactionReportUseCase {
	private AuthorizationPolicy authorizationPolicy;
	private PDFTransactionRepository transactionRepository;

	public GenerateClientTransactionReportUseCase(UserRepository userRepository,
			PDFTransactionRepository transactionRepository, PDFRepository pdfRepository) {
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
		this.transactionRepository = transactionRepository;
	}
	
	public ClientTransactionReport execute(Long userId, Long clientId) {
		if(userId != clientId) {
			authorizationPolicy.CheckIsAdminAuthorization(userId);
		}
		
		return new ClientTransactionReport(this.transactionRepository.findByBuyerId(clientId),
				this.transactionRepository.findByOwnerId(clientId));
	}
}
