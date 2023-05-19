package br.com.brunogambim.pdf_repository.configs;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfigs {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String LOGIN_PATH = "/login";
	public static final String PDFS_V1_PATH = "/v1/pdfs";
}
