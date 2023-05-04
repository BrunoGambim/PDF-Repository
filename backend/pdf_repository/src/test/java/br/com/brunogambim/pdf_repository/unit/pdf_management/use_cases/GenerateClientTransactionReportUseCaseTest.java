package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.ClientTransactionReport;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PurchasePDFAccessTransaction;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.GenerateClientTransactionReportUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class GenerateClientTransactionReportUseCaseTest {
	private GenerateClientTransactionReportUseCase useCase;
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFTransactionRepository transactionRepository = Mockito.mock(PDFTransactionRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private List<PurchasePDFAccessTransaction> asOwnerTransactions;
	private List<PurchasePDFAccessTransaction> asBuyerTransactions;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		PDFPricingPolicy pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		useCase = new GenerateClientTransactionReportUseCase(userRepository, transactionRepository, pdfRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 5);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf5 = new PDF(5L,"name5", "desc5", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client2);
		PDF pdf6 = new PDF(6L,"name6", "desc6", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client2);
		
		pdf2.addToCanBeAccessedByList(client2);
		pdf3.addToCanBeAccessedByList(client2);
		pdf4.addToCanBeAccessedByList(client2);
		pdf5.addToCanBeAccessedByList(client);
		pdf6.addToCanBeAccessedByList(client);
		
		asOwnerTransactions = Arrays.asList(
				new PurchasePDFAccessTransaction(client, pdf, client2, pricingPolicy),
				new PurchasePDFAccessTransaction(client, pdf2, client2, pricingPolicy),
				new PurchasePDFAccessTransaction(client, pdf3, client2, pricingPolicy)
				);
		
		asBuyerTransactions = Arrays.asList(
				new PurchasePDFAccessTransaction(client2, pdf4, client, pricingPolicy),
				new PurchasePDFAccessTransaction(client2, pdf5, client, pricingPolicy)
				);
		
		when(transactionRepository.findByBuyerId(1L)).thenReturn(asBuyerTransactions);
		when(transactionRepository.findByOwnerId(1L)).thenReturn(asOwnerTransactions);
		
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(false);
		when(userRepository.isAdmin(3L)).thenReturn(true);
	}
	
	
	@Test
	void shouldReturnTheCorrectListCalledAsClient() {
		ClientTransactionReport result = useCase.execute(1L, 1L);
	    assertThat(result.getTransactionAsBuyer()).isEqualTo(asBuyerTransactions);
		assertThat(result.getTransactionAsOwner()).isEqualTo(asOwnerTransactions);
	}
	
	@Test
	void shouldReturnTheCorrectListCalledAsAdmin() {
		ClientTransactionReport result = useCase.execute(3L, 1L);
	    assertThat(result.getTransactionAsBuyer()).isEqualTo(asBuyerTransactions);
		assertThat(result.getTransactionAsOwner()).isEqualTo(asOwnerTransactions);
	}
	
	@Test
	void unauthorizedToGenerateTheReport() {
		assertThatThrownBy(() -> {
			useCase.execute(2L, 1L);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
	
	
}
