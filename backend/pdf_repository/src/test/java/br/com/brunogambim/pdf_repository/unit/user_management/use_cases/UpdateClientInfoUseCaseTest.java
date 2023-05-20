package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.EmailIsAlreadyBeingUsedException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientInfoUseCase;

public class UpdateClientInfoUseCaseTest {
	private UpdateClientInfoUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new UpdateClientInfoUseCase(userRepository, pdfRepository);
		Client client = new Client(1L, "oldUser", "654321", "oldUserser@mail.com", 15);
		Client client2 = new Client(2L, "user2", "654321", "user2@mail.com", 15);
		
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isClient(1L)).thenReturn(true);	
		when(userRepository.isAdmin(2L)).thenReturn(true);
		when(userRepository.isAdmin(3L)).thenReturn(false);		
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.emailIsBeingUsed("user2@mail.com")).thenReturn(true);
		when(userRepository.findClientByEmail("user2@mail.com")).thenReturn(client2);
	}
	
	
	@Test
	void useCaseExecutedWithClient() {
		useCase.execute(1L, 1L,"user","user@mail.com");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("654321");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
		
			return true;
	    }));
	}
	
	@Test
	void useCaseExecutedWithAdmin() {
		useCase.execute(2L, 1L,"user","user@mail.com");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("654321");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
		
			return true;
	    }));
	}
	
	@Test
	void useCaseExecutedWithUnauthorizedUser() {	
		assertThatThrownBy(() -> {
			useCase.execute(3L, 1L,"user","user@mail.com");
		}).isInstanceOf(UnauthorizedUserException.class);
	}
	
	@Test
	void useCaseExecutedWithInvalidEmail() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,1L,"user","user2@mail.com");
		}).isInstanceOf(EmailIsAlreadyBeingUsedException.class);
	}
}
