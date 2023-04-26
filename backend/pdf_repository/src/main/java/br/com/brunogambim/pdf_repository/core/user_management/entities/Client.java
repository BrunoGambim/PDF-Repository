package br.com.brunogambim.pdf_repository.core.user_management.entities;

import java.util.ArrayList;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyOwnsThisPDFException;

public class Client extends User{
	private List<PDF> ownedPDFList;
	private List<PDF> hasAccessPDFList;
	int balance;

	public Client(Long id, String username, String password, String email,
			List<PDF> ownedPDFList, List<PDF> hasAccessPDFList, int balance) {
		super(id, username, password, email);
		this.ownedPDFList = ownedPDFList;
		this.hasAccessPDFList = hasAccessPDFList;
		this.balance = balance;
	}
	
	public Client(Long id, String username, String password, String email, int balance) {
		this(id, username, password, email,  new ArrayList<PDF>(), new ArrayList<PDF>(), balance);
	}
	
	public Client(String username, String password, String email, int balance) {
		this(null, username, password, email,  new ArrayList<PDF>(), new ArrayList<PDF>(), balance);
	}

	public List<PDF> getOwnedPDFList() {
		return new ArrayList<PDF>(ownedPDFList);
	}

	public List<PDF> getHasAccessPDFList() {
		return new ArrayList<PDF>(hasAccessPDFList);
	}
	
	public void addPDFToHasAccessPDFList(PDF pdf) {
		if(this.hasAccessPDFList.contains(pdf)) {
			throw new UserAlreadyHasAccessToPDFException();
		}
		this.hasAccessPDFList.add(pdf);
	}
	
	public boolean hasAccessToPDF(Long id) {
		for(PDF pdf: this.hasAccessPDFList) {
			if(id == pdf.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean ownsPDF(Long id) {
		for(PDF pdf: this.ownedPDFList) {
			if(id == pdf.getId()) {
				return true;
			}
		}
		return false;
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
	
	public ClientInfo getClientInfo() {
		List<Long> ownedPDFIdList = ownedPDFList.stream().map(pdf -> pdf.getId()).toList();
		return new ClientInfo(getId(), getUsername(), getEmail(), ownedPDFIdList);
	}
}
