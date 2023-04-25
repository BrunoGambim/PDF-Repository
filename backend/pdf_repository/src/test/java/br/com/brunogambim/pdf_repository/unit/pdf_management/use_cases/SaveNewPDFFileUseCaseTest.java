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
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.SaveNewPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyOwnsThisPDFException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewPDFFileUseCaseTest {
	private SaveNewPDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 5, 10, 3, 10, 9));
		useCase = new SaveNewPDFFileUseCase(userRepository, managementParametersRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		client.addPDFToOwnedPDFList(new PDF(2L,"name2", "desc2", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		client.addPDFToOwnedPDFList(new PDF(3L,"name3", "desc3", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		client.addPDFToHasAccessPDFList(new PDF(4L,"name4", "desc4", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository)));
		when(userRepository.findClient(1L)).thenReturn(client);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		useCase.execute(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4});
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getId()).isEqualTo(1L);
			assertThat(client.getPassword()).isEqualTo("123456");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(30);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(3);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(1);
			PDF pdf = client.getOwnedPDFList().get(2);
			assertThat(pdf).isNotNull();
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
		useCase.execute(1L, "name", "desc", "pdf", 2, new byte[] {1,2});
	
		verify(userRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			Client client = (Client) x;
			assertThat(client.getUsername()).isEqualTo("user");
			assertThat(client.getId()).isEqualTo(1L);
			assertThat(client.getPassword()).isEqualTo("123456");
			assertThat(client.getEmail()).isEqualTo("user@mail.com");
			assertThat(client.getBalance()).isEqualTo(30);
			assertThat(client.getOwnedPDFList().size()).isEqualTo(3);
			assertThat(client.getHasAccessPDFList().size()).isEqualTo(1);
			PDF pdf = client.getOwnedPDFList().get(2);
			assertThat(pdf).isNotNull();
			assertThat(pdf.getName()).isEqualTo("name");
			assertThat(pdf.getDescription()).isEqualTo("desc");
			assertThat(pdf.getData()).isEqualTo(new byte[] {1,2});
			assertThat(pdf.getSize()).isEqualTo(2);
			assertThat(pdf.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			return true;
	    }));
	}
	
	@Test
	void userAlreadyOwnsThePDF() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name2", "desc2", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(UserAlreadyOwnsThisPDFException.class);
	}
	
	@Test
	void pdfInvalidFormat() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "jpg", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "mp4", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
	}
	
	@Test
	void pdfInvalidDataSize() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "pdf", 3, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "pdf", 6, new byte[] {1,2,3,4,5,6});
		}).isInstanceOf(InvalidFileDataSizeException.class);
	}
	
	@Test
	void pdfEmptyOrNullFields() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"", "desc", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
			
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "pdf", 4, null);
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
	}
}
