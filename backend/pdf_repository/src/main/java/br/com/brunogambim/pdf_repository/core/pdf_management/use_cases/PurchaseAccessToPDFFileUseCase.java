package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class PurchaseAccessToPDFFileUseCase {
	private UserRepository userRepository;
	private PDFRepository pdfRepository;
	private PDFPricingPolicy pdfPricingPolicy;
	private PDFTransactionRepository transactionRepository;
	private AuthorizationPolicy authorizationPolicy;

	public PurchaseAccessToPDFFileUseCase(UserRepository userRepository, PDFRepository pdfRepository,
			PDFManagementParametersRepository managementParametersRepository,
			PDFTransactionRepository transactionRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.pdfPricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		this.transactionRepository = transactionRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public void execute(Long userId, Long pdfId) {
		this.authorizationPolicy.checkIsClient(userId);
		Client buyer = this.userRepository.findClient(userId);
		PDF pdf = this.pdfRepository.find(pdfId);
		Client owner = pdf.getOwner();
		
		PurchasePDFAccessTransaction transaction = new PurchasePDFAccessTransaction(buyer,pdf,owner, pdfPricingPolicy);
		buyer.subtractBalance(transaction.getPrice());
		owner.addBalance(transaction.getPrice());
		pdf.addToCanBeAccessedByList(buyer);
		
		this.userRepository.save(buyer);
		this.userRepository.save(owner);
		this.pdfRepository.save(pdf);
		this.transactionRepository.save(transaction);
	}
}
