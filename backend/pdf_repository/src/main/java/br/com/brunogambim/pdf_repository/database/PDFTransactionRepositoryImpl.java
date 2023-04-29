package br.com.brunogambim.pdf_repository.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.database.mysql.models.ClientModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.PurchasePDFAccessTransactionModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPATransactionRepository;

@Service
public class PDFTransactionRepositoryImpl implements PDFTransactionRepository{
	
	private JPATransactionRepository jpaTransactionRepository;
	private PDFRepository pdfRepository;
	private UserRepository userRepository;
	private PDFSizePolicy pdfSizePolicy;
	private PDFPricingPolicy pdfPricingPolicy;
	
	@Autowired
	public PDFTransactionRepositoryImpl(JPATransactionRepository jpaTransactionRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository, PDFRepository pdfRepository,
			UserRepository userRepository) {
		this.jpaTransactionRepository = jpaTransactionRepository;
		this.pdfSizePolicy = new PDFSizePolicy(pdfManagementParametersRepository);
		this.pdfPricingPolicy = new PDFPricingPolicy(pdfManagementParametersRepository);
		this.userRepository = userRepository;
		this.pdfRepository = pdfRepository;
	}

	@Override
	public List<PurchasePDFAccessTransaction> findByBuyerId(Long id) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByBuyerId(id), pdfPricingPolicy, pdfSizePolicy);
	}

	@Override
	public List<PurchasePDFAccessTransaction> findByOwnerId(Long id) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByPdfOwnerId(id), pdfPricingPolicy, pdfSizePolicy);
	}

	@Override
	public void save(PurchasePDFAccessTransaction transaction) {
		Client owner = userRepository.findClient(transaction.getPdfOwner().getId());
		Client buyer = userRepository.findClient(transaction.getBuyer().getId());
		PDF pdf = pdfRepository.find(transaction.getPdf().getId());
		PurchasePDFAccessTransactionModel model = new PurchasePDFAccessTransactionModel(transaction,
				new ClientModel(owner),new ClientModel(buyer),new PDFModel(pdf));
		this.jpaTransactionRepository.save(model);
	}

	@Override
	public List<PurchasePDFAccessTransaction> findAll() {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findAll(), pdfPricingPolicy, pdfSizePolicy);
	}

	@Override
	public List<PurchasePDFAccessTransaction> findTransactionsByPdfId(Long pdfId) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByPdfId(pdfId), pdfPricingPolicy, pdfSizePolicy);
	}
}
