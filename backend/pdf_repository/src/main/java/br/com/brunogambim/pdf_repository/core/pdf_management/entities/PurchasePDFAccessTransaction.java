package br.com.brunogambim.pdf_repository.core.pdf_management.entities;

import java.time.LocalDateTime;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class PurchasePDFAccessTransaction {
	private Long id;
	private Long buyerId;
	private String buyerName;
	private Long pdfId;
	private String pdfName;
	private Long ownerId;
	private String ownerName;
	private int price;
	private LocalDateTime createdAt;
	
	public PurchasePDFAccessTransaction(Long buyerId, String buyerName, Long pdfId, String pdfName,
			Long ownerId, String ownerName, int price) {
		this(null,  buyerId, buyerName, pdfId, pdfName, ownerId, ownerName, price, LocalDateTime.now());
	}
	
	public PurchasePDFAccessTransaction(Client buyer, PDF pdf, Client owner, PDFPricingPolicy pdfPricingPolicy) {
		this(buyer.getId(), buyer.getUsername(), pdf.getId(), pdf.getName(), owner.getId(), owner.getUsername(), pdfPricingPolicy.execute(pdf));
	}

	public PurchasePDFAccessTransaction(Long id, Long buyerId, String buyerName, Long pdfId, String pdfName,
			Long ownerId, String ownerName, int price, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.pdfId = pdfId;
		this.pdfName = pdfName;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.price = price;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public Long getPdfId() {
		return pdfId;
	}

	public String getPdfName() {
		return pdfName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public int getPrice() {
		return price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
