package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.time.LocalDateTime;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
public class PurchasePDFAccessTransaction {
	Long id;
	ClientInfo buyer;
	PDFInfo pdf;
	ClientInfo pdfOwner;
	int price;
	private LocalDateTime createdAt;
	
	public PurchasePDFAccessTransaction(Client buyer, PDF pdf, Client pdfOwner,
			PDFPricingPolicy pdfPricingPolicy) {
		this(null, buyer, pdf, pdfOwner, pdfPricingPolicy);
	}
	
	public PurchasePDFAccessTransaction(Long id, Client buyer, PDF pdf, Client pdfOwner,
			PDFPricingPolicy pdfPricingPolicy) {
		this(id, buyer.getClientInfo(), pdf.getPDFInfo(pdfPricingPolicy), pdfOwner.getClientInfo(), pdfPricingPolicy.execute(pdf));
	}
	
	public PurchasePDFAccessTransaction(ClientInfo buyer, PDFInfo pdf, ClientInfo pdfOwner, int price) {
		this(null, buyer, pdf, pdfOwner, price);
	}
	
	public PurchasePDFAccessTransaction(Long id, ClientInfo buyer, PDFInfo pdf, ClientInfo pdfOwner, int price) {
		this.buyer = buyer;
		this.pdf = pdf;
		this.pdfOwner = pdfOwner;
		this.price = price;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public ClientInfo getBuyer() {
		return buyer;
	}

	public PDFInfo getPdf() {
		return pdf;
	}

	public ClientInfo getPdfOwner() {
		return pdfOwner;
	}

	public int getPrice() {
		return price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
