package br.com.brunogambim.pdf_repository.unit.pdf_management.use_cases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

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
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.UpdatePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

public class UpdatePDFFileUseCaseTest {
	private UpdatePDFFileUseCase useCase;
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);
	Client owner;
	Client hasAccess;

	@BeforeEach
	void initUseCase() {
		useCase = new UpdatePDFFileUseCase(pdfRepository, managementParametersRepository, userRepository);
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 5, 10, 3, 10, 9));
		when(userRepository.isAdmin(1L)).thenReturn(false);
		when(userRepository.isAdmin(2L)).thenReturn(false);
		owner = new Client(1L, "user", "123456","user@mail.com", 30);
		hasAccess = new Client(2L, "user2", "123456","user2@mail.com", 30);
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository), owner);
		pdf.addToCanBeAccessedByList(hasAccess);
		when(pdfRepository.find(1L)).thenReturn(pdf);
		when(userRepository.findClient(1L)).thenReturn(owner);
		when(userRepository.findClient(2L)).thenReturn(hasAccess);
		when(pdfRepository.findPDFilesOwnedBy(1L)).thenReturn(Arrays.asList(pdf));
		when(pdfRepository.findPDFilesOwnedBy(2L)).thenReturn(new ArrayList<PDF>());
		when(pdfRepository.findPDFFilesThatCanBeAccessedBy(2L)).thenReturn(Arrays.asList(pdf));
		when(pdfRepository.findPDFFilesThatCanBeAccessedBy(1L)).thenReturn(new ArrayList<PDF>());
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		useCase.execute(1L, 1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2,3,4});
			assertThat(x.getSize()).isEqualTo(4);
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getStatus()).isEqualTo(PDFStatus.WAITING_FOR_ADMIN_VALIDATION);
			assertThat(x.getOwner()).isEqualTo(owner);
			assertThat(x.getCanBeAccessedBy()).contains(hasAccess);
			return true;
	    }));
		
	}
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithASmallFile() {
		useCase.execute(1L, 1L,"name", "desc", "pdf", 2, new byte[] {1,2});
	
		verify(pdfRepository).save(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x.getId()).isEqualTo(1L);
			assertThat(x.getName()).isEqualTo("name");
			assertThat(x.getData()).isEqualTo(new byte[] {1,2});
			assertThat(x.getSize()).isEqualTo(2);
			assertThat(x.getDescription()).isEqualTo("desc");
			assertThat(x.getStatus()).isEqualTo(PDFStatus.VALIDATED);
			assertThat(x.getOwner()).isEqualTo(owner);
			assertThat(x.getCanBeAccessedBy()).contains(hasAccess);
			return true;
	    }));
	}
	
	@Test
	void pdfInvalidFormat() {
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "jpg", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "mp4", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileFormatException.class);
	}
	
	@Test
	void pdfInvalidDataSize() {
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "pdf", 4, new byte[] {1,2,3});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "pdf", 3, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidFileDataSizeException.class);
		
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "pdf", 6, new byte[] {1,2,3,4,5,6});
		}).isInstanceOf(InvalidFileDataSizeException.class);
	}
	
	@Test
	void pdfEmptyOrNullFields() {
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"", "desc", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
			
		assertThatThrownBy(() -> {
			useCase.execute(1L, 1L,"name", "desc", "pdf", 4, null);
		}).isInstanceOf(InvalidEmptyOrNullFileFieldException.class);
	}
	
	@Test
	void unauthorizedToUpdatePDf() {	
		assertThatThrownBy(() -> {
			useCase.execute(2L, 1L,"", "desc", "pdf", 4, new byte[] {1,2,3,4});
		}).isInstanceOf(UnauthorizedUserException.class);
	}
}
