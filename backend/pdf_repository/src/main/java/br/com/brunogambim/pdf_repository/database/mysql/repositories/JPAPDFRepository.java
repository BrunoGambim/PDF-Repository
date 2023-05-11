package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;

@Repository
public interface JPAPDFRepository extends JpaRepository<PDFModel, Long> {
	public Page<PDFModel> findByOwnerIdAndStatus(Long id, int status, Pageable pageable);
	
	public Page<PDFModel> findByStatus(int status, Pageable pageable);
	public Page<PDFModel> findByNameContainsAndStatus(String name, int status, Pageable pageable);
	
	public Page<PDFModel> findByOwnerUsernameContainsAndStatus(String name, int status, Pageable pageable);
	
	public Page<PDFModel> findByStatusAndCanBeAccessedBy_Id(int status, Long id, Pageable pageable);
}
