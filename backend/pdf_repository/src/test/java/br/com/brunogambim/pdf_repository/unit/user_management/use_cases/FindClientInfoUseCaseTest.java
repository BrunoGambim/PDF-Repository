package br.com.brunogambim.pdf_repository.unit.user_management.use_cases;

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
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.FindClientInfoUseCase;

public class FindClientInfoUseCaseTest {
	private FindClientInfoUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	private Client client;
	private List<PDF> pdfList;
	
	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		useCase = new FindClientInfoUseCase(userRepository, pdfRepository);
		client = new Client(1L, "user", "654321", "user@mail.com", 15);
		pdfList = Arrays.asList(new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
					new PDFSizePolicy(managementParametersRepository), client),
				new PDF(2L,"name2", "desc2", "pdf", 2, new byte[] {1,2},
						new PDFSizePolicy(managementParametersRepository), client));
		when(userRepository.findClient(1L)).thenReturn(client);
		when(pdfRepository.findPDFilesOwnedBy(1L)).thenReturn(pdfList);
	}
	
	
	
	@Test
	void methodCalledWithNormalUser() {
		ClientInfo clientInfo = client.getClientInfo(pdfList);
		ClientInfo result = useCase.execute(1L);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(clientInfo.getId());
		assertThat(result.getEmail()).isEqualTo(clientInfo.getEmail());
		assertThat(result.getUsername()).isEqualTo(clientInfo.getUsername());
		assertThat(result.getOwnedPDFList()).isEqualTo(clientInfo.getOwnedPDFList());
	}
}
