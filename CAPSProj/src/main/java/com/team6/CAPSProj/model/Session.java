package com.team6.CAPSProj.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionId; 
	

	private long timestamp;
	
	private String email;

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Session(int sessionId, long timestamp, String email) {
		super();
		this.sessionId = sessionId;
		this.timestamp = timestamp;
		this.email = email;
	}

	public Session(long timestamp, String email) {
		super();
		this.timestamp = timestamp;
		this.email = email;
	}

	public int getSessionId() {
		return sessionId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getEmail() {
		return email;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public void setTimestamp(long timestamp) {
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
