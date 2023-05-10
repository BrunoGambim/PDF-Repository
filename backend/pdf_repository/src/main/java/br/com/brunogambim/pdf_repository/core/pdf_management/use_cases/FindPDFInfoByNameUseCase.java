package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFInfoByNameUseCase {
	private PDFRepository pdfRepository;
	private UserRepository userRepository;
	private PDFPricingPolicy pricingPolicy;

	public FindPDFInfoByNameUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository,
			UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.userRepository = userRepository;
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
	}
	
	public List<PDFInfo> execute(Long userId, String name) {
		List<PDF> pdfList = this.pdfRepository.findPDFFilesByNameContains(name);
		if(userId == null) {
			return pdfList.stream().map(pdf -> pdf.getPDFInfoWithoutData(pricingPolicy)).toList();
		}else if(this.userRepository.isAdmin(userId)) {
			return pdfList.stream().map(pdf -> pdf.getPDFInfoWithData(pricingPolicy)).toList();
		}else {
			return pdfList.stream().map(pdf -> pdf.getPDFInfo(userId, pricingPolicy)).toList();
		}
	}
}
