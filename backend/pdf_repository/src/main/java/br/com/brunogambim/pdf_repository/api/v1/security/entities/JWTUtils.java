package br.com.brunogambim.pdf_repository.api.v1.security.entities;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Integer expiration;
	
	public String generateToken(String username, String role) {
		JWTSubject subject = new JWTSubject(username, role);
		return Jwts.builder()
				.setSubject(subject.toString())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean isValidToken(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(claims.getSubject() != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims == null || claims.getSubject() == null) {
			return null;
		}
		JWTSubject subject = JWTSubject.fromString(claims.getSubject());
		return subject.getEmail();
	}
}
