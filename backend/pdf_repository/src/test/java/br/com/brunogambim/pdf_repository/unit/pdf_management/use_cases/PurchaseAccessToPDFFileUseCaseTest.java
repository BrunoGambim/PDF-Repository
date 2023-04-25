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
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.PurchaseAccessToPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class PurchaseAccessToPDFFileUseCaseTest {
	private PurchaseAccessToPDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new PurchaseAccessToPDFFileUseCase(userRepository, pdfRepository, managementParametersRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 5);
		client.addPDFToOwnedPDFList(new PDF(2L,"name2", "desc2", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		client.addPDFToOwnedPDFList(new PDF(3L,"name3", "desc3", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		client.addPDFToHasAccessPDFList(new PDF(4L,"name4", "desc4", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(2L)).thenReturn(client2);
		PDF pdf1 = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf2 = new PDF(5L,"name5", "desc5", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf3 = new PDF(4L,"name4", "desc4", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		when(pdfRepository.find(1L)).thenReturn(pdf1);
		when(pdfRepository.find(5L)).thenReturn(pdf2);
		when(pdfRepository.find(4L)).thenReturn(pdf3);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		useCase.execute(1L, 1L);
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getId()).isEqualTo(1L);
			assertThat(client.getPassword()).isEqualTo("123456");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(20);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(2);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(2);
			PDF pdf = client.getHasAccessPDFList().get(1);
			assertThat(pdf).isNotNull();
			assertThat(pdf.getId()).isEqualTo(1L);
			assertThat(pdf.getName()).isEqualTo("name");
			assertThat(pdf.getDescription()).isEqualTo("desc");
			assertThat(pdf.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(pdf.getSize()).isEqualTo(4);
			assertThat(pdf.getStatus()).isEqualTo(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
			return true;
	    }));
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithASmallFile() {
		useCase.execute(1L, 5L);
		
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getId()).isEqualTo(1L);
			assertThat(client.getPassword()).isEqualTo("123456");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(25);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(2);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(2);
			PDF pdf = client.getHasAccessPDFList().get(1);
			assertThat(pdf).isNotNull();
			assertThat(pdf.getId()).isEqualTo(5L);
			assertThat(pdf.getName()).isEqualTo("name5");
			assertThat(pdf.getDescription()).isEqualTo("desc5");
			assertThat(pdf.getData()).isEqualTo(new byte[] {1,2});
			assertThat(pdf.getSize()).isEqualTo(2);
			assertThat(pdf.getStatus()).isEqualTo(PDFStatus.VALIDATED);
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
