package br.com.brunogambim.pdf_repository.core.pdf_management.exceptions;

public class InvalidEvaluationValueException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidEvaluationValueException() {
		super();
	}
	
	public InvalidEvaluationValueException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Invalid evaluation value.";
	}
}
