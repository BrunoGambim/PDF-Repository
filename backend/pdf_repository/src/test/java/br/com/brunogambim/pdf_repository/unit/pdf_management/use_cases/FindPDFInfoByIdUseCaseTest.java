package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByIdUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFInfoByIdUseCaseTest {
	private FindPDFInfoByIdUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new FindPDFInfoByIdUseCase(pdfRepository, managementParametersRepository, userRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client3);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(pdfRepository.find(2L)).thenReturn(pdf2);
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(true);
	}
	
	
	@Test
	void useCaseExecutedWithUserLoggedOut() {
		PDFInfo pdf = useCase.execute(null, 1L);
		assertThat(pdf.getId()).isEqualTo(1L);
		assertThat(pdf.getDescription()).isEqualTo("desc");
		assertThat(pdf.getName()).isEqualTo("name");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user@mail.com");
		assertThat(pdf.getData()).isNull();
		
		pdf = useCase.execute(null, 2L);
		assertThat(pdf.getId()).isEqualTo(2L);
		assertThat(pdf.getDescription()).isEqualTo("desc2");
		assertThat(pdf.getName()).isEqualTo("name2");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user3");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user3@mail.com");
		assertThat(pdf.getData()).isNull();
	}
	
	@Test
	void useCaseExecutedWithAdmin() {
		PDFInfo pdf = useCase.execute(2L, 1L);
		assertThat(pdf.getId()).isEqualTo(1L);
		assertThat(pdf.getDescription()).isEqualTo("desc");
		assertThat(pdf.getName()).isEqualTo("name");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user@mail.com");
		assertThat(pdf.getData()).isNotNull();
		
		pdf = useCase.execute(2L, 2L);
		assertThat(pdf.getId()).isEqualTo(2L);
		assertThat(pdf.getDescription()).isEqualTo("desc2");
		assertThat(pdf.getName()).isEqualTo("name2");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user3");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user3@mail.com");
		assertThat(pdf.getData()).isNotNull();
	}
	
	@Test
	void useCaseExecutedWithClient() {
		PDFInfo pdf = useCase.execute(1L, 1L);
		assertThat(pdf.getId()).isEqualTo(1L);
		assertThat(pdf.getDescription()).isEqualTo("desc");
		assertThat(pdf.getName()).isEqualTo("name");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user@mail.com");
		assertThat(pdf.getData()).isNotNull();
		
		pdf = useCase.execute(1L, 2L);
		assertThat(pdf.getId()).isEqualTo(2L);
		assertThat(pdf.getDescription()).isEqualTo("desc2");
		assertThat(pdf.getName()).isEqualTo("name2");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user3");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user3@mail.com");
		assertThat(pdf.getData()).isNull();
	}
}
