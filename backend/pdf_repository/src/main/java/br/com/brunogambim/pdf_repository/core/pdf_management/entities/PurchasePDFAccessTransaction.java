package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
public class PurchasePDFAccessTransaction {
	Long id;
	ClientInfo buyer;
	PDFInfo pdf;
	ClientInfo pdfOwner;
	int price;
	
	public PurchasePDFAccessTransaction(Client buyer, PDF pdf, Client pdfOwner, int price,
			PDFPricingPolicy pdfPricingPolicy) {
		this(buyer.getClientInfo(), pdf.getPDFInfo(pdfPricingPolicy), pdfOwner.getClientInfo(), price);
	}
	
	public PurchasePDFAccessTransaction(Long id, Client buyer, PDF pdf, Client pdfOwner, int price,
			PDFPricingPolicy pdfPricingPolicy) {
		this(id, buyer.getClientInfo(), pdf.getPDFInfo(pdfPricingPolicy), pdfOwner.getClientInfo(), price);
	}
	
	public PurchasePDFAccessTransaction(ClientInfo buyer, PDFInfo pdf, ClientInfo pdfOwner, int price) {
		this(null, buyer, pdf, pdfOwner, price);
	}
	
	public PurchasePDFAccessTransaction(Long id, ClientInfo buyer, PDFInfo pdf, ClientInfo pdfOwner, int price) {
		this.buyer = buyer;
		this.pdf = pdf;
		this.pdfOwner = pdfOwner;
		this.price = price;
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
	
	
}
