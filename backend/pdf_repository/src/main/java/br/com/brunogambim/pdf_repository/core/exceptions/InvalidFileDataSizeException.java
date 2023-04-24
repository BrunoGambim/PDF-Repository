package br.com.brunogambim.pdf_repository.core.exceptions;

public class InvalidFileDataSizeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidFileDataSizeException() {
		super(message());
	}
	
	public InvalidFileDataSizeException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Invalid file data size";
	}
}
