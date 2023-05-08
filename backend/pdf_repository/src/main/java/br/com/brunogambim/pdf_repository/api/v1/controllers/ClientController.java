package br.com.brunogambim.pdf_repository.api.v1.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunogambim.pdf_repository.api.v1.dtos.CreateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdateClientPasswordDTO;
import br.com.brunogambim.pdf_repository.api.v1.dtos.UpdatePasswordCodeDTO;
import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.AllClientsTransactionReport;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.ClientTransactionReport;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.GenerateAllClientsTransactionReportUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.GenerateClientTransactionReportUseCase;
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
@RequestMapping(value = "/v1/clients")
public class ClientController {

	private UserRepository userRepository;
	private PasswordEncripterGateway encripterGateway;
	private EmailSenderGateway emailSenderGateway;
	private PDFRepository pdfRepository;
	private PDFTransactionRepository transactionRepository;
	
	@Autowired
	public ClientController(UserRepository userRepository, PasswordEncripterGateway encripterGateway, 
			EmailSenderGateway emailSenderGateway, PDFTransactionRepository transactionRepository, PDFRepository pdfRepository) {
		this.userRepository = userRepository;
		this.encripterGateway = encripterGateway;
		this.emailSenderGateway = emailSenderGateway;
		this.transactionRepository = transactionRepository;
		this.pdfRepository = pdfRepository;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientInfo> getClient(@PathVariable Long id){
		FindClientInfoUseCase useCase = new FindClientInfoUseCase(userRepository, pdfRepository);
		return ResponseEntity.ok(useCase.execute(id));
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> postClient(@RequestBody CreateClientDTO dto){
		SaveNewClientUseCase useCase = new SaveNewClientUseCase(userRepository, encripterGateway);
		Long id = useCase.execute(dto.getUsername(), dto.getEmail(), dto.getPassword());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/updatePasswordCode", method = RequestMethod.POST)
	public ResponseEntity<Void> sendUpdatePasswordCode(@RequestBody UpdatePasswordCodeDTO dto){
		SendUpdatePasswordCodeUseCase useCase = new SendUpdatePasswordCodeUseCase(userRepository,
				emailSenderGateway);
		useCase.execute(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{clientId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClient(@PathVariable Long clientId, @RequestBody UpdateClientDTO dto){
		UpdateClientInfoUseCase useCase = new UpdateClientInfoUseCase(userRepository, pdfRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, clientId, dto.getUsername(), dto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateClientPassword(@RequestBody UpdateClientPasswordDTO dto){
		UpdateClientPasswordUseCase useCase = new UpdateClientPasswordUseCase(userRepository, encripterGateway);
		useCase.execute(dto.getEmail(), dto.getPassword(), dto.getCode());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/transactionReport", method = RequestMethod.GET)
	public ResponseEntity<AllClientsTransactionReport> generateAllClientsTransactionReport(){
		GenerateAllClientsTransactionReportUseCase useCase = new GenerateAllClientsTransactionReportUseCase(userRepository, transactionRepository, pdfRepository);
		Long userId = AuthenticationService.authenticatedId();
		AllClientsTransactionReport report = useCase.execute(userId);
		return ResponseEntity.ok(report);
	}
	
	@RequestMapping(value = "/{clientId}/transactionReport", method = RequestMethod.GET)
	public ResponseEntity<ClientTransactionReport> generateClientTransactionReport(@PathVariable Long clientId){
		GenerateClientTransactionReportUseCase useCase = new GenerateClientTransactionReportUseCase(userRepository, transactionRepository, pdfRepository);
		Long userId = AuthenticationService.authenticatedId();
		ClientTransactionReport report = useCase.execute(userId, clientId);
		return ResponseEntity.ok(report);
	}
}