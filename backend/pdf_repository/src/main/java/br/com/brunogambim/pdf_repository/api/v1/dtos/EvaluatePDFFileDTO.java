package br.com.brunogambim.pdf_repository.api.v1.dtos;

public class EvaluatePDFFileDTO {
	private double evaluationValue;
	
	public EvaluatePDFFileDTO() {
	}

	public EvaluatePDFFileDTO(double evaluationValue) {
		this.evaluationValue = evaluationValue;
	}

	public double getEvaluationValue() {
		return evaluationValue;
	}

	public void setEvaluationValue(double evaluationValue) {
		this.evaluationValue = evaluationValue;
	}
}
