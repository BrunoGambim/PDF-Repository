package br.com.brunogambim.pdf_repository.gateways;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import br.com.brunogambim.pdf_repository.core.user_management.gateways.EmailSenderGateway;

@Service
public class EmailSenderGatewayImpl implements EmailSenderGateway{
	@Value("${default.sender}")
	private String sender;
	
	private MailSender mailSender;

	@Autowired
	public EmailSenderGatewayImpl(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void send(String email, String message, String subject) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(message);
		mailMessage.setTo(email);
		mailMessage.setFrom(sender);
		mailMessage.setSubject(subject);
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}
	
	
	
}
