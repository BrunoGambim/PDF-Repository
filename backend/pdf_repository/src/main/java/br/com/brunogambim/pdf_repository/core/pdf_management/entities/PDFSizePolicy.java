package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;

public class PDFSizePolicy {
	
	private PDFManagementParametersRepository repository;
	
	public PDFSizePolicy(PDFManagementParametersRepository repository) {
		this.repository = repository;
	}
	
	public void execute(PDF pdf) {
		PDFManagementParameters sizeParameters = this.repository.findParameters();
		if(pdf.getSize() > sizeParameters.getMaxSize()) {
			throw new InvalidFileDataSizeException();
		} else if(pdf.getSize() < sizeParameters.getMinBigFileSize()) {
			pdf.setStatus(PDFStatus.VALIDATED);
		}
	}
}
