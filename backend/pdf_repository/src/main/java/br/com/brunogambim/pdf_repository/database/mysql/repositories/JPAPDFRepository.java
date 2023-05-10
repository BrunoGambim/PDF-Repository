package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;

@Repository
public interface JPAPDFRepository extends JpaRepository<PDFModel, Long> {
	public List<PDFModel> findByOwnerIdAndStatus(Long id, int status);
	
	public List<PDFModel> findByStatus(int status);
	public List<PDFModel> findByNameContainsAndStatus(String name, int status);
	
	public List<PDFModel> findByOwnerUsernameContainsAndStatus(String name, int status);
	
	@Query(value = "select pdfs.* from pdfs inner join pdfs_can_be_accessed_by on pdfs_id = id where can_be_accessed_by_id = :id and status = :status", nativeQuery = true)
	public List<PDFModel> findPDFFilesThatCanBeAccessedBy(@Param("id") Long id, @Param("status") int status);
}
