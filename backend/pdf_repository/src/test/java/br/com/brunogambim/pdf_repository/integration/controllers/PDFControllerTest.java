package br.com.brunogambim.pdf_repository.integration.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.brunogambim.pdf_repository.api.v1.dtos.CredentialsDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.EvaluatePDFFileDTO;
import br.com.brunogambim.pdf_repository.configs.TestConfigs;
import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFStatus;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.database.mysql.models.AdminModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.ClientModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.PDFModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAPDFRepository;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAUserRepository;
import br.com.brunogambim.pdf_repository.integration.test_containers.AbstractIntegrationTest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PDFControllerTest extends AbstractIntegrationTest {
	
	@Autowired
	JPAPDFRepository pdfRepository;
	@Autowired
	JPAUserRepository userRepository;
	@Autowired
	PasswordEncripterGateway encripter;
	
	private static RequestSpecification specification;
	
	@Test
	@Order(0)
	public void insertDBData() {
		ClientModel client = new ClientModel(null, "user", encripter.encript("123456"), "user@mail.com", null, 30);
		ClientModel client2 = new ClientModel(null, "user2", encripter.encript("123456"), "user2@mail.com", null, 30);
		ClientModel client3 = new ClientModel(null, "user3", encripter.encript("123456"), "user3@mail.com", null, 30);
		AdminModel admin = new AdminModel(null, "admin", encripter.encript("123456"), "admin@mail.com", null);
		Arrays.asList(client, client2, client3, admin)
			.forEach(c -> userRepository.save(c));
		 
		PDFModel pdf = new PDFModel(null, "name", "desc", 6, new byte[]{1,2,3,4,5,6}, 2, client,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel pdf2 = new PDFModel(null, "name2", "desc2", 6, new byte[]{1,2,3,4,5,6}, 2, client,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client2, client3)));
		PDFModel pdf3 = new PDFModel(null, "name3", "desc3", 6, new byte[]{1,2,3,4,5,6}, 2, client,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client2)));
		PDFModel pdf4 = new PDFModel(null, "name4", "desc4", 6, new byte[]{1,2,3,4,5,6}, 2, client2,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel pdf5 = new PDFModel(null, "name5", "desc5", 6, new byte[]{1,2,3,4,5,6}, 2, client2,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client)));
		PDFModel pdf6 = new PDFModel(null, "name6", "desc6", 6, new byte[]{1,2,3,4,5,6}, 2, client2
				, LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel pdf7 = new PDFModel(null, "name7", "desc7", 6, new byte[]{1,2,3,4,5,6}, 2, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel pdf8 = new PDFModel(null, "name8", "desc8", 6, new byte[]{1,2,3,4,5,6}, 2, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client)));
		PDFModel pdf9 = new PDFModel(null, "name9", "desc9", 6, new byte[]{1,2,3,4,5,6}, 2, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel pdf10 = new PDFModel(null, "name10", "desc10", 6, new byte[]{1,2,3,4,5,6}, 2, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client)));
		PDFModel pdf11 = new PDFModel(null, "name11", "desc11", 6, new byte[]{1,2,3,4,5,6}, 2, client,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel reported1 = new PDFModel(null, "rep1", "desc rep1", 6, new byte[]{1,2,3,4,5,6}, 3, client2,
				LocalDateTime.now(), new ArrayList<ClientModel>(Arrays.asList(client, client2)));
		PDFModel reported2 = new PDFModel(null, "rep2", "desc rep2", 6, new byte[]{1,2,3,4,5,6}, 3, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel waiting1 = new PDFModel(null, "waiting1", "desc waiting1", 6, new byte[]{1,2,3,4,5,6}, 1, client2,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		PDFModel waiting2 = new PDFModel(null, "waiting2", "desc waiting2", 6, new byte[]{1,2,3,4,5,6}, 1, client3,
				LocalDateTime.now(), new ArrayList<ClientModel>());
		Arrays.asList(pdf, pdf2, pdf3, pdf4, pdf5, pdf6, pdf7, pdf8, pdf9, pdf10, reported1, reported2, waiting1, waiting2, pdf11)
			.forEach(p -> pdfRepository.save(p));
		
		specification = new RequestSpecBuilder()
				.setBasePath(TestConfigs.PDFS_V1_PATH)
				.build();
	}
	
	@Test
	@Order(1)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByName() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2","name11"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10","desc rep1", "desc rep2","desc11"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNull());
		
		pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("name", "name4")
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name4"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc4"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNull());
	}
	
	@Test
	@Order(2)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByOwnerName() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2","name11"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10","desc rep1","desc rep2","desc11"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNull());
		
		pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("name", "user2")
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
		.isEqualTo(Arrays.asList("name4","name5","name6","rep1"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
		.isEqualTo(Arrays.asList("desc4","desc5","desc6","desc rep1"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNull());
		
		pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("name", "user3")
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name7","name8","name9","name10","rep2"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
		.isEqualTo(Arrays.asList("desc7","desc8","desc9","desc10","desc rep2"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNull());
	}
	
	@Test
	@Order(3)
	public void authenticateAsClient() {
		CredentialsDTO user = new CredentialsDTO("user@mail.com", "123456");
		var authHeader = given()
				.basePath(TestConfigs.LOGIN_PATH)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract().header(TestConfigs.AUTHORIZATION_HEADER);
		
		specification = new RequestSpecBuilder()
				.setBasePath(TestConfigs.PDFS_V1_PATH)
				.addHeader(TestConfigs.AUTHORIZATION_HEADER, authHeader)
				.build();
	}
	
	@Test
	@Order(4)
	public void deletePDFFileWithClient() {
		given().spec(specification)
			.when()
			.request("DELETE", "/15")
			.then()
				.statusCode(204);
		assertThat(pdfRepository.findAll().stream().map(pdf -> pdf.getId()).toList())
			.isEqualTo(Arrays.asList(
					1L,2L,3L,4L,5L,6L,7L,8L,9L,10L,11L,12L,13L,14L));
	}
	
	@Test
	@Order(5)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByNameAuthenticatedWithClient() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10", "desc rep1", "desc rep2"));
		assertThat(pdfs.getItems().stream().filter(pdf -> pdf.get("data") != null).map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name5","name8","name10","rep1"));
	}
	
	@Test
	@Order(6)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByOwnerNameAuthenticatedWithClient() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10", "desc rep1", "desc rep2"));
		assertThat(pdfs.getItems().stream().filter(pdf -> pdf.get("data") != null).map(pdf -> pdf.get("name")).toList())
		.isEqualTo(Arrays.asList("name","name2","name3","name5","name8","name10","rep1"));
	}
	
	@Test
	@Order(7)
	public void reportPDFFile() {
		given().spec(specification)
				.when()
				.request("PUT", "/5/reported")
				.then()
					.statusCode(204);
		assertThat(pdfRepository.findById(5L).get().getStatus())
		.isEqualTo(3);
	}
	
	@Test
	@Order(8)
	public void evaluatePDFFile() {
		EvaluatePDFFileDTO eval = new EvaluatePDFFileDTO(8); 
		given()
				.spec(specification)
				.body(eval)
				.contentType(ContentType.JSON)
				.when()
				.request("PUT", "/5/evaluation")
				.then()
				    .log().body()
					.statusCode(204);
		assertThat(pdfRepository.findById(5L).get().getEvaluations().get(0).getValue())
			.isEqualTo(8);
		assertThat(pdfRepository.findById(5L).get().getEvaluations().get(0).getEvaluator().getId())
			.isEqualTo(1L);
	}
	
	@Test
	@Order(9)
	@SuppressWarnings("unchecked")
	public void findPDFInfoThatAnUserOwns() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "/owned")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNotNull());
	}
	
	@Test
	@Order(10)
	@SuppressWarnings("unchecked")
	public void findPDFInfoThatAnUserHasAccess() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "/hasAccess")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name5","name8","name10","rep1"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc5","desc8","desc10","desc rep1"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNotNull());
	}
	
	@Test
	@Order(11)
	public void findPDFInfoByIdWithClient() {
		PDFInfo pdf = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.request("GET", "/5")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PDFInfo.class);
		assertThat(pdf.getId()).isEqualTo(5L);
		assertThat(pdf.getDescription()).isEqualTo("desc5");
		assertThat(pdf.getName()).isEqualTo("name5");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(1);
		assertThat(pdf.getEvaluationMean()).isEqualTo(8);
		assertThat(pdf.getOwnersName()).isEqualTo("user2");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user2@mail.com");
		assertThat(pdf.getData()).isNotNull();
		
		pdf = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.request("GET", "/6")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PDFInfo.class);
		assertThat(pdf.getId()).isEqualTo(6L);
		assertThat(pdf.getDescription()).isEqualTo("desc6");
		assertThat(pdf.getName()).isEqualTo("name6");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user2");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user2@mail.com");
		assertThat(pdf.getData()).isNull();
	}
	
	@Test
	@Order(12)
	public void purchasePDFFileAccess() {
		given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.request("PUT", "6/hasAccess")
				.then()
					.statusCode(204);
		assertThat(this.pdfRepository.findById(6L).get().getCanBeAccessedBy().stream().map(client -> client.getId()))
			.contains(1L);
	}
	
	@Test
	@Order(13)
	public void createPDFFile() throws IOException {
		MultiPartSpecification file = new MultiPartSpecBuilder(new byte[]{1,2,3,4,5,6})
				.controlName("file")
				.fileName("newname")
				.mimeType("application/pdf")
				.build();
		given().spec(specification)
				.multiPart(file)
				.formParam("description", "desc newname")
				.contentType(ContentType.MULTIPART)
				.when()
				.request("POST", "")
				.then()
					.statusCode(201);
		assertThat(this.pdfRepository.findAll().stream().map(pdf -> pdf.getName()).toList())
			.contains("newname");
	}
	
	@Test
	@Order(14)
	public void updatePDFFile() throws IOException {
		MultiPartSpecification file = new MultiPartSpecBuilder(new byte[]{1,2,3,4,5,6})
				.controlName("file")
				.fileName("renamed")
				.mimeType("application/pdf")
				.build();
		given().spec(specification)
				.multiPart(file)
				.formParam("description", "newdesc newname")
				.contentType(ContentType.MULTIPART)
				.when()
				.request("PUT", "/16")
				.then()
					.statusCode(204);
		PDFModel pdf = this.pdfRepository.findById(16L).get();
		assertThat(pdf.getName()).isEqualTo("renamed");
		assertThat(pdf.getDescription()).isEqualTo("newdesc newname");
		
		this.pdfRepository.deleteById(16L);
	}
	
	@Test
	@Order(15)
	public void authenticateAsAdmin() {
		CredentialsDTO user = new CredentialsDTO("admin@mail.com", "123456");
		var authHeader = given()
				.basePath(TestConfigs.LOGIN_PATH)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract().header(TestConfigs.AUTHORIZATION_HEADER);
		
		specification = new RequestSpecBuilder()
				.setBasePath(TestConfigs.PDFS_V1_PATH)
				.addHeader(TestConfigs.AUTHORIZATION_HEADER, authHeader)
				.build();
	}
	
	@Test
	@Order(16)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByNameAuthenticatedWithAdmin() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10", "desc rep1", "desc rep2"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNotNull());
	}
	
	@Test
	@Order(17)
	@SuppressWarnings("unchecked")
	public void findPDFInfoByOwnerNameAuthenticatedWithAdmin() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name","name2","name3","name4","name5","name6","name7","name8","name9","name10","rep1","rep2"));
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("description")).toList())
			.isEqualTo(Arrays.asList("desc","desc2","desc3","desc4","desc5","desc6","desc7","desc8","desc9","desc10", "desc rep1", "desc rep2"));
		pdfs.getItems().stream().map(pdf -> pdf.get("data")).forEach(data -> assertThat(data).isNotNull());
	}
	
	@Test
	@Order(18)
	public void validateReportedPDF() {
		given().spec(specification)
				.when()
				.request("PUT", "11/validate")
				.then()
					.statusCode(204);
		PageRequest pageRequest = PageRequest.of(0, 10);
		assertThat(pdfRepository.findByStatus(PDFStatus.REPORTED.getCode(), pageRequest).getContent()
			.stream().map(pdf -> pdf.getName()).toList())
		.isEqualTo(Arrays.asList("name5","rep2"));
	}
	
	@Test
	@Order(19)
	public void validateWaitingForValidationPDF() {
		given().spec(specification)
				.when()
				.request("PUT", "13/validate")
				.then()
					.statusCode(204);
		PageRequest pageRequest = PageRequest.of(0, 10);
		assertThat(pdfRepository.findByStatus(PDFStatus.WAITING_FOR_ADMIN_VALIDATION.getCode(), pageRequest).getContent()
			.stream().map(pdf -> pdf.getName()).toList())
		.isEqualTo(Arrays.asList("waiting2"));
	}
	
	@Test
	@Order(20)
	public void deletePDFFileWithAdmin() {
		given().spec(specification)
			.when()
			.request("DELETE", "/10")
			.then()
				.statusCode(204);
		assertThat(pdfRepository.findAll().stream().map(pdf -> pdf.getId()).toList())
			.isEqualTo(Arrays.asList(
					1L,2L,3L,4L,5L,6L,7L,8L,9L,11L,12L,13L,14L));
	}
	
	@Test
	@Order(21)
	public void findPDFInfoByIdWithAdmin() {
		PDFInfo pdf = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.request("GET", "/5")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PDFInfo.class);
		assertThat(pdf.getId()).isEqualTo(5L);
		assertThat(pdf.getDescription()).isEqualTo("desc5");
		assertThat(pdf.getName()).isEqualTo("name5");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(1);
		assertThat(pdf.getEvaluationMean()).isEqualTo(8);
		assertThat(pdf.getOwnersName()).isEqualTo("user2");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user2@mail.com");
		assertThat(pdf.getData()).isNotNull();
		
		pdf = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.request("GET", "/6")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PDFInfo.class);
		assertThat(pdf.getId()).isEqualTo(6L);
		assertThat(pdf.getDescription()).isEqualTo("desc6");
		assertThat(pdf.getName()).isEqualTo("name6");
		assertThat(pdf.getNumberOfEvaluations()).isEqualTo(0);
		assertThat(pdf.getEvaluationMean()).isEqualTo(0);
		assertThat(pdf.getOwnersName()).isEqualTo("user2");
		assertThat(pdf.getOwnersEmail()).isEqualTo("user2@mail.com");
		assertThat(pdf.getData()).isNotNull();
	}
	
	@Test
	@Order(22)
	@SuppressWarnings("unchecked")
	public void findReportedPDFFiles() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "/reported")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("name5","rep2"));
	}
	
	@Test
	@Order(23)
	@SuppressWarnings("unchecked")
	public void findWaitingForValidationPDFFiles() {
		PageAdapter<Map<String, String>> pdfs = given().spec(specification)
				.contentType(ContentType.JSON)
				.when()
				.param("ownersName", true)
				.param("pageSize", 20)
				.request("GET", "/waitingForValidation")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.as(PageAdapter.class);
		assertThat(pdfs.getItems().stream().map(pdf -> pdf.get("name")).toList())
			.isEqualTo(Arrays.asList("waiting2"));
	}
}
