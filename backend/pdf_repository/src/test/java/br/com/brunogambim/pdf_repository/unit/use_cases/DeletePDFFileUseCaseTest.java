package br.com.brunogambim.pdf_repository.unit.use_cases;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.use_cases.DeletePDFFileUseCase;

public class DeletePDFFileUseCaseTest {
	private DeletePDFFileUseCase useCase;
	private PDFRepository pdfRepository = Mockito.mock(PDFRepository.class);

	@BeforeEach
	void initUseCase() {
		useCase = new DeletePDFFileUseCase(pdfRepository);
	}
	
	
	@Test
	void repositoryMethodAreCalledWithTheCorrectParameterWithABigFile() {
		useCase.execute(1L);
	
		verify(pdfRepository).delete(argThat( x -> {
			assertThat(x).isNotNull();
			assertThat(x).isEqualTo(1L);
			return true;
	    }));
	}
}
