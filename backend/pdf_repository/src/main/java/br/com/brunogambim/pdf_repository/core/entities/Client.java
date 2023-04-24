package br.com.brunogambim.pdf_repository.core.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.core.exceptions.UserAlreadyOwnsThisPDFException;

public class Client extends User{
	private List<PDF> ownedPDFList;
	private List<PDF> hasAccessPDFList;
	int balance;

	public Client(Long id, String username, String password, String email, List<PDF> ownedPDFList, List<PDF> hasAccessPDFList, int balance) {
		super(id, username, password, email);
		this.ownedPDFList = ownedPDFList;
		this.hasAccessPDFList = hasAccessPDFList;
		this.balance = balance;
	}
	
	public Client(Long id, String username, String password, String email, int balance) {
		super(id, username, password, email);
		this.ownedPDFList = new ArrayList<PDF>();
		this.hasAccessPDFList = new ArrayList<PDF>();
		this.balance = balance;
	}

	public List<PDF> getOwnedPDFList() {
		return ownedPDFList;
	}

	public List<PDF> getHasAccessPDFList() {
		return hasAccessPDFList;
	}
	
	public void addPDFToHasAccessPDFList(PDF pdf) {
		if(this.hasAccessPDFList.contains(pdf)) {
			throw new UserAlreadyHasAccessToPDFException();
		}
		this.hasAccessPDFList.add(pdf);
	}
	
	public void addPDFToOwnedPDFList(PDF pdf) {
		if(this.ownedPDFList.contains(pdf)) {
			throw new UserAlreadyOwnsThisPDFException();
		}
		this.ownedPDFList.add(pdf);
	}
	
	public void subtractBalance(int value) {
		if(value > this.balance) {
			throw new InsufficientBalanceException();
		}
		this.balance = this.balance - value;
	}
	
	public void addBalance(int value) {
		this.balance = this.balance + value;
	}

	public int getBalance() {
		return balance;
	}
}
