package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class InvalidEmptyOrNullUserFieldException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidEmptyOrNullUserFieldException(String field) {
		super(message(field));
	}
	
	public InvalidEmptyOrNullUserFieldException(Throwable cause, String field) {
		super(message(field), cause);
	}
	
	private static String message(String field) {
		return field + "cannot be empty nor null.";
	}
}
