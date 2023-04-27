package br.com.brunogambim.pdf_repository.core.user_management.gateways;

public interface EmailSenderGateway {
	public void send(String email, String message);
}
