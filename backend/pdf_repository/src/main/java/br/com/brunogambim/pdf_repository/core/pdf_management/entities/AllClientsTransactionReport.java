package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.util.ArrayList;
import java.util.List;

public class AllClientsTransactionReport {
	List<PurchasePDFAccessTransaction> transactions;
	
	public AllClientsTransactionReport(List<PurchasePDFAccessTransaction> transactions) {
		this.transactions = transactions;
	}

	public List<PurchasePDFAccessTransaction> getTransactions() {
		return new ArrayList<PurchasePDFAccessTransaction>(transactions);
	}
}
