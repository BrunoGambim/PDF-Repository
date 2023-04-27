package br.com.brunogambim.pdf_repository.core.user_management.entities;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
	private Long id;
	private String username;
	private String email;
	private int balance;
	private List<Long> ownedPDFList;
	
	public ClientInfo(Long id, String username, String email, int balance, List<Long> ownedPDFList) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.ownedPDFList = ownedPDFList;
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

	public List<Long> getOwnedPDFList() {
		return new ArrayList<Long>(ownedPDFList);
	}
}
