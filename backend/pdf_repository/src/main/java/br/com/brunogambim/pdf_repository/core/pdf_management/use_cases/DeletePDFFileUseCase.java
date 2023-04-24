package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class DeletePDFFileUseCase {
	private PDFRepository pdfRepository;

	public DeletePDFFileUseCase(PDFRepository pdfRepository) {
		this.pdfRepository = pdfRepository;
	}
	
	public void execute(Long id) {
		this.pdfRepository.delete(id);
	}
}
