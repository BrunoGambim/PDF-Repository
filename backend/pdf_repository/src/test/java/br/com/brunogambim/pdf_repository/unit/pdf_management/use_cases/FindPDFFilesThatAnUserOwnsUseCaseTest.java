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
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFFilesThatAnUserOwnsUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class FindPDFFilesThatAnUserOwnsUseCaseTest {
	private FindPDFFilesThatAnUserOwnsUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private List<PDF> pdfList;
	private List<PDF> pdfList2;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new FindPDFFilesThatAnUserOwnsUseCase(userRepository);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf2 = new PDF(2L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf3 = new PDF(3L,"name3", "desc3", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf4 = new PDF(4L,"name4", "desc4", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf5 = new PDF(5L,"name5", "desc5", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		PDF pdf6 = new PDF(6L,"name6", "desc6", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		pdfList = Arrays.asList(pdf, pdf2, pdf3, pdf4);
		pdfList2 = Arrays.asList(pdf5, pdf6);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 5);
		client.addPDFToOwnedPDFList(pdf);
		client.addPDFToOwnedPDFList(pdf2);
		client.addPDFToOwnedPDFList(pdf3);
		client.addPDFToOwnedPDFList(pdf4);
		client.addPDFToHasAccessPDFList(pdf5);
		client.addPDFToHasAccessPDFList(pdf6);
		client2.addPDFToOwnedPDFList(pdf5);
		client2.addPDFToOwnedPDFList(pdf6);
		client2.addPDFToHasAccessPDFList(pdf2);
		client2.addPDFToHasAccessPDFList(pdf3);
		when(userRepository.findClient(1L)).thenReturn(client);
		when(userRepository.findClient(2L)).thenReturn(client2);
	}
	
	
	@Test
	void methodAreCalledWithNormaluser() {
		List<PDF> result = useCase.execute(1L);
		assertThat(result).isEqualTo(pdfList);
		assertThat(result.size()).isEqualTo(4);
		
		result = useCase.execute(2L);
		assertThat(result).isEqualTo(pdfList2);
		assertThat(result.size()).isEqualTo(2);
	}
}
