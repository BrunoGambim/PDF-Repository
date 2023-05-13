package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class EmailIsAlreadyBeingUsedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EmailIsAlreadyBeingUsedException() {
		super(message());
	}
	
	public EmailIsAlreadyBeingUsedException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Email is already being used.";
	}
}
