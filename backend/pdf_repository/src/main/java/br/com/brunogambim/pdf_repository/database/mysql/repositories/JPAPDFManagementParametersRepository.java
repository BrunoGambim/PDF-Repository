package br.com.brunogambim.pdf_repository.database.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.pdf_repository.database.mysql.models.PDFManagementParametersModel;

@Repository
public interface JPAPDFManagementParametersRepository extends JpaRepository<PDFManagementParametersModel, Long>{

}
