package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class UserAlreadyHasAccessToPDFException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyHasAccessToPDFException() {
		super(message());
	}
	
	public UserAlreadyHasAccessToPDFException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "User already has access to this PDF file.";
	}
}
