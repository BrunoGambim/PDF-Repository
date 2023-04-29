package br.com.brunogambim.pdf_repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAPDFManagementParametersRepository;

@SpringBootApplication
public class PdfRepositoryApplication {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PDFRepository pdfRepository;
	@Autowired
	PDFManagementParametersRepository managementParametersRepository;
	@Autowired
	JPAPDFManagementParametersRepository managementParametersRepository2;

	public static void main(String[] args) {
		SpringApplication.run(PdfRepositoryApplication.class, args);
	}
}
