package com.example.customercrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.customercrud.entity.Customer;
import com.example.customercrud.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Value("${spring.mail.username}")
	private String sender;
	

	@Scheduled(fixedRate = 300000)
	public void sendMailToEveryCustomers() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		List<Customer> customers = customerRepository.findAll(); // Fetch all users
		  for (Customer receiver : customers) {
		    String email = receiver.getEmail(); // Get email address
		    simpleMailMessage.setFrom(sender);
			simpleMailMessage.setSubject("This is a demo project");
			simpleMailMessage.setText("This project is realted to JWTW Token generation and sending mails to all customers in every 5 minutes.");
			simpleMailMessage.setTo(email);
			mailSender.send(simpleMailMessage);
		  }
	}

	public void sendMailToParticularCustomer(String email, String subject, String body) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);
		simpleMailMessage.setTo(email);
		mailSender.send(simpleMailMessage);
	}
}