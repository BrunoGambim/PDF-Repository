package br.com.brunogambim.pdf_repository.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;

@Service
public class PasswordEncripterGatewayImpl implements PasswordEncripterGateway{
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public PasswordEncripterGatewayImpl(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	public String encript(String password) {
		return passwordEncoder.encode(password);
	}

}
