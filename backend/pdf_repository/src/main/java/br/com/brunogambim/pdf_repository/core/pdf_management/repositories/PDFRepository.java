package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import java.util.List;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;

public interface PDFRepository {
	public PDF find(Long id);
	public Long save(PDF pdf);
	public void delete(Long id);
	public List<PDF> findAllReportedPDFs();
	public List<PDF> findAllWaitingForValidationPDFs();
	public List<PDF> findPDFFilesByNameContains(String name);
	public List<PDF> findPDFFilesByOwnerNameContains(String name);
	public List<PDF> findPDFFilesThatCanBeAccessedBy(Long id);
	public List<PDF> findPDFilesOwnedBy(Long id);
}
