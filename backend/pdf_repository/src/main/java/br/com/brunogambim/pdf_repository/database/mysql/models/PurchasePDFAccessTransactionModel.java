package br.com.brunogambim.pdf_repository.database.mysql.models;

import java.time.LocalDateTime;
import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "purchase_pdf_access_transaction")
public class PurchasePDFAccessTransactionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	ClientModel buyer;
	
	@ManyToOne
	@JoinColumn(name = "pdf_id")
	PDFModel pdf;
	
	@ManyToOne
	@JoinColumn(name = "pdf_owner_id")
	ClientModel pdfOwner;
	
	int price;
	private LocalDateTime createdAt;
	
	public PurchasePDFAccessTransactionModel() {
	}
	
	public PurchasePDFAccessTransactionModel(PurchasePDFAccessTransaction pdfAccessTransaction,
			ClientModel owner, ClientModel buyer, PDFModel pdf) {
		this.buyer = buyer;
		this.pdfOwner = owner;
		this.pdf = pdf;
		this.price = pdfAccessTransaction.getPrice();
		this.createdAt = pdfAccessTransaction.getCreatedAt();
	}
	
	public PurchasePDFAccessTransactionModel(Long id, ClientModel buyer, PDFModel pdf, ClientModel pdfOwner, int price,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.pdf = pdf;
		this.pdfOwner = pdfOwner;
		this.price = price;
		this.createdAt = createdAt;
	}
	
	public PurchasePDFAccessTransaction toEntity(PDFPricingPolicy pdfPricingPolicy, PDFSizePolicy pdfSizePolicy) {
		return new PurchasePDFAccessTransaction(id,buyer.toClient(pdfSizePolicy).getClientInfo(),
				pdf.toPDF(pdfSizePolicy).getPDFInfo(pdfPricingPolicy),
				pdfOwner.toClient(pdfSizePolicy).getClientInfo(),price);
	}
	
	public static List<PurchasePDFAccessTransaction> modelListToEntityList(List<PurchasePDFAccessTransactionModel> modelList,
			PDFPricingPolicy pdfPricingPolicy, PDFSizePolicy pdfSizePolicy) {
		return modelList.stream().map(transaction -> transaction.toEntity(pdfPricingPolicy, pdfSizePolicy)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClientModel getBuyer() {
		return buyer;
	}

	public void setBuyer(ClientModel buyer) {
		this.buyer = buyer;
	}

	public PDFModel getPdf() {
		return pdf;
	}

	public void setPdf(PDFModel pdf) {
		this.pdf = pdf;
	}

	public ClientModel getPdfOwner() {
		return pdfOwner;
	}

	public void setPdfOwner(ClientModel pdfOwner) {
		this.pdfOwner = pdfOwner;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
