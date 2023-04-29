package br.com.brunogambim.pdf_repository.database.mysql.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EvaluationId {
	@Column(name = "evaluator_id")
	private Long evaluatorId;
	@Column(name = "pdf_id")
	private Long pdfId;
	
	public EvaluationId() {
	}
	
	public EvaluationId(Long evaluatorId, Long pdfId) {
		this.evaluatorId = evaluatorId;
		this.pdfId = pdfId;
	}

	public Long getEvaluatorId() {
		return evaluatorId;
	}

	public void setEvaluatorId(Long evaluatorId) {
		this.evaluatorId = evaluatorId;
	}

	public Long getPdfId() {
		return pdfId;
	}

	public void setPdfId(Long pdfId) {
		this.pdfId = pdfId;
	}
}
