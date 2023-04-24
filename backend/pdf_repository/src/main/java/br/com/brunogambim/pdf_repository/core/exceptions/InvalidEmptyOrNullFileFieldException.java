package br.com.brunogambim.pdf_repository.core.exceptions;

public class InvalidEmptyOrNullFileFieldException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidEmptyOrNullFileFieldException(String field) {
		super(message(field));
	}
	
	public InvalidEmptyOrNullFileFieldException(Throwable cause, String field) {
		super(message(field), cause);
	}
	
	private static String message(String field) {
		return field + "cannot be empty nor null.";
	}
}
