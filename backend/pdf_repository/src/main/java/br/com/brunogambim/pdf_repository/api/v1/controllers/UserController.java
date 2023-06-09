package br.com.brunogambim.pdf_repository.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateUserPasswordDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdatePasswordCodeDTO;
import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SendPasswordUpdateCodeUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateUserPasswordUseCase;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

	private AuthenticationService authService;
	private PasswordEncripterGateway encripterGateway;
	private UserRepository userRepository;
	private EmailSenderGateway emailSenderGateway;
	
	@Autowired
	public UserController(AuthenticationService authService, UserRepository userRepository,
			PasswordEncripterGateway encripterGateway, EmailSenderGateway emailSenderGateway) {
		this.authService = authService;
		this.userRepository = userRepository;
		this.encripterGateway = encripterGateway;
		this.emailSenderGateway = emailSenderGateway;
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUserPassword(@RequestBody UpdateUserPasswordDTO dto){
		UpdateUserPasswordUseCase useCase = new UpdateUserPasswordUseCase(userRepository, encripterGateway);
		useCase.execute(dto.getEmail(), dto.getPassword(), dto.getCode());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/updatePasswordCode", method = RequestMethod.POST)
	public ResponseEntity<Void> sendUpdatePasswordCode(@RequestBody UpdatePasswordCodeDTO dto){
		SendPasswordUpdateCodeUseCase useCase = new SendPasswordUpdateCodeUseCase(userRepository,
				emailSenderGateway);
		useCase.execute(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse httpResponse){
		this.authService.refreshToken(httpResponse);
		return ResponseEntity.noContent().build();
	}
}
