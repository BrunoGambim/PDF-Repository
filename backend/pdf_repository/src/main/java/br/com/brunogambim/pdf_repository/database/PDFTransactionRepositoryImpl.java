package br.com.brunogambim.pdf_repository.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.database.mysql.models.PurchasePDFAccessTransactionModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPATransactionRepository;

@Service
public class PDFTransactionRepositoryImpl implements PDFTransactionRepository{
	
	private JPATransactionRepository jpaTransactionRepository;
	
	@Autowired
	public PDFTransactionRepositoryImpl(JPATransactionRepository jpaTransactionRepository) {
		this.jpaTransactionRepository = jpaTransactionRepository;
	}

	@Override
	public List<PurchasePDFAccessTransaction> findByBuyerId(Long id) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByBuyerId(id));
	}

	@Override
	public List<PurchasePDFAccessTransaction> findByOwnerId(Long id) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByOwnerId(id));
	}

	@Override
	public Long save(PurchasePDFAccessTransaction transaction) {
		PurchasePDFAccessTransactionModel model = new PurchasePDFAccessTransactionModel(transaction);
		return this.jpaTransactionRepository.save(model).getId();
	}

	@Override
	public List<PurchasePDFAccessTransaction> findAll() {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findAll());
	}

	@Override
	public List<PurchasePDFAccessTransaction> findTransactionsByPdfId(Long pdfId) {
		return PurchasePDFAccessTransactionModel.modelListToEntityList(
				this.jpaTransactionRepository.findByPdfId(pdfId));
	}
}
