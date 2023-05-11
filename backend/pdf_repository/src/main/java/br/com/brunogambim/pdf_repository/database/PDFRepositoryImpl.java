package br.com.brunogambim.pdf_repository.database;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
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
	public PageAdapter<PDF> findAllReportedPDFs(Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByStatus(PDFStatus.REPORTED.getCode(), pageRequest),
				pdfSizePolicy);
	}

	@Override
	public PageAdapter<PDF> findAllWaitingForValidationPDFs(Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION.getCode(), pageRequest),
				pdfSizePolicy);
	}

	@Override
	public PageAdapter<PDF> findPDFFilesByNameContains(String name, Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByNameContainsAndStatus(name, PDFStatus.VALIDATED.getCode(), pageRequest),
				pdfSizePolicy);
	}

	@Override
	public PageAdapter<PDF> findPDFFilesByOwnerNameContains(String name, Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByOwnerUsernameContainsAndStatus(name, PDFStatus.VALIDATED.getCode(), pageRequest),
				pdfSizePolicy);
	}

	@Override
	public PageAdapter<PDF> findPDFFilesThatCanBeAccessedBy(Long id, Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByStatusAndCanBeAccessedBy_Id(PDFStatus.VALIDATED.getCode(), id, pageRequest),
				pdfSizePolicy);
	}

	@Override
	public PageAdapter<PDF> findPDFilesOwnedBy(Long id, Integer pageIndex, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
		return PDFModel.pdfModelPageToEntityPage(
				this.jpaPDFRepository.findByOwnerIdAndStatus(id, PDFStatus.VALIDATED.getCode(), pageRequest),
				pdfSizePolicy);
	}

}
