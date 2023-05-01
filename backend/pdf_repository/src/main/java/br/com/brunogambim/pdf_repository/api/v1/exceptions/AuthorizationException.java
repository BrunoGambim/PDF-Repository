package br.com.brunogambim.pdf_repository.api.v1.exceptions;

public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthorizationException() {
		super(message());
	}
	
	public AuthorizationException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Acess denied";
	}
}
