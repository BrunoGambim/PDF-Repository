package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;

public interface PDFTransactionRepository {
	public List<PurchasePDFAccessTransaction> findByBuyerId(Long id);
	public List<PurchasePDFAccessTransaction> findByOwnerId(Long id);
	public void save(PurchasePDFAccessTransaction transaction);
	public List<PurchasePDFAccessTransaction> findAll();
	public List<PurchasePDFAccessTransaction> findTransactionsByPdfId(Long pdfId);
}
