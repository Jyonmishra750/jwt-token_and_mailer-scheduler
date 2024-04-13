package com.example.customercrud.entity;

import jakarta.validation.constraints.NotNull;

public class MailMessage {
	@NotNull(message = "To should not be Null")
	String to;
	@NotNull(message = "Subject should not be Null")
	String subject;
	@NotNull(message = "Body should not be Null")
	String body;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	private MailMessage(
			@NotNull(message = "To should not be Null") String to,
			@NotNull(message = "Subject should not be Null") String subject,
			@NotNull(message = "Body should not be Null") String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	private MailMessage(
			@NotNull(message = "Subject should not be Null") String subject,
			@NotNull(message = "Body should not be Null") String body) {
		super();
		this.subject = subject;
		this.body = body;
	}
	private MailMessage() {
		super();
	}	
}