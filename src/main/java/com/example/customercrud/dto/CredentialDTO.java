package com.example.customercrud.dto;

import jakarta.validation.constraints.NotNull;

public class CredentialDTO {
	@NotNull(message = "Email should not be Null")
	private String email;
	@NotNull(message = "Password should not be Null")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private CredentialDTO(@NotNull(message = "Email should not be Null") String email,
			@NotNull(message = "Password should not be Null") String password) {
		super();
		this.email = email;
		this.password = password;
	}
	private CredentialDTO() {
		super();
		// TODO Auto-generated constructor stub
	}	
}