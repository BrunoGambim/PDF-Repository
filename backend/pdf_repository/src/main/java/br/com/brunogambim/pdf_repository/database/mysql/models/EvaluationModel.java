package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.util.Collection;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.Evaluation;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name = "evaluations")
public class EvaluationModel {
	@EmbeddedId
	private EvaluationId id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("evaluatorId")
	@JoinColumn(name = "evaluator_id")
	private ClientModel evaluator;
	
	private double value;
	
	public EvaluationModel() {
	}

	public EvaluationModel(Evaluation evaluation, Long pdfId) {
		this(evaluation.getValue(), new ClientModel(evaluation.getEvaluator()), pdfId);
	}

	public EvaluationModel(double value, ClientModel evaluator, Long pdfId) {
		this.evaluator = evaluator;
		this.value = value;
		this.id = new EvaluationId(evaluator.getId(), pdfId);
	}
	
	public static List<EvaluationModel> evaluationCollectionToEvaluationModelList(Collection<Evaluation> evaluations, Long pdfId) {
		return evaluations.stream().map(evaluation -> new EvaluationModel(evaluation, pdfId)).toList();
	}
	
	public Evaluation toEvaluation(PDFSizePolicy sizePolicy) {
		return new Evaluation(value, this.getEvaluator().toClient(sizePolicy));
	}

	public EvaluationId getId() {
		return id;
	}

	public void setId(EvaluationId id) {
		this.id = id;
	}

	public ClientModel getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(ClientModel evaluator) {
		this.evaluator = evaluator;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
}
