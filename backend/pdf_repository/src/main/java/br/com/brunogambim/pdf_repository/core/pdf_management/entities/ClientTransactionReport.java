package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.util.ArrayList;
import java.util.List;

public class ClientTransactionReport {
	List<PurchasePDFAccessTransaction> transactionAsBuyer;
	List<PurchasePDFAccessTransaction> transactionAsOwner;
	
	public ClientTransactionReport(List<PurchasePDFAccessTransaction> transactionAsBuyer,
			List<PurchasePDFAccessTransaction> transactionAsOwner) {
		super();
		this.transactionAsBuyer = transactionAsBuyer;
		this.transactionAsOwner = transactionAsOwner;
	}

	public List<PurchasePDFAccessTransaction> getTransactionAsBuyer() {
		return new ArrayList<PurchasePDFAccessTransaction>(transactionAsBuyer);
	}
	
	public List<PurchasePDFAccessTransaction> getTransactionAsOwner() {
		return new ArrayList<PurchasePDFAccessTransaction>(transactionAsOwner);
	}
}
