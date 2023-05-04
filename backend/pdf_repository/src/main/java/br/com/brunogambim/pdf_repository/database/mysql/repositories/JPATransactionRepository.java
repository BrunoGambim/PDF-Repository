package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.PurchasePDFAccessTransactionModel;

@Repository
public interface JPATransactionRepository extends JpaRepository<PurchasePDFAccessTransactionModel, Long> {
	List<PurchasePDFAccessTransactionModel> findByOwnerId(Long id);
	List<PurchasePDFAccessTransactionModel> findByBuyerId(Long id);
	List<PurchasePDFAccessTransactionModel> findByPdfId(Long id);
}
