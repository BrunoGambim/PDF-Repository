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
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateUserPasswordDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdatePasswordCodeDTO;
import br.com.brunogambim.pdf_repository.configs.TestConfigs;
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
public class UserControllerTest extends AbstractIntegrationTest {

	@Autowired
	JPAUserRepository userRepository;
	@Autowired
	PasswordEncripterGateway encripter;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static RequestSpecification specification;
	
	@Test
	@Order(0)
	public void insertDBData() {
		ClientModel client = new ClientModel(null, "2user", encripter.encript("123456"), "2user@mail.com", null, 30);
		ClientModel client2 = new ClientModel(null, "2user2", encripter.encript("123456"), "2user2@mail.com", null, 30);
		ClientModel client3 = new ClientModel(null, "2user3", encripter.encript("123456"), "2user3@mail.com", null, 30);
		AdminModel admin = new AdminModel(null, "admin", encripter.encript("123456"), "admin2@mail.com", null);
		Arrays.asList(client, client2, client3, admin)
			.forEach(c -> userRepository.save(c));
		
		specification = new RequestSpecBuilder()
				.setBasePath(TestConfigs.USERS_V1_PATH)
				.build();
	}
	
	@Test
	@Order(1)
	public void sendUpdatePasswordCode() {
		UpdatePasswordCodeDTO dto = new UpdatePasswordCodeDTO("2user@mail.com");
		given().spec(specification)
				.contentType(ContentType.JSON)
				.body(dto)
				.when()
				.request("POST", "/updatePasswordCode")
				.then()
					.statusCode(204);
		
		assertThat(userRepository.findByEmail("2user@mail.com").get().getCode())
			.isNotNull();
		
		dto = new UpdatePasswordCodeDTO("admin2@mail.com");
		given().spec(specification)
			.contentType(ContentType.JSON)
			.body(dto)
			.when()
			.request("POST", "/updatePasswordCode")
			.then()
				.statusCode(204);
		
		assertThat(userRepository.findByEmail("admin2@mail.com").get().getCode())
			.isNotNull();
	}
	
	@Test
	@Order(1)
	public void updateUserPassword() {
		String code = userRepository.findByEmail("2user@mail.com").get().getCode().getCode();
		UpdateUserPasswordDTO dto = new UpdateUserPasswordDTO("654321",code,"2user@mail.com");
		given().spec(specification)
				.contentType(ContentType.JSON)
				.body(dto)
				.when()
				.request("PUT", "/password")
				.then()
					.statusCode(204);
	
		assertThat(passwordEncoder.matches("654321", userRepository.findByEmail("2user@mail.com").get().getPassword()))
			.isTrue();
		
		code = userRepository.findByEmail("admin2@mail.com").get().getCode().getCode();
		dto = new UpdateUserPasswordDTO("654321",code,"admin2@mail.com");
		given().spec(specification)
			.contentType(ContentType.JSON)
			.body(dto)
			.when()
			.request("PUT", "/password")
			.then()
				.statusCode(204);
		
		assertThat(passwordEncoder.matches("654321", userRepository.findByEmail("admin2@mail.com").get().getPassword()))
			.isTrue();
	}
}
