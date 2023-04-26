package br.com.brunogambim.pdf_repository.unit.pdf_management.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDF;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFManagementParameters;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFPricingPolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFSizePolicy;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.Client;

public class PDFPricingPolicyTest {
	private PDFPricingPolicy policy;
	private PDFManagementParametersRepository managementParametersRepository = Mockito.mock(PDFManagementParametersRepository.class);

	@BeforeEach
	void initUseCase() {
		when(managementParametersRepository.findParameters())
		.thenReturn(new PDFManagementParameters(5, 3, 10, 5, 3, 10, 9));
		policy = new PDFPricingPolicy(managementParametersRepository);

	}
	
	@Test
	void smallFileWithFewEvaluations() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		
		assertThat(policy.execute(pdf)).isEqualTo(5);
		
		pdf.addEvaluation(client, 10);
		pdf.addEvaluation(client2, 10);
		pdf.addEvaluation(client3, 10);
		pdf.addEvaluation(client4, 10);
		pdf.addEvaluation(client5, 10);
		pdf.addEvaluation(client6, 10);
		pdf.addEvaluation(client7, 10);
		pdf.addEvaluation(client8, 10);
		pdf.addEvaluation(client9, 10);
		
		assertThat(policy.execute(pdf)).isEqualTo(5);
	}
	
	@Test
	void smalFileWithEnoughtEvaluationsAndLowEvaluationsMean() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		Client client10 = new Client(10L, "user10", "123456","user10@mail.com", 30);
		
		pdf.addEvaluation(client, 8);
		pdf.addEvaluation(client2, 8);
		pdf.addEvaluation(client3, 8);
		pdf.addEvaluation(client4, 8);
		pdf.addEvaluation(client5, 8);
		pdf.addEvaluation(client6, 8);
		pdf.addEvaluation(client7, 8);
		pdf.addEvaluation(client8, 8);
		pdf.addEvaluation(client9, 8);
		pdf.addEvaluation(client10, 8);
		
		assertThat(policy.execute(pdf)).isEqualTo(5);
	}
	
	@Test
	void smallFileWithEnoughtEvaluationsAndEnoughtEvaluationsMean() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 2, new byte[] {1,2},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		Client client10 = new Client(10L, "user10", "123456","user10@mail.com", 30);
		
		pdf.addEvaluation(client, 10);
		pdf.addEvaluation(client2, 10);
		pdf.addEvaluation(client3, 10);
		pdf.addEvaluation(client4, 10);
		pdf.addEvaluation(client5, 10);
		pdf.addEvaluation(client6, 10);
		pdf.addEvaluation(client7, 10);
		pdf.addEvaluation(client8, 10);
		pdf.addEvaluation(client9, 10);
		pdf.addEvaluation(client10, 10);
		
		assertThat(policy.execute(pdf)).isEqualTo(8);
	}
	
	@Test
	void bigFileWithFewEvaluations() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		
		assertThat(policy.execute(pdf)).isEqualTo(10);
		
		pdf.addEvaluation(client, 10);
		pdf.addEvaluation(client2, 10);
		pdf.addEvaluation(client3, 10);
		pdf.addEvaluation(client4, 10);
		pdf.addEvaluation(client5, 10);
		pdf.addEvaluation(client6, 10);
		pdf.addEvaluation(client7, 10);
		pdf.addEvaluation(client8, 10);
		pdf.addEvaluation(client9, 10);
		
		assertThat(policy.execute(pdf)).isEqualTo(10);
	}
	
	@Test
	void bigFileWithEnoughtEvaluationsAndLowEvaluationsMean() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		Client client10 = new Client(10L, "user10", "123456","user10@mail.com", 30);
		
		pdf.addEvaluation(client, 8);
		pdf.addEvaluation(client2, 8);
		pdf.addEvaluation(client3, 8);
		pdf.addEvaluation(client4, 8);
		pdf.addEvaluation(client5, 8);
		pdf.addEvaluation(client6, 8);
		pdf.addEvaluation(client7, 8);
		pdf.addEvaluation(client8, 8);
		pdf.addEvaluation(client9, 8);
		pdf.addEvaluation(client10, 8);
		
		assertThat(policy.execute(pdf)).isEqualTo(10);
	}
	
	@Test
	void bigFileWithEnoughtEvaluationsAndEnoughtEvaluationsMean() {
		PDF pdf = new PDF(1L,"name", "desc", "pdf", 4, new byte[] {1,2,3,4},
				new PDFSizePolicy(managementParametersRepository));
		Client client = new Client(1L, "user", "123456","user@mail.com", 30);
		Client client2 = new Client(2L, "user2", "123456","user2@mail.com", 30);
		Client client3 = new Client(3L, "user3", "123456","user3@mail.com", 30);
		Client client4 = new Client(4L, "user4", "123456","user4@mail.com", 30);
		Client client5 = new Client(5L, "user5", "123456","user5@mail.com", 30);
		Client client6 = new Client(6L, "user6", "123456","user6@mail.com", 30);
		Client client7 = new Client(7L, "user7", "123456","user7@mail.com", 30);
		Client client8 = new Client(8L, "user8", "123456","user8@mail.com", 30);
		Client client9 = new Client(9L, "user9", "123456","user9@mail.com", 30);
		Client client10 = new Client(10L, "user10", "123456","user10@mail.com", 30);
		
		pdf.addEvaluation(client, 10);
		pdf.addEvaluation(client2, 10);
		pdf.addEvaluation(client3, 10);
		pdf.addEvaluation(client4, 10);
		pdf.addEvaluation(client5, 10);
		pdf.addEvaluation(client6, 10);
		pdf.addEvaluation(client7, 10);
		pdf.addEvaluation(client8, 10);
		pdf.addEvaluation(client9, 10);
		pdf.addEvaluation(client10, 10);
		
		assertThat(policy.execute(pdf)).isEqualTo(13);
	}
}
