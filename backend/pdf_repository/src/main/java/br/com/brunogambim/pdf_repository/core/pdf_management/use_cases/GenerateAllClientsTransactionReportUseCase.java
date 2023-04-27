package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.AllClientsTransactionReport;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class GenerateAllClientsTransactionReportUseCase {
	private AuthorizationPolicy authorizationPolicy;
	private PDFTransactionRepository transactionRepository;

	public GenerateAllClientsTransactionReportUseCase(UserRepository userRepository,
			PDFTransactionRepository transactionRepository) {
		this.authorizationPolicy = new AuthorizationPolicy(userRepository);
		this.transactionRepository = transactionRepository;
	}
	
	public AllClientsTransactionReport execute(Long userId) {
		authorizationPolicy.CheckIsAdminAuthorization(userId);
		return new AllClientsTransactionReport(this.transactionRepository.findAll());
	}
}
