package br.com.brunogambim.pdf_repository.api.v1.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.brunogambim.pdf_repository.api.v1.dtos.EvaluatePDFFileDTO;
import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import br.com.brunogambim.pdf_repository.core.pdf_management.adapters.PageAdapter;
import br.com.brunogambim.pdf_repository.core.pdf_management.entities.PDFInfo;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFManagementParametersRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.repositories.PDFTransactionRepository;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.ValidatePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.DeletePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.EvaluatePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFFilesThatAnUserHasAccessUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFFilesThatAnUserOwnsUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByIdUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByNameUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindPDFInfoByOwnerNameUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindReportedPDFFilesUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.FindWaitingForValidationPDFFilesUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.PurchaseAccessToPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.ReportPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.SaveNewPDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.pdf_management.use_cases.UpdatePDFFileUseCase;
import br.com.brunogambim.pdf_repository.core.user_management.repositories.UserRepository;

@RestController
@RequestMapping(value = "/v1/pdfs")
public class PDFController {

	private UserRepository userRepository;
	private PDFManagementParametersRepository pdfManagementParametersRepository;
	private PDFRepository pdfRepository;
	private PDFTransactionRepository transactionRepository;
	
	@Autowired
	public PDFController(UserRepository userRepository, PDFRepository pdfRepository,
			PDFManagementParametersRepository pdfManagementParametersRepository, PDFTransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.pdfRepository = pdfRepository;
		this.pdfManagementParametersRepository = pdfManagementParametersRepository;
		this.transactionRepository = transactionRepository;
	}
	
