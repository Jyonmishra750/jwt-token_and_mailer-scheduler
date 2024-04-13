package com.example.customercrud.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.customercrud.dto.CredentialDTO;
import com.example.customercrud.dto.CustomerRequestDTO;
import com.example.customercrud.dto.CustomerResponseDTO;
import com.example.customercrud.dto.MailMessageDTO;
import com.example.customercrud.entity.Customer;
import com.example.customercrud.entity.MailMessage;
import com.example.customercrud.entity.Token;
import com.example.customercrud.mapper.CustomerMapper;
import com.example.customercrud.service.CustomerJwtService;
import com.example.customercrud.service.CustomerService;
import com.example.customercrud.service.MailService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerJwtService customerJwtService;
	
	@PostMapping("/signup")
	public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customer) {
		if (customerService.checkCustomer(customer.getEmail())) {
			throw new RuntimeException("Email Already Exists.");
		} else {
			var user = CustomerMapper.modelMapper(customer);
			var result = customerService.saveCustomer(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.dtoMapper(result));
		}
	}
	
	@PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Token login(@RequestBody CredentialDTO credentialRequestDTO ) {
		var email = credentialRequestDTO.getEmail();
		
        if (customerService.checkCustomer(email)) {
        	var customerEmail = customerJwtService.loadUserByUsername(email);
            var token = customerJwtService.generateToken(String.valueOf(customerEmail));
            
            var message = "Login Successful!! Now you can access all APIs.";
            return new Token(token, message);
		} else {
			var token = "Failed to generate token.";
			var message = "Email Not Found";
			return new Token(token, message);
		}
        
        
    }
	
	@PostMapping("/sendMailToOne")
	public ResponseEntity<String> sendEmailToOneCustomer(@RequestBody MailMessage mailMessage){
		var email = mailMessage.getTo();
		if(customerService.checkCustomer(email)) {
			var body = mailMessage.getBody();
			var subject = mailMessage.getSubject();
			mailService.sendMailToParticularCustomer(email, subject, body);
			return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully.");
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Not Found.");
		}
	}
	
	@PostMapping("/sendMailToAll")
	public ResponseEntity<String> sendEmailToAllCustomer(){
		mailService.sendMailToEveryCustomers();
		return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully to all customers.");
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam String email) {
		customerService.deleteCustomer(email);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
	} 
	
	@GetMapping("/home")
	public String customerHome() {
		return "Welcome to Customer CRUD Project.";
	}
	
	@GetMapping("/allCustomers")
	public List<Customer> allCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/byAge")
	public List<Customer> customersByAge(@RequestParam int age) {
		return customerService.getCustomersByAge(age);
	}
	
	@GetMapping("/byName")
	public List<Customer> customersByName(@RequestParam String name) {
		return customerService.getCustomersByName(name);
	}
	
	@GetMapping("/byAddress")
	public List<Customer> customersByAddress(@RequestParam String address) {
		return customerService.getCustomersByAddress(address);
	}
	
	@GetMapping("/byEmail")
	public Optional<Customer> customerByEmail(@RequestParam String email) {
		return customerService.getCustomerByEmail(email);
	}
	
	@GetMapping("/byId")
	public Optional<Customer> customerById(@RequestParam Long id) {
		return customerService.getCustomerById(id);
	}	
}