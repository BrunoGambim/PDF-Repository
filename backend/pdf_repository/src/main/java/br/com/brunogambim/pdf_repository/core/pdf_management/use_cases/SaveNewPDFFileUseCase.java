package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class SaveNewPDFFileUseCase {
	private PDFRepository pdfRepository;

	public SaveNewPDFFileUseCase(PDFRepository pdfRepository) {
		this.pdfRepository = pdfRepository;
	}
	
	public void execute(String name, String format, int size, byte[] data) {
		this.pdfRepository.save(new PDF(name, format, size, data));
	}
}
