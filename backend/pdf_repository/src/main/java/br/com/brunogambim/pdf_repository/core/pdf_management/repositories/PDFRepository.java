package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;

public interface PDFRepository {
	public PDF find(Long id);
	public Long save(PDF pdf);
	public void delete(Long id);
	public PageAdapter<PDF> findAllReportedPDFs(Integer pageIndex, Integer pageSize);
	public PageAdapter<PDF> findAllWaitingForValidationPDFs(Integer pageIndex, Integer pageSize);
	public PageAdapter<PDF> findPDFFilesByNameContains(String name, Integer pageIndex, Integer pageSize);
	public PageAdapter<PDF> findPDFFilesByOwnerNameContains(String name, Integer pageIndex, Integer pageSize);
	public PageAdapter<PDF> findPDFFilesThatCanBeAccessedBy(Long id, Integer pageIndex, Integer pageSize);
	public PageAdapter<PDF> findPDFilesOwnedBy(Long id, Integer pageIndex, Integer pageSize);
}
