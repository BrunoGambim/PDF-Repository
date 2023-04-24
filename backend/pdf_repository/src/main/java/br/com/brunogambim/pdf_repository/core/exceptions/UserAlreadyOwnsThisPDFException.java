package br.com.brunogambim.pdf_repository.core.exceptions;

public class UserAlreadyOwnsThisPDFException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyOwnsThisPDFException() {
		super(message());
	}
	
	public UserAlreadyOwnsThisPDFException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "User already owns this PDF file.";
	}
}
