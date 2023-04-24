package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;

public interface PDFRepository {
	public void save(PDF pdf);
	public void delete(Long id);
}
