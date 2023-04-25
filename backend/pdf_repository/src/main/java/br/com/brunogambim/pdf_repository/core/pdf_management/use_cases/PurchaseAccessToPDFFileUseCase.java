package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class PurchaseAccessToPDFFileUseCase {
	private UserRepository userRepository;
	private PDFRepository pdfRepository;
	private PDFPricePolicy pdfPricePolicy;

	public PurchaseAccessToPDFFileUseCase(UserRepository userRepository, PDFRepository pdfRepository,
			PDFManagementParametersRepository managementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.pdfPricePolicy = new PDFPricePolicy(managementParametersRepository);
	}
	
	public void execute(Long userId, Long pdfId) {
		Client client = this.userRepository.findClient(userId);
		PDF pdf = this.pdfRepository.find(pdfId);
		
		int pdfPrice = this.pdfPricePolicy.execute(pdf);
		client.subtractBalance(pdfPrice);
		
		client.addPDFToHasAccessPDFList(pdf);
		this.userRepository.save(client);
	}
}
