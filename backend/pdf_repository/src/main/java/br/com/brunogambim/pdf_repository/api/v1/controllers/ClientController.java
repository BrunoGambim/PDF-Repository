package br.com.brunogambim.pdf_repository.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunogambim.pdf_repository.api.v1.dtos.CreateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientPasswordDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdatePasswordCodeDTO;
import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.FindClientInfoUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SaveNewClientUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SendUpdatePasswordCodeUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientInfoUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientPasswordUseCase;

@RestController
@RequestMapping(name = "/clients")
public class ClientController {

	private UserRepository userRepository;
	private PasswordEncripterGateway encripterGateway;
	private EmailSenderGateway emailSenderGateway;
	
	@Autowired
	public ClientController(UserRepository userRepository, PasswordEncripterGateway encripterGateway, 
			EmailSenderGateway emailSenderGateway) {
		this.userRepository = userRepository;
		this.encripterGateway = encripterGateway;
		this.emailSenderGateway = emailSenderGateway;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientInfo> getClient(@RequestParam(name = "id") Long id){
		FindClientInfoUseCase useCase = new FindClientInfoUseCase(userRepository);
		return ResponseEntity.ok(useCase.execute(id));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> postClient(@RequestBody CreateClientDTO dto){
		SaveNewClientUseCase useCase = new SaveNewClientUseCase(userRepository, encripterGateway);
		useCase.execute(dto.getUsername(), dto.getEmail(), dto.getPassword());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/updatePasswordCode", method = RequestMethod.POST)
	public ResponseEntity<Void> sendUpdatePasswordCode(@RequestBody UpdatePasswordCodeDTO dto){
		SendUpdatePasswordCodeUseCase useCase = new SendUpdatePasswordCodeUseCase(userRepository,
				emailSenderGateway);
		useCase.execute(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClient(@RequestParam(name = "id") Long clientId, @RequestBody UpdateClientDTO dto){
		UpdateClientInfoUseCase useCase = new UpdateClientInfoUseCase(userRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, clientId, dto.getUsername(), dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/updatePasswordCode/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClientPassword(@RequestParam(name = "id") Long clientId, @RequestBody UpdateClientPasswordDTO dto){
		UpdateClientPasswordUseCase useCase = new UpdateClientPasswordUseCase(userRepository, encripterGateway);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, clientId, dto.getPassword(), dto.getCode());
		return ResponseEntity.noContent().build();
	}
}