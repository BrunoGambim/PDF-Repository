package br.com.brunogambim.pdf_repository.unit.user_management.entities;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.AuthorizationPolicy;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class AuthorizationPolicyTest {
	private AuthorizationPolicy policy;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		policy = new AuthorizationPolicy(userRepository, pdfRepository);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(false);
		when(userRepository.isAdmin(3L)).thenReturn(true);		
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client2);
		pdf.addToCanBeAccessedByList(client2);
		pdf2.addToCanBeAccessedByList(client);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(2L)).thenReturn(client2);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(pdfRepository.find(2L)).thenReturn(pdf2);
	}
	
	@Test
	void policyExecutedWithAdmin() {
		policy.checkIsAdmin(3L);
		
		policy.checkIsAdminOrOwner(3L,1L);
		
		assertThatThrownBy(() -> {
			policy.checkClientHasAccess(3L,1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
	
	@Test
	void policyExecutedWithOwner() {
		policy.checkIsAdminOrOwner(1L,1L);
		
		assertThatThrownBy(() -> {
			policy.checkIsAdminOrOwner(1L,2L);
		}).isInstanceOf(UnauthorizedUserException.class);
		
		assertThatThrownBy(() -> {
			policy.checkIsAdminOrOwner(2L,1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
	
	@Test
	void policyExecutedWithUserThatHasAccess() {
		policy.checkClientHasAccess(1L,2L);
		
		assertThatThrownBy(() -> {
			policy.checkClientHasAccess(1L,1L);
		}).isInstanceOf(UnauthorizedUserException.class);
		
		assertThatThrownBy(() -> {
			policy.checkClientHasAccess(2L,2L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
