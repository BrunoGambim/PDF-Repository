package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.PurchaseAccessToPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class PurchaseAccessToPDFFileUseCaseTest {
	private PurchaseAccessToPDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFTransactionRepository transactionRepository = Mockito.mock(PDFTransactionRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new PurchaseAccessToPDFFileUseCase(userRepository, pdfRepository, managementParametersRepository, transactionRepository);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 5);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client3);
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository), client3);
		PDF pdf5 = new PDF(5L,"name5", "desc5", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository), client3);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 5);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository), client);
		pdf4.addToCanBeAccessedByList(client);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(pdfRepository.find(2L)).thenReturn(pdf2);
		when(pdfRepository.find(3L)).thenReturn(pdf3);
		when(pdfRepository.find(4L)).thenReturn(pdf4);
		when(pdfRepository.find(5L)).thenReturn(pdf5);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(2L)).thenReturn(client2);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
		useCase.execute(1L, 1L);
	
		verify(userRepository, times(2)).save(clientCaptor.capture());
		
		List<Client> clients = clientCaptor.getAllValues();
		
		Client buyer = clients.get(0);
		assertThat(buyer).isNotNull();
		assertThat(buyer.getId()).isEqualTo(1L);
		assertThat(buyer.getUsername()).isEqualTo("user");
		assertThat(buyer.getPassword()).isEqualTo("123456");
		assertThat(buyer.getEmail()).isEqualTo("user@mail.com");
		assertThat(buyer.getBalance()).isEqualTo(20);
		
		Client owner = clients.get(1);
		assertThat(owner).isNotNull();
		assertThat(owner.getId()).isEqualTo(3L);
		assertThat(owner.getUsername()).isEqualTo("user3");
		assertThat(owner.getPassword()).isEqualTo("123456");
		assertThat(owner.getEmail()).isEqualTo("user3@mail.com");
		assertThat(owner.getBalance()).isEqualTo(15);
		
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(x.getSize()).isEqualTo(4);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
			return true;
		}));
		
		verify(transactionRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isNull();
			assertThat(x.getBuyerId()).isEqualTo(1L);
			assertThat(x.getBuyerName()).isEqualTo("user");
			assertThat(x.getOwnerId()).isEqualTo(3L);
			assertThat(x.getOwnerName()).isEqualTo("user3");
			return true;
	    }));
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithASmallFile() {
		ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
		useCase.execute(1L, 5L);
		
		verify(userRepository, times(2)).save(clientCaptor.capture());
		List<Client> clients = clientCaptor.getAllValues();
		
		Client buyer = clients.get(0);
		assertThat(buyer).isNotNull();
		assertThat(buyer.getId()).isEqualTo(1L);
		assertThat(buyer.getUsername()).isEqualTo("user");
		assertThat(buyer.getPassword()).isEqualTo("123456");
		assertThat(buyer.getEmail()).isEqualTo("user@mail.com");
		assertThat(buyer.getBalance()).isEqualTo(25);

		Client owner = clients.get(1);
		assertThat(owner).isNotNull();
		assertThat(owner.getId()).isEqualTo(3L);
		assertThat(owner.getUsername()).isEqualTo("user3");
		assertThat(owner.getPassword()).isEqualTo("123456");
		assertThat(owner.getEmail()).isEqualTo("user3@mail.com");
		assertThat(owner.getBalance()).isEqualTo(10);
		
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(5L);
			assertThat(x.getName()).isEqualTo("name5");
			assertThat(x.getDescription()).isEqualTo("desc5");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			return true;
		}));
		
		verify(transactionRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isNull();
			assertThat(x.getBuyerId()).isEqualTo(1L);
			assertThat(x.getBuyerName()).isEqualTo("user");
			assertThat(x.getOwnerId()).isEqualTo(3L);
			assertThat(x.getOwnerName()).isEqualTo("user3");
			return true;
	    }));
	}
	
	@Test
	void userAlreadyHasAccessToThePDF() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,4L);
		}).isInstanceOf(UserAlreadyHasAccessToPDFException.class);
	}
	
	@Test
	void insufficientBalance() {
		assertThatThrownBy(() -> {
			useCase.execute(2L,1L);
		}).isInstanceOf(InsufficientBalanceException.class);
	}
}
