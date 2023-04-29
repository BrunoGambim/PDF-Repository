package br.com.brunogambim.pdf_repository.database.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException() {
		super(message());
	}
	
	public ObjectNotFoundException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Object no found!";
	}
}
