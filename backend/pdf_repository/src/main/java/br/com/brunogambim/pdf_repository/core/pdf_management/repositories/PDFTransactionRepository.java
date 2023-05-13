package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;

public interface PDFTransactionRepository {
	public Long save(PurchasePDFAccessTransaction transaction);
	public List<PurchasePDFAccessTransaction> findTransactionsByPdfId(Long pdfId);
}
