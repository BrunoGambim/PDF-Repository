package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SendUpdatePasswordCodeUseCase;

public class SendUpdatePasswordCodeUseCaseTest {
	private SendUpdatePasswordCodeUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private EmailSenderGateway emailSenderGateway = Mockito.mock(EmailSenderGateway.class);
	Client client;
	
	@BeforeEach
	void initUseCase() {
		useCase = new SendUpdatePasswordCodeUseCase(userRepository, emailSenderGateway);
		client = new Client(1L, "user", "654321", "user@mail.com", 15);
		when(userRepository.findClientEmail("user@mail.com")).thenReturn(client);
	}
	
	
	@Test
	void methodCalledWithClient() {
		useCase.execute("user@mail.com");
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getPassword()).isEqualTo("654321");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(15);
		
			return true;
	    }));
		
		verify(emailSenderGateway).send(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo("user@mail.com");
			
			return true;
	    }),argThat( x -> {
			assertThat(x).isNotNull();
			
			return true;
	    }),argThat( x -> {
			assertThat(x).isNotNull();
			
			return true;
	    }));
	}
}
