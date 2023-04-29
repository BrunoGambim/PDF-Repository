package br.com.brunogambim.pdf_repository.core.user_management.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import br.com.brunogambim.pdf_repository.core.user_management.exceptions.InvalidUpdatePasswordCodeException;

public class UpdatePasswordCode {
	private String code;
	private LocalDateTime createdAt;
	private static int MAX_PERIOD = 5 * 60 * 1000;
	private static int CODE_LENGTH = 8;
	
	public UpdatePasswordCode() {
		this.code = generateRandomCode();
		this.createdAt = LocalDateTime.now();
	}
	
	private String generateRandomCode() {
		String result = "";
		for(int i  = 0; i < CODE_LENGTH; i++) {
			result = result + generateRandomDigit();
		}
		return result;
	}
	
	private String generateRandomDigit() {
		Random random = new Random();
		return Integer.toString(random.nextInt() % 10);
	}

	public String getCode() {
		return code;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void validateCode(String code) {
		Long timePeriod = Duration.between(createdAt, LocalDateTime.now()).toMillis();
		if(MAX_PERIOD < timePeriod || !code.equals(this.code)) {
			throw new InvalidUpdatePasswordCodeException();
		}
	}
}
