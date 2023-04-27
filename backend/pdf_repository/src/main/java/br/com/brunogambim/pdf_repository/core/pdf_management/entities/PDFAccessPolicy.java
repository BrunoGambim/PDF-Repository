package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.NegativeBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class PDFAccessPolicy {
	public static void checkClient(Client client) {
		if(client.getBalance() < 0) {
			throw new NegativeBalanceException();
		}
	}
}
