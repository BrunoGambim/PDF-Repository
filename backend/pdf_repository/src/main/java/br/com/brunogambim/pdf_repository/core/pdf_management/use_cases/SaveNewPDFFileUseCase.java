package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewPDFFileUseCase {
	private UserRepository userRepository;
	private PDFSizePolicy pdfSizePolicy;

	public SaveNewPDFFileUseCase(UserRepository userRepository, PDFManagementParametersRepository managementParametersRepository) {
		this.userRepository = userRepository;
		this.pdfSizePolicy = new PDFSizePolicy(managementParametersRepository);
	}
	
	public void execute(Long userId, String name, String description, String format, int size, byte[] data) {
		Client client = this.userRepository.findClient(userId);
		client.addPDFToOwnedPDFList(new PDF(name, description, format, size, data, pdfSizePolicy));
		this.userRepository.save(client);
	}
}
