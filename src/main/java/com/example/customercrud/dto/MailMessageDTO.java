package com.example.customercrud.dto;

import jakarta.validation.constraints.NotNull;

public class MailMessageDTO {
	@NotNull(message = "Subject should not be Null")
	private String subject;
	@NotNull(message = "Body should not be Null")
	private String body;
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
	private MailMessageDTO(@NotNull(message = "Subject should not be Null") String subject,
			@NotNull(message = "Body should not be Null") String body) {
		super();
		this.subject = subject;
		this.body = body;
	}
	private MailMessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}