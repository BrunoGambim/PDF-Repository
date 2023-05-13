package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.EmailIsAlreadyBeingUsedException;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SaveNewClientUseCase;

public class SaveNewClientUseCaseTest {
	private SaveNewClientUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PasswordEncripterGateway passwordEncriptGateway = Mockito.mock(PasswordEncripterGateway.class);
	
	@BeforeEach
	void initUseCase() {
		useCase = new SaveNewClientUseCase(userRepository, passwordEncriptGateway);
		when(passwordEncriptGateway.encript("123456")).thenReturn("654321");
		when(userRepository.emailIsBeingUsed("user2@mail.com")).thenReturn(true);
	}
	
	
	@Test
	void useCaseExecutedWithValidParameters() {
		useCase.execute("user","user@mail.com","123456");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("654321");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(30);
		
			return true;
	    }));
		
	}
	
	@Test
	void useCaseExecutedWithInvalidEmail() {
		assertThatThrownBy(() -> {
			useCase.execute("user","user2@mail.com","123456");
		}).isInstanceOf(EmailIsAlreadyBeingUsedException.class);
	}
}
