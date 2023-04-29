package br.com.brunogambim.pdf_repository.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAPDFManagementParametersRepository;

@Service
public class PDFManagementParametersRepositoryImpl implements PDFManagementParametersRepository{
	private JPAPDFManagementParametersRepository jpapdfManagementParametersRepository;
	
	@Autowired
	public PDFManagementParametersRepositoryImpl(JPAPDFManagementParametersRepository jpapdfManagementParametersRepository) {
		this.jpapdfManagementParametersRepository = jpapdfManagementParametersRepository;
	}

	@Override
	public PDFManagementParameters findParameters() {
		return this.jpapdfManagementParametersRepository.findById(1L).orElseThrow(() -> {
			throw new ObjectNotFoundException();
		}).toPDFManagementParameters();
	}

}
