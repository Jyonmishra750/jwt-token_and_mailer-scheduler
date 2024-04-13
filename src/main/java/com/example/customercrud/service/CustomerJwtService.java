package com.example.customercrud.service;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.customercrud.entity.Customer;
import com.example.customercrud.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class CustomerJwtService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;

	private final String secret = "385a472fe0ba0c4f085773084a786f59f0942e3e4abca5b0dffa5edbde9b1a57";
	
	public String generateToken(String email) {
        
		return Jwts.builder()
                .signWith(getKey())
                .subject(email)
                .compact();
	}
	
	public String validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
	
	private SecretKey getKey() {
        byte bytes[] = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found"));
		return customer;
	}
}