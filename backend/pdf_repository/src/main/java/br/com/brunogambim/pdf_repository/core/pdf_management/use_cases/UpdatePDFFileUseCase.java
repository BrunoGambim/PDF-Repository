package br.com.brunogambim.pdf_repository.core.pdf_management.use_cases;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;

public class UpdatePDFFileUseCase {
	private PDFRepository pdfRepository;
	private PDFSizePolicy pdfSizePolicy;

	public UpdatePDFFileUseCase(PDFRepository pdfRepository, PDFManagementParametersRepository managementParametersRepository) {
		this.pdfRepository = pdfRepository;
		this.pdfSizePolicy = new PDFSizePolicy(managementParametersRepository);
	}
	
	public void execute(Long id, String name, String format, int size, byte[] data) {
		this.pdfRepository.save(new PDF(id, name, format, size, data, pdfSizePolicy));
	}
}
