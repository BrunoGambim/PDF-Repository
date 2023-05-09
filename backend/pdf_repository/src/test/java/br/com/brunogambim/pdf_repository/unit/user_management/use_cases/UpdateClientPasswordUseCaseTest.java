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
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientPasswordUseCase;

public class UpdateClientPasswordUseCaseTest {
	private UpdateClientPasswordUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PasswordEncripterGateway passwordEncriptGateway = Mockito.mock(PasswordEncripterGateway.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	String code;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new UpdateClientPasswordUseCase(userRepository, passwordEncriptGateway);
		when(passwordEncriptGateway.encript("789012")).thenReturn("210987");
		Client client = new Client(1L, "user", "654321", "user@mail.com", 15);
		code = client.newUpdatePasswordCode();
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
		when(userRepository.isAdmin(3L)).thenReturn(false);		
		when(userRepository.findClientByEmail("user@mail.com")).thenReturn(client);
	}
	
	
	@Test
	void methodCalledWithClient() {
		useCase.execute("user@mail.com","789012", code);
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("210987");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
		
			return true;
	    }));
	}
	
	@Test
	void invalidaUpdatePasswordCode() {	
		assertThatThrownBy(() -> {
			useCase.execute("user@mail.com","789012", "123A42");
		}).isInstanceOf(InvalidUpdatePasswordCodeException.class);
	}
}
