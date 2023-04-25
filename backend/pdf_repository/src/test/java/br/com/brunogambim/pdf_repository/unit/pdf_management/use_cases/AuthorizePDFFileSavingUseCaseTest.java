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
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.AuthorizePDFFileSavingUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class AuthorizePDFFileSavingUseCaseTest {
	private AuthorizePDFFileSavingUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5));
		useCase = new AuthorizePDFFileSavingUseCase(pdfRepository, userRepository);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf1 = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		client.addPDFToOwnedPDFList(pdf1);
		client.addPDFToHasAccessPDFList(pdf1);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(pdfRepository.find(1L)).thenReturn(pdf1);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithAdmin() {
		useCase.execute(2L, 1L);
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(x.getSize()).isEqualTo(4);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			return true;
	    }));
	}
	
	@Test
	void unauthorizedToDeletePDf() {	
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
