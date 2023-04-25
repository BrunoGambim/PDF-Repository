package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEvaluationValueException;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class Evaluation {
	private double value;
	private Client evaluator;
	private static int HIGHEST_EVALUATION_VALUE = 10;
	private static int LOWEST_EVALUATION_VALUE = 0;
	
	public Evaluation(double value, Client evaluator) {
		this.setValue(value);
		this.evaluator = evaluator;
	}
	
	public void setValue(double value) {
		if(value > HIGHEST_EVALUATION_VALUE || value < LOWEST_EVALUATION_VALUE) {
			throw new InvalidEvaluationValueException();
		}
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public Client getEvaluator() {
		return evaluator;
	}
}
