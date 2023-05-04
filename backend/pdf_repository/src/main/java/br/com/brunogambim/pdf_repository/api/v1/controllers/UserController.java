package br.com.brunogambim.pdf_repository.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunogambim.pdf_repository.api.v1.security.servicies.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private AuthenticationService authService;
	
	@Autowired
	public UserController(AuthenticationService authService) {
		this.authService = authService;
	}
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse httpResponse){
		this.authService.refreshToken(httpResponse);
		return ResponseEntity.noContent().build();
	}
}
