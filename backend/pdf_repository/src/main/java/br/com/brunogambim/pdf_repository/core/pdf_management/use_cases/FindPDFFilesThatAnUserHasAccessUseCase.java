package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class FindPDFFilesThatAnUserHasAccessUseCase {
	private PDFRepository pdfRepository;
	private PDFPricingPolicy pdfPricingPolicy;
	
	public FindPDFFilesThatAnUserHasAccessUseCase(PDFRepository pdfRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.pdfPricingPolicy = new PDFPricingPolicy(pdfManagementParametersRepository);
	}
	
	public List<PDFInfo> execute(Long id) {
		return this.pdfRepository.findPDFFilesThatCanBeAccessedBy(id)
				.stream().map(pdf -> pdf.getPDFInfoWithData(pdfPricingPolicy)).toList();
	}
}
