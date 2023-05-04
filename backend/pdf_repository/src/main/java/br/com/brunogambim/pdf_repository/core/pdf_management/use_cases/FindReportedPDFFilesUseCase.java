package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindReportedPDFFilesUseCase {
	private PDFRepository pdfRepository;
	private AuthorizationPolicy authorizationPolicy;
	private PDFPricingPolicy pdfPricingPolicy;

	public FindReportedPDFFilesUseCase(PDFRepository pdfRepository, UserRepository userRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
		this.pdfPricingPolicy = new PDFPricingPolicy(pdfManagementParametersRepository);
	}
	
	public List<PDFInfo> execute(Long userId) {
		this.authorizationPolicy.CheckIsAdminAuthorization(userId);
		return this.pdfRepository.findAllReportedPDFs()
				.stream().map(pdf -> pdf.getPDFInfoWithData(pdfPricingPolicy)).toList();
	}
}
