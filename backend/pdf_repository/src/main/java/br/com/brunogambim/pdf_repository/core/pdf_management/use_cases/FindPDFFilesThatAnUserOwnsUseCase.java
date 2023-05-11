package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class FindPDFFilesThatAnUserOwnsUseCase {
	private PDFRepository pdfRepository;
	private PDFPricingPolicy pdfPricingPolicy;
	
	public FindPDFFilesThatAnUserOwnsUseCase(PDFRepository pdfRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.pdfPricingPolicy = new PDFPricingPolicy(pdfManagementParametersRepository);
	}
	
	public PageAdapter<PDFInfo> execute(Long id, Integer pageIndex, Integer pageSize) {
		return pdfRepository.findPDFilesOwnedBy(id, pageIndex, pageSize)
				.mapTo(pdf -> pdf.getPDFInfoWithData(pdfPricingPolicy));
	}
}
