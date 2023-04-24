package br.com.brunogambim.pdf_repository.core.repositories;

import br.com.brunogambim.pdf_repository.core.entities.PDF;

public interface PDFRepository {
	public PDF get(Long id);
	public void save(PDF pdf);
	public void delete(Long id);
}
