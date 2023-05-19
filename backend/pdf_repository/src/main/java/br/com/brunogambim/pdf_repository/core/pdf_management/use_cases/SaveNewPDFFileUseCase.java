package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewPDFFileUseCase {
	private UserRepository userRepository;
	private PDFRepository pdfRepository;
	private PDFSizePolicy pdfSizePolicy;
	private AuthorizationPolicy authorizationPolicy;

	public SaveNewPDFFileUseCase(UserRepository userRepository, PDFManagementParametersRepository managementParametersRepository,
			PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.pdfSizePolicy = new PDFSizePolicy(managementParametersRepository);
		this.pdfRepository = pdfRepository;
		this.authorizationPolicy = new AuthorizationPolicy(userRepository, pdfRepository);
	}
	
	public Long execute(Long userId, String name, String description, String format, int size, byte[] data) {
		this.authorizationPolicy.checkIsClient(userId);
		Client client = this.userRepository.findClient(userId);
		PDF pdf = new PDF(name, description, format, size, data, pdfSizePolicy, client);
		return this.pdfRepository.save(pdf);
	}
}
