package br.com.brunogambim.pdf_repository.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.user_management.gateways.PasswordEncripterGateway;

@Service
public class PasswordEncripterGatewayImpl implements PasswordEncripterGateway{
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public PasswordEncripterGatewayImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}



	@Override
	public String encript(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

}
