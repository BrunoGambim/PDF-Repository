package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFInfoByIdUseCase {
	private PDFRepository pdfRepository;
	private UserRepository userRepository;
	private PDFPricingPolicy pricingPolicy;

	public FindPDFInfoByIdUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository,
			UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
	}
	
	public PDFInfo execute(Long userId, Long pdfId) {
		PDF pdf = this.pdfRepository.find(pdfId);
		if(userId == null) {
			return pdf.getPDFInfoWithoutData(pricingPolicy);
		}else if(this.userRepository.isAdmin(userId)) {
			return pdf.getPDFInfoWithData(pricingPolicy);
		}else {
			return pdf.getPDFInfo(userId, pricingPolicy);
		}
	}
}
