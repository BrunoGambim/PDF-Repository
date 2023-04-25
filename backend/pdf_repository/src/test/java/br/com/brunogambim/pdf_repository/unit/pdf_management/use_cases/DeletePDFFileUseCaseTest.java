package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.DeletePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class DeletePDFFileUseCaseTest {
	private DeletePDFFileUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5));
		useCase = new DeletePDFFileUseCase(pdfRepository, userRepository);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
		when(userRepository.isAdmin(3L)).thenReturn(false);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		client.addPDFToOwnedPDFList(new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		Client client2 = new Client(3L, "user2", "123456","user2@mail.com", 30);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(3L)).thenReturn(client2);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithTheOwner() {
		useCase.execute(1L, 1L);
	
		verify(pdfRepository).delete(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo(1L);
			return true;
	    }));
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithAnAdmin() {
		useCase.execute(2L, 1L);
	
		verify(pdfRepository).delete(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo(1L);
			return true;
	    }));
	}
	
	@Test
	void unauthorizedToDeletePDf() {	
		assertThatThrownBy(() -> {
			useCase.execute(3L, 1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
