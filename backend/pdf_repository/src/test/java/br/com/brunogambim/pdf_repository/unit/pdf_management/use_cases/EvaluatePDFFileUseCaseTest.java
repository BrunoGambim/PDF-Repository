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
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEvaluationValueException;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.EvaluatePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class EvaluatePDFFileUseCaseTest {
	private EvaluatePDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		useCase = new EvaluatePDFFileUseCase(pdfRepository, userRepository);
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 5, 10, 3, 10, 9));
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(false);
	}
	
	
	@Test
	void evaluateAPDFFileWithNoEvaluations() {
		Client owner = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf =  new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2}, new PDFSizePolicy(managementParametersRepository), owner);
		
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		pdf.addToCanBeAccessedByList(client);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(userRepository.findClient(1L)).thenReturn(client);
		
		useCase.execute(1L, 1L, 5);
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			assertThat(x.getEvaluations().get(1L)).isNotNull();
			assertThat(x.getEvaluations().get(2L)).isNull();
			assertThat(x.getEvaluations().get(1L).getValue()).isEqualTo(5);
			assertThat(x.getEvaluationMean()).isEqualTo(5);
			return true;
	    }));
	}
	
	@Test
	void evaluateAPDFFileWithOneEvaluation() {
		Client owner = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf =  new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2}, new PDFSizePolicy(managementParametersRepository), owner);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		pdf.addToCanBeAccessedByList(client2);
		pdf.addEvaluation(client2, 10);
		
		when(pdfRepository.find(1L)).thenReturn(pdf);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		pdf.addToCanBeAccessedByList(client);
		when(userRepository.findClient(1L)).thenReturn(client);
		
		useCase.execute(1L, 1L, 5);
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			assertThat(x.getEvaluations().get(1L)).isNotNull();
			assertThat(x.getEvaluations().get(1L).getValue()).isEqualTo(5);
			assertThat(x.getEvaluationMean()).isEqualTo(7.5);
			return true;
	    }));
	}
	
	@Test
	void invalidEvaluationValue() {
		Client owner = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf =  new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2}, new PDFSizePolicy(managementParametersRepository), owner);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		pdf.addToCanBeAccessedByList(client);
		when(userRepository.findClient(1L)).thenReturn(client);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L, 11);
		}).isInstanceOf(InvalidEvaluationValueException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L, -1);
		}).isInstanceOf(InvalidEvaluationValueException.class);
	}
}
