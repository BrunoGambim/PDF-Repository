package br.com.brunogambim.pdf_repository.core.pdf_management.exceptions;

public class InvalidFileFormatException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidFileFormatException(String fileFormat) {
		super(message(fileFormat));
	}
	
	public InvalidFileFormatException(Throwable cause, String fileFormat) {
		super(message(fileFormat), cause);
	}
	
	private static String message(String fileFormat) {
		return "Invalid file format: " + fileFormat + ". File format must be pdf.";
	}
}
