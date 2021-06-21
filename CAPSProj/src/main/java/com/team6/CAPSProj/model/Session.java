package com.team6.CAPSProj.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionId; 
	
	@FutureOrPresent
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm:ss") 
	private LocalDateTime timestamp;
	
	private String email;

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Session(int sessionId, @FutureOrPresent LocalDateTime timestamp, String email) {
		super();
		this.sessionId = sessionId;
		this.timestamp = timestamp;
		this.email = email;
	}

	public Session(@FutureOrPresent LocalDateTime timestamp, String email) {
		super();
		this.timestamp = timestamp;
		this.email = email;
	}

	public int getSessionId() {
		return sessionId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getEmail() {
		return email;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", timestamp=" + timestamp + ", email=" + email + "]";
	} 
	
	
	

}
