package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class UnauthorizedUserException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedUserException() {
		super(message());
	}
	
	public UnauthorizedUserException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "User does not have authorization to complete this action.";
	}
}
