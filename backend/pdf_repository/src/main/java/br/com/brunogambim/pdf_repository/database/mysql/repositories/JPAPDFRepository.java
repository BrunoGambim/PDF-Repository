package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;

@Repository
public interface JPAPDFRepository extends JpaRepository<PDFModel, Long> {
	@Query(value = "select pdfs.owner_id from pdfs where pdfs.id = :id", nativeQuery = true)
	public Optional<Long> findOwnerId(@Param("id") Long id);
	
	public List<PDFModel> findByStatus(int status);
	public List<PDFModel> findByNameContains(String name);
	
	@Query(value = "select pdfs.* from pdfs inner join users on pdfs.owner_id = users.id where users.username like %:name%", nativeQuery = true)
	public List<PDFModel> findByOwnerNameContains(@Param("name")String name);
}
