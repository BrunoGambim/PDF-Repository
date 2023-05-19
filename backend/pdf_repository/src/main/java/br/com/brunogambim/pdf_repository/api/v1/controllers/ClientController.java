package br.com.brunogambim.pdf_repository.api.v1.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.pdf_repository.api.v1.dtos.CreateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.user_management.entities.ClientInfo;
import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.FindClientInfoUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.SaveNewClientUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.use_cases.UpdateClientInfoUseCase;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1/clients")
public class ClientController {

	private UserRepository userRepository;
	private PasswordEncripterGateway encripterGateway;
	private PDFRepository pdfRepository;
	private AuthenticationService authenticationService;
	
	@Autowired
	public ClientController(UserRepository userRepository, PasswordEncripterGateway encripterGateway, AuthenticationService authenticationService,
			PDFTransactionRepository transactionRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.encripterGateway = encripterGateway;
		this.pdfRepository = pdfRepository;
		this.authenticationService = authenticationService;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ClientInfo> findClient(@RequestParam(name = "email") String email){
		FindClientInfoUseCase useCase = new FindClientInfoUseCase(userRepository);
		return ResponseEntity.ok(useCase.execute(email));
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> createClient(@RequestBody CreateClientDTO dto){
		SaveNewClientUseCase useCase = new SaveNewClientUseCase(userRepository, encripterGateway);
		useCase.execute(dto.getUsername(), dto.getEmail(), dto.getPassword());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("email", dto.getEmail()).build().toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{clientId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClient(HttpServletResponse httpResponse, @PathVariable Long clientId, @RequestBody UpdateClientDTO dto){
		UpdateClientInfoUseCase useCase = new UpdateClientInfoUseCase(userRepository, pdfRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, clientId, dto.getUsername(), dto.getEmail());
		authenticationService.regenerateToken(httpResponse, dto.getEmail());
		return ResponseEntity.noContent().build();
	}
}