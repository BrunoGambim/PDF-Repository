package br.com.brunogambim.pdf_repository.core.user_management.exceptions;

public class InsufficientBalanceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InsufficientBalanceException() {
		super(message());
	}
	
	public InsufficientBalanceException(Throwable cause) {
		super(message(), cause);
	}
	
	private static String message() {
		return "Insufficient balance to complete the transaction.";
	}
}
