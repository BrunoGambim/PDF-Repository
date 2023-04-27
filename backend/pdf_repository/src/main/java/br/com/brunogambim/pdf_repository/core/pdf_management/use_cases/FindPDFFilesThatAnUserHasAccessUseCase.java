package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFAccessPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFFilesThatAnUserHasAccessUseCase {
	private UserRepository userRepository;
	
	public FindPDFFilesThatAnUserHasAccessUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<PDF> execute(Long id) {
		Client client = userRepository.findClient(id);
		PDFAccessPolicy.checkClient(client);
		return client.getHasAccessPDFList();
	}
}
