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
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByNameUseCase;

public class FindPDFInfoByNameUseCaseTest {
	private FindPDFInfoByNameUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private PDFPricingPolicy pricingPolicy; 
	private List<PDF> pdfList;
	private List<PDF> pdfList2;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		this.pricingPolicy = new PDFPricingPolicy(managementParametersRepository);
		useCase = new FindPDFInfoByNameUseCase(pdfRepository, managementParametersRepository);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf5 = new PDF(5L,"2name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf6 = new PDF(6L,"2name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf7 = new PDF(7L,"2name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf8 = new PDF(8L,"2name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		pdfList = Arrays.asList(pdf, pdf2, pdf3, pdf4);
		pdfList2 = Arrays.asList(pdf5, pdf6, pdf7, pdf8);
		when(pdfRepository.findPDFFilesNameContains("name")).thenReturn(pdfList);
		when(pdfRepository.findPDFFilesNameContains("2name")).thenReturn(pdfList2);
	}
	
	
	@Test
	void shouldReturnTheCorrectList() {
		List<Long> idList = pdfList.stream().map(pdf -> pdf.getPDFInfo(pricingPolicy).getId()).toList();
		List<Long> idList2 = pdfList2.stream().map(pdf -> pdf.getPDFInfo(pricingPolicy).getId()).toList();
		List<Long> resultIds = useCase.execute("name").stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(resultIds).isEqualTo(idList);
		assertThat(resultIds.size()).isEqualTo(4);
		
		List<Long> resultIds2 = useCase.execute("2name").stream().map(pdfInfo -> pdfInfo.getId()).toList();
		assertThat(resultIds2).isEqualTo(idList2);
		assertThat(resultIds2.size()).isEqualTo(4);
	}
}
