package br.com.brunogambim.pdf_repository.core.pdf_management.exceptions;

public class InvalidFileDataSizeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidFileDataSizeException() {
		super(message());
	}
	
	public InvalidFileDataSizeException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "File data size does not match with file size.";
	}
}
