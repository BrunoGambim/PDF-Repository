package br.com.brunogambim.pdf_repository.core.use_cases;

import br.com.brunogambim.pdf_repository.core.entities.Client;
import br.com.brunogambim.pdf_repository.core.entities.PDF;
import br.com.brunogambim.pdf_repository.core.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.repositories.PDFManagementParametersRepository;

public class SaveNewPDFFileUseCase {
	private UserRepository clientRepository;
	private PDFSizePolicy pdfSizePolicy;

	public SaveNewPDFFileUseCase(UserRepository clientRepository, PDFManagementParametersRepository managementParametersRepository) {
		this.clientRepository = clientRepository;
		this.pdfSizePolicy = new PDFSizePolicy(managementParametersRepository);
	}
	
	public void execute(Long userId, String name, String format, int size, byte[] data) {
		Client client = this.clientRepository.getClient(userId);
		client.addPDFToOwnedPDFList(new PDF(name, format, size, data, pdfSizePolicy));
		this.clientRepository.save(client);
	}
}