	@RequestMapping(value = "/{pdfId}/validate", method = RequestMethod.PUT)
	public ResponseEntity<Void> validatePDFFile(@PathVariable Long pdfId){
		ValidatePDFFileUseCase useCase = new ValidatePDFFileUseCase(pdfRepository, userRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{pdfId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePDFFile(@PathVariable Long pdfId){
		DeletePDFFileUseCase useCase = new DeletePDFFileUseCase(pdfRepository, userRepository, transactionRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{pdfId}/reported", method = RequestMethod.PUT)
	public ResponseEntity<Void> reportPDFFile(@PathVariable Long pdfId){
		ReportPDFFileUseCase useCase = new ReportPDFFileUseCase(pdfRepository, userRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{pdfId}/evaluation", method = RequestMethod.PUT)
	public ResponseEntity<Void> evaluatePDFFile(@PathVariable Long pdfId, @RequestBody EvaluatePDFFileDTO dto){
		EvaluatePDFFileUseCase useCase = new EvaluatePDFFileUseCase(pdfRepository, userRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId, dto.getEvaluationValue());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/hasAccess", method = RequestMethod.GET)
	public ResponseEntity<PageAdapter<PDFInfo>> findPDFFilesThatAnUserHasAccess(
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex){
		FindPDFFilesThatAnUserHasAccessUseCase useCase = new FindPDFFilesThatAnUserHasAccessUseCase(pdfRepository,
				pdfManagementParametersRepository);
		Long userId = AuthenticationService.authenticatedId();
		PageAdapter<PDFInfo> pdfs = useCase.execute(userId, pageIndex, pageSize);
		return ResponseEntity.ok(pdfs);
	}
	
	@RequestMapping(value = "/owned", method = RequestMethod.GET)
	public ResponseEntity<PageAdapter<PDFInfo>> findPDFFilesThatAnUserOwns(
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex){
		FindPDFFilesThatAnUserOwnsUseCase useCase = new FindPDFFilesThatAnUserOwnsUseCase(pdfRepository,
				pdfManagementParametersRepository);
		Long userId = AuthenticationService.authenticatedId();
		PageAdapter<PDFInfo> pdfs = useCase.execute(userId, pageIndex, pageSize);
		return ResponseEntity.ok(pdfs);
	}
	
	@RequestMapping(value = "/{pdfId}", method = RequestMethod.GET)
	public ResponseEntity<PDFInfo> findPDFInfoById(@PathVariable Long pdfId){
		Long userId = AuthenticationService.authenticatedId();
		FindPDFInfoByIdUseCase useCase = new FindPDFInfoByIdUseCase(pdfRepository, pdfManagementParametersRepository,
				userRepository);
		PDFInfo pdf = useCase.execute(userId, pdfId);
		return ResponseEntity.ok(pdf);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<PageAdapter<PDFInfo>> findPDFInfoByName(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(value="ownersName", required = false, defaultValue = "false") boolean ownersName,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex){
		Long userId = AuthenticationService.authenticatedId();
		if(ownersName) {
			FindPDFInfoByOwnerNameUseCase useCase = new FindPDFInfoByOwnerNameUseCase(pdfRepository, pdfManagementParametersRepository,
					userRepository);
			PageAdapter<PDFInfo> pdfs = useCase.execute(userId, name, pageIndex, pageSize);
			return ResponseEntity.ok(pdfs);
		}
		FindPDFInfoByNameUseCase useCase = new FindPDFInfoByNameUseCase(pdfRepository, pdfManagementParametersRepository,
				userRepository);
		PageAdapter<PDFInfo> pdfs = useCase.execute(userId, name, pageIndex, pageSize);
		return ResponseEntity.ok(pdfs);
	}

	@RequestMapping(value = "/reported", method = RequestMethod.GET)
	public ResponseEntity<PageAdapter<PDFInfo>> findReportedPDFFiles(
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex){
		FindReportedPDFFilesUseCase useCase = new FindReportedPDFFilesUseCase(pdfRepository, userRepository, 
				pdfManagementParametersRepository);
		Long userId = AuthenticationService.authenticatedId();
		PageAdapter<PDFInfo> pdfs = useCase.execute(userId, pageIndex, pageSize);
		return ResponseEntity.ok(pdfs);
	}
	
	@RequestMapping(value = "/waitingForValidation", method = RequestMethod.GET)
	public ResponseEntity<PageAdapter<PDFInfo>> findWaitingForValidationPDFFiles(
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex){
		FindWaitingForValidationPDFFilesUseCase useCase = new FindWaitingForValidationPDFFilesUseCase(pdfRepository, userRepository,
				pdfManagementParametersRepository);
		Long userId = AuthenticationService.authenticatedId();
		PageAdapter<PDFInfo> pdfs = useCase.execute(userId, pageIndex, pageSize);
		return ResponseEntity.ok(pdfs);
	}
	
	@RequestMapping(value = "/{pdfId}/hasAccess", method = RequestMethod.PUT)
	public ResponseEntity<Void> purchasePDFFileAccess(@PathVariable Long pdfId){
		PurchaseAccessToPDFFileUseCase useCase = new PurchaseAccessToPDFFileUseCase(userRepository, pdfRepository,
				pdfManagementParametersRepository, transactionRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void> createPDFFile(@RequestPart(name = "") MultipartFile file,
			@RequestParam(name = "description") String description) throws IOException{
		SaveNewPDFFileUseCase useCase = new SaveNewPDFFileUseCase(userRepository, pdfManagementParametersRepository, pdfRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, file.getOriginalFilename(), description, file.getContentType().split("/")[1], file.getBytes().length, file.getBytes());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/{pdfId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updatePDFFile(@PathVariable Long pdfId, @RequestPart(name = "") MultipartFile file,
			@RequestParam(name = "description") String description) throws IOException{
		UpdatePDFFileUseCase useCase = new UpdatePDFFileUseCase(pdfRepository, pdfManagementParametersRepository, userRepository);
		Long userId = AuthenticationService.authenticatedId();
		useCase.execute(userId, pdfId, file.getOriginalFilename(), description,
				file.getContentType().split("/")[1], file.getBytes().length, file.getBytes());
		return ResponseEntity.noContent().build();
	}
}