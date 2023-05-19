package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdatePDFFileUseCase {
	private PDFRepository pdfRepository;
	private PDFSizePolicy pdfSizePolicy;
	private AuthorizationPolicy authorizationPolicy;

	public UpdatePDFFileUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository,
			UserRepository userRepository) {
		this.pdfRepository = pdfRepository;
		this.pdfSizePolicy = new PDFSizePolicy(managementParametersRepository);
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public void execute(Long userId, Long pdfId, String name, String description, String format, int size, byte[] data) {
		this.authorizationPolicy.checkIsAdminOrOwner(userId, pdfId);
		PDF pdf = this.pdfRepository.find(pdfId);
		this.pdfRepository.save(new PDF(pdfId, name, description, format, size, data, pdfSizePolicy,
				pdf.getOwner(), pdf.getEvaluations(), pdf.getCanBeAccessedBy()));
	}
}
