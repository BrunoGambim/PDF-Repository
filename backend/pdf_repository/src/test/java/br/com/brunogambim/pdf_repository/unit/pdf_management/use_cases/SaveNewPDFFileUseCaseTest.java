package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizeManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEmptyOrNullFileFieldException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.SaveNewPDFFileUseCase;

public class SaveNewPDFFileUseCaseTest {
	private SaveNewPDFFileUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		useCase = new SaveNewPDFFileUseCase(pdfRepository, managementParametersRepository);
		when(managementParametersRepository.findSizeParameters())
		.thenReturn(new PDFSizeManagementParameters(5, 3));
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		useCase.execute("name", "pdf", 4, new byte[] {1,2,3,4});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(x.getSize()).isEqualTo(4);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
			return true;
	    }));
		
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithASmallFile() {
		useCase.execute("name", "pdf", 2, new byte[] {1,2});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			return true;
	    }));
	}
	
	@Test
	void pdfInvalidFormat() {
		assertThatThrownBy(() -> {
			useCase.execute("name", "jpg", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute("name", "mp4", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
	}
	
	@Test
	void pdfInvalidDataSize() {
		assertThatThrownBy(() -> {
			useCase.execute("name", "pdf", 4, new byte[] {1,2,3});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute("name", "pdf", 3, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute("name", "pdf", 6, new byte[] {1,2,3,4,5,6});
		}).isInstanceOf(InvalidFileDataSizeException.class);
	}
	
	@Test
	void pdfEmptyOrNullFields() {
		assertThatThrownBy(() -> {
			useCase.execute("", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
			
		assertThatThrownBy(() -> {
			useCase.execute("name", "pdf", 4, null);
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
	}
}
