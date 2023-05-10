package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByOwnerNameUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFInfoByOwnerNameUseCaseTest {
	private FindPDFInfoByOwnerNameUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private PDFPricingPolicy pricingPolicy; 
	private List<PDF> pdfList;
	private List<PDF> pdfList2;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		useCase = new FindPDFInfoByOwnerNameUseCase(pdfRepository, managementParametersRepository, userRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf5 = new PDF(5L,"2name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf6 = new PDF(6L,"2name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf7 = new PDF(7L,"2name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		PDF pdf8 = new PDF(8L,"2name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository), client);
		pdfList = Arrays.asList(pdf, pdf2, pdf3, pdf4);
		pdfList2 = Arrays.asList(pdf5, pdf6, pdf7, pdf8);
		when(pdfRepository.findPDFFilesByOwnerNameContains("name")).thenReturn(pdfList);
		when(pdfRepository.findPDFFilesByOwnerNameContains("2name")).thenReturn(pdfList2);
		when(userRepository.isAdmin(1L)).thenReturn(false);
	}
	
	
	@Test
	void shouldReturnTheCorrectList() {
		List<Long> idList = pdfList.stream().map(pdf -> pdf.getPDFInfoWithoutData(pricingPolicy).getId()).toList();
		List<Long> idList2 = pdfList2.stream().map(pdf -> pdf.getPDFInfoWithoutData(pricingPolicy).getId()).toList();
		List<Long> resultIds = useCase.execute(null, "name").stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(resultIds).isEqualTo(idList);
		assertThat(resultIds.size()).isEqualTo(4);
		
		List<Long> resultIds2 = useCase.execute(null, "2name").stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(resultIds2).isEqualTo(idList2);
		assertThat(resultIds2.size()).isEqualTo(4);
	}
}
