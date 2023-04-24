package br.com.brunogambim.pdf_repository.core.entities;

import br.com.brunogambim.pdf_repository.core.repositories.PDFManagementParametersRepository;

public class PDFPricePolicy {
	
	private PDFManagementParametersRepository repository;
	
	public PDFPricePolicy(PDFManagementParametersRepository repository) {
		this.repository = repository;
	}
	
	public int execute(PDF pdf) {
		PDFManagementParameters parameters = this.repository.findParameters();
		if(pdf.getSize() < parameters.getMinBigFileSize()) {
			return parameters.getSmallFilePrice();
		} else {
			return parameters.getBigFilePrice();
		}
	}
}
