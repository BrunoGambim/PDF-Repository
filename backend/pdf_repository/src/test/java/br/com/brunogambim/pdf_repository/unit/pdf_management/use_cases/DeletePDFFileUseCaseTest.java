package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.DeletePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class DeletePDFFileUseCaseTest {
	private DeletePDFFileUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFTransactionRepository transactionRepository = Mockito.mock(PDFTransactionRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new DeletePDFFileUseCase(pdfRepository, userRepository, transactionRepository);
		PDFPricingPolicy pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
		when(userRepository.isAdmin(3L)).thenReturn(false);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client3 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		List<PurchasePDFAccessTransaction> transactions = Arrays.asList(
				new PurchasePDFAccessTransaction(client2, pdf, client, pricingPolicy),
				new PurchasePDFAccessTransaction(client3, pdf, client, pricingPolicy)
				);
		when(transactionRepository.findAll()).thenReturn(transactions);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(3L)).thenReturn(client2);
		when(userRepository.findClient(4L)).thenReturn(client3);
		when(pdfRepository.find(1L)).thenReturn(pdf);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithTheOwner() {
		ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
		useCase.execute(1L, 1L);
	
		verify(pdfRepository).delete(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo(1L);
			return true;
	    }));
		
		verify(userRepository, times(3)).save(clientCaptor.capture());
		
		List<Client> clients = clientCaptor.getAllValues();
		
		Client buyer = clients.get(0);
		assertThat(buyer).isNotNull();
		assertThat(buyer.getId()).isEqualTo(3L);
		assertThat(buyer.getUsername()).isEqualTo("user3");
		assertThat(buyer.getPassword()).isEqualTo("123456");
		assertThat(buyer.getEmail()).isEqualTo("user3@mail.com");
		assertThat(buyer.getBalance()).isEqualTo(40);
		
		Client buyer2 = clients.get(1);
		assertThat(buyer2).isNotNull();
		assertThat(buyer2.getId()).isEqualTo(4L);
		assertThat(buyer2.getUsername()).isEqualTo("user4");
		assertThat(buyer2.getPassword()).isEqualTo("123456");
		assertThat(buyer2.getEmail()).isEqualTo("user4@mail.com");
		assertThat(buyer2.getBalance()).isEqualTo(40);
		
		Client owner = clients.get(2);
		assertThat(owner).isNotNull();
		assertThat(owner.getId()).isEqualTo(1L);
		assertThat(owner.getUsername()).isEqualTo("user");
		assertThat(owner.getPassword()).isEqualTo("123456");
		assertThat(owner.getEmail()).isEqualTo("user@mail.com");
		assertThat(owner.getBalance()).isEqualTo(10);
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithAnAdmin() {
		ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
		useCase.execute(2L, 1L);
	
		verify(pdfRepository).delete(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo(1L);
			return true;
	    }));
		
		verify(userRepository, times(3)).save(clientCaptor.capture());
		
		List<Client> clients = clientCaptor.getAllValues();
		
		Client buyer = clients.get(0);
		assertThat(buyer).isNotNull();
		assertThat(buyer.getId()).isEqualTo(3L);
		assertThat(buyer.getUsername()).isEqualTo("user3");
		assertThat(buyer.getPassword()).isEqualTo("123456");
		assertThat(buyer.getEmail()).isEqualTo("user3@mail.com");
		assertThat(buyer.getBalance()).isEqualTo(40);
		
		Client buyer2 = clients.get(1);
		assertThat(buyer2).isNotNull();
		assertThat(buyer2.getId()).isEqualTo(4L);
		assertThat(buyer2.getUsername()).isEqualTo("user4");
		assertThat(buyer2.getPassword()).isEqualTo("123456");
		assertThat(buyer2.getEmail()).isEqualTo("user4@mail.com");
		assertThat(buyer2.getBalance()).isEqualTo(40);
		
		Client owner = clients.get(2);
		assertThat(owner).isNotNull();
		assertThat(owner.getId()).isEqualTo(1L);
		assertThat(owner.getUsername()).isEqualTo("user");
		assertThat(owner.getPassword()).isEqualTo("123456");
		assertThat(owner.getEmail()).isEqualTo("user@mail.com");
		assertThat(owner.getBalance()).isEqualTo(10);
	}
	
	@Test
	void unauthorizedToDeletePDf() {	
		assertThatThrownBy(() -> {
			useCase.execute(3L, 1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
