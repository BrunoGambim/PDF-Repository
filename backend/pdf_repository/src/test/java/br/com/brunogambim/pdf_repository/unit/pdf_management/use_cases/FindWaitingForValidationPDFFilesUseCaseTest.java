package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindWaitingForValidationPDFFilesUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindWaitingForValidationPDFFilesUseCaseTest {
	private FindWaitingForValidationPDFFilesUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFPricingPolicy pricingPolicy;
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private List<PDF> pdfList;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		useCase = new FindWaitingForValidationPDFFilesUseCase(pdfRepository, userRepository,
				managementParametersRepository);
		when(userRepository.isAdmin(1L)).thenReturn(true);
		when(userRepository.isAdmin(2L)).thenReturn(false);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		pdfList = Arrays.asList(pdf, pdf2, pdf3, pdf4);
		when(pdfRepository.findAllWaitingForValidationPDFs(1, 1)).thenReturn(new PageAdapter<PDF>(pdfList, 1, 1, 1));
	}
	
	
	@Test
	void useCaseExecutedWithAdmin() {
		List<Long> idList = pdfList.stream().map(pdf -> pdf.getPDFInfoWithData(pricingPolicy).getId()).toList();
		List<Long> result = useCase.execute(1L, 1, 1).getItems().stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(result).isEqualTo(idList);
		assertThat(result.size()).isEqualTo(4);
	}
	
	@Test
	void useCaseExecutedWithUnauthorizedUser() {	
		assertThatThrownBy(() -> {
			useCase.execute(2L, 1, 1);
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
