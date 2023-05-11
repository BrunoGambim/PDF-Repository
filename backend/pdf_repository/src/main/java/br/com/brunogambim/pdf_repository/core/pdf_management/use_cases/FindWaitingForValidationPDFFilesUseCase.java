package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindWaitingForValidationPDFFilesUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;
	private PDFPricingPolicy pdfPricingPolicy;

	public FindWaitingForValidationPDFFilesUseCase(PDFRepository pdfRepository, UserRepository userRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
		this.pdfPricingPolicy = new PDFPricingPolicy(pdfManagementParametersRepository);
	}
	
	public PageAdapter<PDFInfo> execute(Long userId, Integer pageIndex, Integer pageSize) {
		this.authorizationPolicy.CheckIsAdminAuthorization(userId);
		return this.pdfRepository.findAllWaitingForValidationPDFs(pageIndex, pageSize)
				.mapTo(pdf -> pdf.getPDFInfoWithData(pdfPricingPolicy));
	}
}
