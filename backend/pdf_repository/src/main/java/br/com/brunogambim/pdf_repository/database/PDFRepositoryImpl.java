package br.com.brunogambim.pdf_repository.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;
import br.com.brunogambim.pdf_repository.database.mysql.models.ClientModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAPDFRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PDFRepositoryImpl implements PDFRepository{
	
	private JPAPDFRepository jpaPDFRepository;
	private PDFSizePolicy pdfSizePolicy;
	
	@PersistenceContext
    private EntityManager em;
	
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
		}).toEntity(pdfSizePolicy);
	}

	@Override
	public Long save(PDF pdf) {
		return this.jpaPDFRepository.save(new PDFModel(pdf)).getId();
	}

	@Override
	public void delete(Long id) {
		PDFModel pdf = this.jpaPDFRepository.findById(id).orElseThrow(()->{
			throw new ObjectNotFoundException();
		});
		pdf.setCanBeAccessedBy(new ArrayList<ClientModel>());
		this.jpaPDFRepository.save(pdf);
		this.jpaPDFRepository.deleteById(id);
	}

	@Override
	public List<PDF> findAllReportedPDFs() {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findByStatus(PDFStatus.REPORTED.getCode()), pdfSizePolicy);
	}

	@Override
	public List<PDF> findAllWaitingForValidationPDFs() {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findByStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION.getCode()),
				pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFFilesByNameContains(String name) {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findByNameContainsAndStatus(name, PDFStatus.VALIDATED.getCode()), pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFFilesByOwnerNameContains(String name) {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findByOwnerUsernameContainsAndStatus(name, PDFStatus.VALIDATED.getCode()), pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFFilesThatCanBeAccessedBy(Long id) {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findPDFFilesThatCanBeAccessedBy(id, PDFStatus.VALIDATED.getCode()), pdfSizePolicy);
	}

	@Override
	public List<PDF> findPDFilesOwnedBy(Long id) {
		return PDFModel.pdfModelListToEntityList(this.jpaPDFRepository.findByOwnerIdAndStatus(id, PDFStatus.VALIDATED.getCode()), pdfSizePolicy);
	}

}
