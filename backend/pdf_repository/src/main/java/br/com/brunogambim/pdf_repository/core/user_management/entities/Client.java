package br.com.brunogambim.pdf_repository.core.user_management.entities;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;

public class Client extends User{
	int balance;

	public Client(Long id, String username, String password, String email, int balance) {
		super(id, username, password, email);
		this.balance = balance;
	}
	
	public Client(String username, String password, String email, int balance) {
		this(null, username, password, email, balance);
	}
	
	public void subtractBalance(int value) {
		if(value > this.balance) {
			throw new InsufficientBalanceException();
		}
		this.balance = this.balance - value;
	}
	
	public void refound(int value) {
		this.balance = this.balance - value;
	}
	
	public void addBalance(int value) {
		this.balance = this.balance + value;
	}

	public int getBalance() {
		return balance;
	}

	public ClientInfo getClientInfo() {
		return new ClientInfo(getId(), getUsername(), getEmail(), balance);
	}
}
