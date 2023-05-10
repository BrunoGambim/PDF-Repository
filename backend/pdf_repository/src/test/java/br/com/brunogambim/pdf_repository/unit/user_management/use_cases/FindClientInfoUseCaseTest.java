package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.FindClientInfoUseCase;

public class FindClientInfoUseCaseTest {
	private FindClientInfoUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private Client client;
	
	@BeforeEach
	void initUseCase() {
		useCase = new FindClientInfoUseCase(userRepository);
		client = new Client(1L, "user", "654321", "user@mail.com", 15);
		when(userRepository.findClientByEmail("user@mail.com")).thenReturn(client);
	}
	
	
	
	@Test
	void methodCalledWithNormalUser() {
		ClientInfo clientInfo = client.getClientInfo();
		ClientInfo result = useCase.execute("user@mail.com");

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(clientInfo.getId());
		assertThat(result.getEmail()).isEqualTo(clientInfo.getEmail());
		assertThat(result.getUsername()).isEqualTo(clientInfo.getUsername());
	}
}
