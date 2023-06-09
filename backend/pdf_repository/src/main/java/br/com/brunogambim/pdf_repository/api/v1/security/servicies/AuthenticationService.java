package br.com.brunogambim.pdf_repository.api.v1.security.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.api.v1.security.entities.JWTUtils;
import br.com.brunogambim.pdf_repository.api.v1.security.entities.UserSS;
import br.com.brunogambim.pdf_repository.core.user_management.exceptions.UnauthorizedUserException;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
	private JWTUtils jwtUtil;
	
	@Autowired
	public AuthenticationService(JWTUtils jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	public void refreshToken(HttpServletResponse httpResponse) {
		UserSS user = (UserSS) authenticated().orElseThrow(() -> new UnauthorizedUserException());
		String jwtToken = this.jwtUtil.generateToken(user.getUsername(), user.getRole());
		httpResponse.addHeader("Authorization","Bearer " + jwtToken);
		httpResponse.addHeader("access-control-expose-headers","Authorization");
	}
	
	public void regenerateToken(HttpServletResponse httpResponse, String email) {
		UserSS user = (UserSS) authenticated().orElseThrow(() -> new UnauthorizedUserException());
		String jwtToken = this.jwtUtil.generateToken(email, user.getRole());
		httpResponse.addHeader("Authorization","Bearer " + jwtToken);
		httpResponse.addHeader("access-control-expose-headers","Authorization");
	}
	
	public static Optional<UserSS> authenticated() {
		try {
			return Optional.of((UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public static Long authenticatedId() {
		try {
			return ((UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		}
		catch (Exception e) {
			return null;
		}
	}
}
