package br.com.brunogambim.pdf_repository.core.use_cases;

import br.com.brunogambim.pdf_repository.core.entities.Client;
import br.com.brunogambim.pdf_repository.core.entities.PDF;
import br.com.brunogambim.pdf_repository.core.entities.PDFPricePolicy;
import br.com.brunogambim.pdf_repository.core.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.repositories.PDFRepository;

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
		Client client = this.userRepository.getClient(userId);
		PDF pdf = this.pdfRepository.get(pdfId);
		
		int pdfPrice = this.pdfPricePolicy.execute(pdf);
		client.subtractBalance(pdfPrice);
		
		client.addPDFToHasAccessPDFList(pdf);
		this.userRepository.save(client);
	}
}
