package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

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
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientPasswordUseCase;

public class UpdateClientPasswordUseCaseTest {
	private UpdateClientPasswordUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PasswordEncripterGateway passwordEncriptGateway = Mockito.mock(PasswordEncripterGateway.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new UpdateClientPasswordUseCase(userRepository, passwordEncriptGateway);
		when(passwordEncriptGateway.encript("789012")).thenReturn("210987");
		Client client = new Client(1L, "user", "654321", "user@mail.com", 15);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		client.addPDFToHasAccessPDFList(pdf);
		client.addPDFToOwnedPDFList(pdf2);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
		when(userRepository.isAdmin(3L)).thenReturn(false);		
		when(userRepository.findClient(1L)).thenReturn(client);
	}
	
	
	@Test
	void methodCalledWithClient() {
		useCase.execute(1L, 1L,"789012");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("210987");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(1);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(1);
		
			return true;
	    }));
	}
	
	@Test
	void methodCalledWithAdmin() {
		useCase.execute(2L, 1L,"789012");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("210987");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(1);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(1);
		
			return true;
	    }));
	}
	
	@Test
	void unauthorizedToUpdateClientPassword() {	
		assertThatThrownBy(() -> {
			useCase.execute(3L, 1L,"789012");
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
