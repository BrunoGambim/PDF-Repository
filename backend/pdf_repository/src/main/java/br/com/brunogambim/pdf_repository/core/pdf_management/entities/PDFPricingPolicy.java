package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;

public class PDFPricingPolicy {
	
	private PDFManagementParametersRepository repository;
	
	public PDFPricingPolicy(PDFManagementParametersRepository repository) {
		this.repository = repository;
	}
	
	public int execute(PDF pdf) {
		PDFManagementParameters parameters = this.repository.findParameters();
		return computePDFBaseValue(pdf, parameters) + computeEvaluationBonus(pdf, parameters);
	}
	
	private int computePDFBaseValue(PDF pdf, PDFManagementParameters parameters) {
		if(pdf.getSize() < parameters.getMinBigFileSize()) {
			return parameters.getSmallFilePrice();
		} else {
			return parameters.getBigFilePrice();
		}
	}
	
	private int computeEvaluationBonus(PDF pdf, PDFManagementParameters parameters) {
		if(pdf.getEvaluations().size() >= parameters.getMinEvaluationNumberToBonus() && pdf.getEvaluationMean() >= parameters.getMinHighEvaluationMean()) {
			return parameters.getHighEvaluationMeanBonus();
		} 
		return 0;
	}
}
