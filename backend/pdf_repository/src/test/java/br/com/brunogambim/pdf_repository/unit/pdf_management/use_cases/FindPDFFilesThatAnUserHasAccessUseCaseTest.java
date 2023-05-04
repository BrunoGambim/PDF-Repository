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
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFFilesThatAnUserHasAccessUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class FindPDFFilesThatAnUserHasAccessUseCaseTest {
	private FindPDFFilesThatAnUserHasAccessUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private PDFPricingPolicy pricingPolicy;
	private List<PDF> pdfList;
	private List<PDF> pdfList2;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		useCase = new FindPDFFilesThatAnUserHasAccessUseCase(pdfRepository, managementParametersRepository);
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
		pdfList = Arrays.asList(pdf5, pdf6);
		pdfList2 = Arrays.asList(pdf2, pdf3, pdf4);
		
		pdf2.addToCanBeAccessedByList(client2);
		pdf3.addToCanBeAccessedByList(client2);
		pdf4.addToCanBeAccessedByList(client2);
		pdf5.addToCanBeAccessedByList(client);
		pdf6.addToCanBeAccessedByList(client);

		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(pdfRepository.find(2L)).thenReturn(pdf2);
		when(pdfRepository.find(3L)).thenReturn(pdf3);
		when(pdfRepository.find(4L)).thenReturn(pdf4);
		when(pdfRepository.find(5L)).thenReturn(pdf5);
		when(pdfRepository.find(6L)).thenReturn(pdf6);
		
		when(pdfRepository.findPDFFilesThatCanBeAccessedBy(1L)).thenReturn(pdfList);
		when(pdfRepository.findPDFFilesThatCanBeAccessedBy(2L)).thenReturn(pdfList2);
	}
	
	
	@Test
	void methodAreCalledWithNormaluser() {
		List<Long> idList = pdfList.stream().map(pdf -> pdf.getPDFInfoWithData(pricingPolicy).getId()).toList();
		List<Long> idList2 = pdfList2.stream().map(pdf -> pdf.getPDFInfoWithData(pricingPolicy).getId()).toList();
		List<Long> result = useCase.execute(1L).stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(result).isEqualTo(idList);
		assertThat(result.size()).isEqualTo(2);
		
		result = useCase.execute(2L).stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(result).isEqualTo(idList2);
		assertThat(result.size()).isEqualTo(3);
	}
}
