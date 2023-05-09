package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class FindPDFInfoByNameUseCase {
	private PDFRepository pdfRepository;
	private PDFPricingPolicy pricingPolicy;

	public FindPDFInfoByNameUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
	}
	
	public List<PDFInfo> execute(Long userId, String name) {
		List<PDF> pdfList = this.pdfRepository.findPDFFilesByNameContains(name);
		if(userId == null) {
			return pdfList.stream().map(pdf -> pdf.getPDFInfoWithoutData(pricingPolicy)).toList();
		}else {
			return pdfList.stream().map(pdf -> pdf.getPDFInfo(userId, pricingPolicy)).toList();
		}
	}
}
