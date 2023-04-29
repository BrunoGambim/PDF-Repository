package br.com.brunogambim.pdf_repository.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;
import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAPDFRepository;

@Service
public class PDFRepositoryImpl implements PDFRepository{
	
	private JPAPDFRepository jpaPDFRepository;
	private PDFSizePolicy pdfSizePolicy;
	
	@Autowired
	public PDFRepositoryImpl(JPAPDFRepository jpaPDFRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository) {
		this.jpaPDFRepository = jpaPDFRepository;
		this.pdfSizePolicy = new PDFSizePolicy(pdfManagementParametersRepository);
	}

	@Override
	public PDF find(Long id) {
		return this.jpaPDFRepository.findById(id).orElseThrow(()->{
			throw new ObjectNotFoundException();
		}).toPDF(pdfSizePolicy);
	}

	@Override
	public void save(PDF pdf) {
		this.jpaPDFRepository.save(new PDFModel(pdf));
	}

	@Override
	public void delete(Long id) {
		this.jpaPDFRepository.deleteById(id);
	}

	@Override
	public List<PDF> findAllReportedPDFs() {
		return PDFModel.pdfModelListToPDFList(this.jpaPDFRepository.findByStatus(PDFStatus.REPORTED.getCode()), pdfSizePolicy);
	}

	@Override
	public List<PDF> findAllWaitingForValidationPDFs() {
		return PDFModel.pdfModelListToPDFList(this.jpaPDFRepository.findByStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION.getCode()),
				pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFFilesByNameContains(String name) {
		return PDFModel.pdfModelListToPDFList(this.jpaPDFRepository.findByNameContains(name), pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFFilesByOwnerNameContains(String name) {
		return PDFModel.pdfModelListToPDFList(this.jpaPDFRepository.findByOwnerNameContains(name), pdfSizePolicy);
	}

}
