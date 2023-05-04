package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.time.LocalDateTime;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "purchase_pdf_access_transaction")
public class PurchasePDFAccessTransactionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long buyerId;
	private String buyerName;
	private Long pdfId;
	private String pdfName;
	private Long ownerId;
	private String ownerName;
	private int price;
	private LocalDateTime createdAt;

	public PurchasePDFAccessTransactionModel(Long id, Long buyerId, String buyerName, Long pdfId, String pdfName,
			Long ownerId, String ownerName, int price, LocalDateTime createdAt) {
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
	
	public PurchasePDFAccessTransactionModel(PurchasePDFAccessTransaction entity) {
		this(entity.getId(),entity.getBuyerId(), entity.getBuyerName(), entity.getPdfId(), entity.getPdfName(),
				entity.getOwnerId(), entity.getOwnerName(), entity.getPrice(), entity.getCreatedAt());
	}
	
	public PurchasePDFAccessTransaction toEntity() {
		return new PurchasePDFAccessTransaction(id, buyerId, buyerName, pdfId, pdfName, ownerId, ownerName, price, createdAt);
	}
	
	public static List<PurchasePDFAccessTransaction> modelListToEntityList(List<PurchasePDFAccessTransactionModel> modelList) {
		return modelList.stream().map(transaction -> transaction.toEntity()).toList();
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
	
	public PurchasePDFAccessTransactionModel() {
	}
}
