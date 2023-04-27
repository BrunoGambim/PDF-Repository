package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class InvalidUpdatePasswordCodeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidUpdatePasswordCodeException() {
		super(message());
	}
	
	public InvalidUpdatePasswordCodeException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Invalid update password code.";
	}
}
