package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFInfoByOwnerNameUseCase {
	private PDFRepository pdfRepository;
	private UserRepository userRepository;
	private PDFPricingPolicy pricingPolicy;

	public FindPDFInfoByOwnerNameUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository,
			UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		this.userRepository = userRepository;
	}
	
	public PageAdapter<PDFInfo> execute(Long userId, String name, Integer pageIndex, Integer pageSize) {
		PageAdapter<PDF> pdfList = this.pdfRepository.findPDFFilesByOwnerNameContains(name, pageIndex, pageSize);
		if(userId == null) {
			return pdfList.mapTo(pdf -> pdf.getPDFInfoWithoutData(pricingPolicy));
		}else if(this.userRepository.isAdmin(userId)) {
			return pdfList.mapTo(pdf -> pdf.getPDFInfoWithData(pricingPolicy));
		}else {
			return pdfList.mapTo(pdf -> pdf.getPDFInfo(userId, pricingPolicy));
		}
	}
}
