package br.com.brunogambim.pdf_repository.integration.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.brunogambim.pdf_repository.api.v1.dtos.CreateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.CredentialsDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientDTO;
import br.com.brunogambim.pdf_repository.configs.TestConfigs;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.database.mysql.models.AdminModel;
import br.com.brunogambim.pdf_repository.database.mysql.models.ClientModel;
import br.com.brunogambim.pdf_repository.database.mysql.repositories.JPAUserRepository;
import br.com.brunogambim.pdf_repository.integration.test_containers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ClientControllerTest extends AbstractIntegrationTest {

	@Autowired
	JPAUserRepository userRepository;
	@Autowired
	PasswordEncripterGateway encripter;
	
	private static RequestSpecification specification;
	
	@Test
	@Order(0)
	public void insertDBData() {
		ClientModel client = new ClientModel(null, "3user", encripter.encript("123456"), "3user@mail.com", null, 30);
		ClientModel client2 = new ClientModel(null, "3user2", encripter.encript("123456"), "3user2@mail.com", null, 30);
		ClientModel client3 = new ClientModel(null, "3user3", encripter.encript("123456"), "3user3@mail.com", null, 30);
		AdminModel admin = new AdminModel(null, "admin", encripter.encript("123456"), "admin3@mail.com", null);
		Arrays.asList(client, client2, client3, admin)
			.forEach(c -> userRepository.save(c));
		
		specification = new RequestSpecBuilder()
				.setBaseUri(TestConfigs.BASE_URI)
				.setBasePath(TestConfigs.CLIENTS_V1_PATH)
				.build();
	}
	
	@Test
	@Order(1)
	public void createClient() {
		CreateClientDTO dto = new CreateClientDTO("new client", "new@mail.com", "123456");
		given().spec(specification).relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
				.body(dto)
				.when()
				.request("POST", "")
				.then()
					.statusCode(201);
		assertThat(userRepository.findByEmail("new@mail.com").isEmpty()).isFalse();
		ClientModel client = (ClientModel) userRepository.findByEmail("new@mail.com").get();
		assertThat(client.getEmail()).isEqualTo("new@mail.com");
		assertThat(client.getUsername()).isEqualTo("new client");
		assertThat(client.getId()).isNotNull();
		assertThat(client.getCode()).isNull();
	}
	
	@Test
	@Order(2)
	public void findClient() {
		ClientInfo client = given().spec(specification).relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
				.param("email", "new@mail.com")
				.when()
				.request("GET", "")
				.then()
					.statusCode(200)
					.extract()
					.body()
						.as(ClientInfo.class);
		assertThat(client.getEmail()).isEqualTo("new@mail.com");
		assertThat(client.getUsername()).isEqualTo("new client");
		assertThat(client.getId()).isNotNull();
	}
	
	@Test
	@Order(3)
	public void authenticateAsClient() {
		CredentialsDTO user = new CredentialsDTO("new@mail.com", "123456");
		var authHeader = given()
				.baseUri(TestConfigs.BASE_URI)
				.basePath(TestConfigs.LOGIN_PATH)
				.relaxedHTTPSValidation()
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract().header(TestConfigs.AUTHORIZATION_HEADER);
		
		specification = new RequestSpecBuilder()
				.setBaseUri(TestConfigs.BASE_URI)
				.setBasePath(TestConfigs.CLIENTS_V1_PATH)
				.addHeader(TestConfigs.AUTHORIZATION_HEADER, authHeader)
				.build();
	}
	
	@Test
	@Order(4)
	public void updateClient() {
		UpdateClientDTO dto = new UpdateClientDTO("updated client", "updated@mail.com");
		Long id = userRepository.findByEmail("new@mail.com").get().getId();
		given().spec(specification).relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
				.body(dto)
				.when()
				.request("PUT", "/"+id)
				.then()
					.statusCode(204);
		assertThat(userRepository.findByEmail("updated@mail.com").isEmpty()).isFalse();
		ClientModel client = (ClientModel) userRepository.findByEmail("updated@mail.com").get();
		assertThat(client.getEmail()).isEqualTo("updated@mail.com");
		assertThat(client.getUsername()).isEqualTo("updated client");
		assertThat(client.getId()).isNotNull();
	}
}
