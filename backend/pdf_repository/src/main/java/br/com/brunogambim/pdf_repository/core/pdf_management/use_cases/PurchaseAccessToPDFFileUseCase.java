package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class PurchaseAccessToPDFFileUseCase {
	private UserRepository userRepository;
	private PDFRepository pdfRepository;
	private PDFPricingPolicy pdfPricingPolicy;
	private PDFTransactionRepository transactionRepository;

	public PurchaseAccessToPDFFileUseCase(UserRepository userRepository, PDFRepository pdfRepository,
			PDFManagementParametersRepository managementParametersRepository,
			PDFTransactionRepository transactionRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.pdfPricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		this.transactionRepository = transactionRepository;
	}
	
	public void execute(Long userId, Long pdfId) {
		Client buyer = this.userRepository.findClient(userId);
		Client owner = this.userRepository.findPDFOwner(pdfId);
		PDF pdf = this.pdfRepository.find(pdfId);
		
		PurchasePDFAccessTransaction transaction = new PurchasePDFAccessTransaction(buyer, pdf, owner, pdfPricingPolicy);
		buyer.subtractBalance(transaction.getPrice());
		owner.addBalance(transaction.getPrice());
		buyer.addPDFToHasAccessPDFList(pdf);
		
		this.userRepository.save(buyer);
		this.userRepository.save(owner);
		this.transactionRepository.save(transaction);
	}
}
