package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.SaveNewPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class SaveNewPDFFileUseCaseTest {
	private SaveNewPDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 5, 10, 3, 10, 9));
		useCase = new SaveNewPDFFileUseCase(userRepository, managementParametersRepository, pdfRepository);
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		when(userRepository.isClient(1L)).thenReturn(true);
		when(userRepository.findClient(1L)).thenReturn(client);
	}
	
	
	@Test
	void useCaseExecutedWithABigFile() {
		useCase.execute(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isNull();
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(x.getSize()).isEqualTo(4);
			assertThat(x.getOwner().getId()).isEqualTo(1L);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
			return true;
	    }));
		
	}
	
	@Test
	void useCaseExecutedWithASmallFile() {
		useCase.execute(1L, "name", "desc", "pdf", 2, new byte[] {1,2});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isNull();
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getOwner().getId()).isEqualTo(1L);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			return true;
	    }));
	}
	
	@Test
	void useCaseExecutedWithInvalidFormat() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "jpg", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "mp4", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
	}
	
	@Test
	void useCaseExecutedWithInvalidDataSize() {
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
	void useCaseExecutedWithEmptyOrNullFields() {
		assertThatThrownBy(() -> {
			useCase.execute(1L,"", "desc", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
			
		assertThatThrownBy(() -> {
			useCase.execute(1L,"name", "desc", "pdf", 4, null);
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
	}
}
