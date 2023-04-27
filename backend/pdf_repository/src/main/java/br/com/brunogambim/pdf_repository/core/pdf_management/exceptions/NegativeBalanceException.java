package br.com.brunogambim.pdf_repository.core.pdf_management.exceptions;

public class NegativeBalanceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public NegativeBalanceException() {
		super(message());
	}
	
	public NegativeBalanceException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Client with a negative balance cannot access PDFs.";
	}
}
