package br.com.brunogambim.pdf_repository.api.v1.exception_handling;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InsufficientBalanceException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UserAlreadyHasAccessToPDFException;
import br.com.brunogambim.pdf_repository.database.exceptions.ObjectNotFoundException;


@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException objectNotFoundException, 
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not found",
				objectNotFoundException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standartError);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<StandartError> insufficientBalance(InsufficientBalanceException insufficientBalanceException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Insufficient Balance",
				insufficientBalanceException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(InvalidUpdatePasswordCodeException.class)
	public ResponseEntity<StandartError> invalidUpdatePasswordCode(InvalidUpdatePasswordCodeException invalidUpdatePasswordCodeException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Invalid Code",
				invalidUpdatePasswordCodeException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(UserAlreadyHasAccessToPDFException.class)
	public ResponseEntity<StandartError> userAlreadyHasAccessToPDF(UserAlreadyHasAccessToPDFException userAlreadyHasAccessToPDFException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "User already has access to PDF",
				userAlreadyHasAccessToPDFException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standartError);
	}
	
	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<StandartError> authorization(UnauthorizedUserException authorizationException, HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Unauthorized",
				authorizationException.getMessage(), httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standartError);
	}
	
	/*@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolation(SQLIntegrityConstraintViolationException dataIntegrityException,
			HttpServletRequest httpServletRequest){
		StandartError standartError = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data integrity",
				"Data integrity violation.", httpServletRequest.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);
	}*/
}
