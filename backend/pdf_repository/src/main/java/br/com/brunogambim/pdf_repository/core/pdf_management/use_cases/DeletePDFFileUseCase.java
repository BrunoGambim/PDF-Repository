package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class DeletePDFFileUseCase {
	private PDFRepository pdfRepository;
	private UserRepository userRepository;
	private PDFTransactionRepository transactionRepository;
	private AuthorizationPolicy authorizationPolicy;

	public DeletePDFFileUseCase(PDFRepository pdfRepository, UserRepository userRepository,
			PDFTransactionRepository transactionRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
		this.transactionRepository = transactionRepository;
	}
	
	public void execute(Long clientId, Long pdfId) {
		this.authorizationPolicy.CheckIsAdminOrOwnerAuthorization(clientId, pdfId);
		List<PurchasePDFAccessTransaction> transactions = this.transactionRepository.findAll();
		Client owner = pdfRepository.find(pdfId).getOwner();
		for(PurchasePDFAccessTransaction transaction: transactions) {
			Client client = userRepository.findClient(transaction.getBuyerId());
			client.addBalance(transaction.getPrice());
			userRepository.save(client);
			owner.refound(transaction.getPrice());
		}

		this.userRepository.save(owner);
		this.pdfRepository.delete(pdfId);
	}
	
	
}
