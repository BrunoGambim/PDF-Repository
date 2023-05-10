package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.Evaluation;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "evaluations")
public class EvaluationModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "evaluator_id")
	private ClientModel evaluator;
	
	@ManyToOne
	@JoinColumn(name = "pdf_id")
	private PDFModel pdf;
	
	private double value;
	
	public EvaluationModel() {
	}

	public EvaluationModel(Evaluation evaluation, PDFModel pdf) {
		this(evaluation.getId(), evaluation.getValue(), new ClientModel(evaluation.getEvaluator()), pdf);
	}

	public EvaluationModel(Long id, double value, ClientModel evaluator, PDFModel pdf) {
		System.out.println(evaluator.getId());
		this.evaluator = evaluator;
		this.value = value;
		this.pdf = pdf;
		this.id = id;
	}
	
	public static List<EvaluationModel> evaluationCollectionToEvaluationModelList(Collection<Evaluation> evaluations, PDFModel pdf) {
		return evaluations.stream().map(evaluation -> new EvaluationModel(evaluation, pdf)).toList();
	}
	
	public static Map<Long, Evaluation> evaluationModelListToEvaluationMap(List<EvaluationModel> evaluations, PDFSizePolicy sizePolicy) {
		Map<Long, Evaluation> evaluationsMap = new HashMap<Long, Evaluation>();
		evaluations.forEach(evaluation -> {
			evaluationsMap.put(evaluation.getEvaluator().getId(), evaluation.toEntity());
		});
		return evaluationsMap;
	}
	
	public Evaluation toEntity() {
		return new Evaluation(this.getId() , this.getValue(), this.getEvaluator().toEntity());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public PDFModel getPdf() {
		return pdf;
	}

	public void setPdf(PDFModel pdf) {
		this.pdf = pdf;
	}
}
