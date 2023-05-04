package br.com.brunogambim.pdf_repository.core.user_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindClientInfoUseCase {
	private UserRepository userRepository;
	private PDFRepository pdfRepository;

	public FindClientInfoUseCase(UserRepository userRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.pdfRepository = pdfRepository;
	}
	
	public ClientInfo execute(Long id) {
		return this.userRepository.findClient(id).getClientInfo(pdfRepository.findPDFilesOwnedBy(id));
	}
}
