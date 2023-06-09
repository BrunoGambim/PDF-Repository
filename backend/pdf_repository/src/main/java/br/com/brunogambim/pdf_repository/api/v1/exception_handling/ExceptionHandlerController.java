package br.com.brunogambim.pdf_repository.api.v1.exception_handling;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidEvaluationValueException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileDataSizeException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.InvalidFileFormatException;
import br.com.brunogambim.pdf_repository.core.pdf_management.exceptions.NegativeBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.EmailIsAlreadyBeingUsedException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidEmptyOrNullUserFieldException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;


@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandartError> defaultHandler(Exception exception, 
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",
				"Internal server error", httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standartError);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException objectNotFoundException, 
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not found",
				objectNotFoundException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standartError);
	}
	
	@ExceptionHandler(EmailIsAlreadyBeingUsedException.class)
	public ResponseEntity<StandartError> insufficientBalance(EmailIsAlreadyBeingUsedException emailIsAlreadyBeingUsedException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid email",
				emailIsAlreadyBeingUsedException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<StandartError> insufficientBalance(InsufficientBalanceException insufficientBalanceException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Insufficient balance",
				insufficientBalanceException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidUpdatePasswordCodeException.class)
	public ResponseEntity<StandartError> invalidUpdatePasswordCode(InvalidUpdatePasswordCodeException invalidUpdatePasswordCodeException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Invalid code",
				invalidUpdatePasswordCodeException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(UserAlreadyHasAccessToPDFException.class)
	public ResponseEntity<StandartError> userAlreadyHasAccessToPDF(UserAlreadyHasAccessToPDFException userAlreadyHasAccessToPDFException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid operation",
				userAlreadyHasAccessToPDFException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidEmptyOrNullUserFieldException.class)
	public ResponseEntity<StandartError> invalidEmptyOrNullUserField(InvalidEmptyOrNullUserFieldException invalidEmptyOrNullUserFieldException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Empty field",
				invalidEmptyOrNullUserFieldException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidEvaluationValueException.class)
	public ResponseEntity<StandartError> invalidEvaluationValue(InvalidEvaluationValueException invalidEvaluationValueException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid evaluation value",
				invalidEvaluationValueException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidFileDataSizeException.class)
	public ResponseEntity<StandartError> invalidFileDataSize(InvalidFileDataSizeException invalidFileDataSizeException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid file size",
				invalidFileDataSizeException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidFileFormatException.class)
	public ResponseEntity<StandartError> invalidFileFormat(InvalidFileFormatException invalidFileFormatException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid format size",
				invalidFileFormatException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(NegativeBalanceException.class)
	public ResponseEntity<StandartError> invalidFileFormat(NegativeBalanceException negativeBalanceException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Negative balance",
				negativeBalanceException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<StandartError> authorization(UnauthorizedUserException authorizationException, HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Unauthorized",
				authorizationException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standartError);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandartError> accessDenied(AccessDeniedException accessDeniedException, HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Unauthorized",
				"Access Denied", httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standartError);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolation(SQLIntegrityConstraintViolationException dataIntegrityException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Data integrity",
				"Data integrity violation.", httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
}
