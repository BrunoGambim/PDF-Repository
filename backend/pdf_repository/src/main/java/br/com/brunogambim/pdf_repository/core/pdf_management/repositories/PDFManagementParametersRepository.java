package br.com.brunogambim.pdf_repository.core.pdf_management.repositories;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizeManagementParameters;

public interface PDFManagementParametersRepository {
	public PDFSizeManagementParameters findSizeParameters();
}
