package br.com.brunogambim.pdf_repository.core.user_management.entities;

public class ClientInfo {
	private Long id;
	private String username;
	private String email;
	private int balance;
	
	public ClientInfo(Long id, String username, String email, int balance) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public int getBalance() {
		return balance;
	}
}
