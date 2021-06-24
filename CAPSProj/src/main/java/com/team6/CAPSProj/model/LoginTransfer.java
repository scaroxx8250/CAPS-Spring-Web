package com.team6.CAPSProj.model;

public class LoginTransfer {

	private String emailAdd;
	private String password;
	private Role role;
	
	public LoginTransfer(String emailAdd, String password, Role role) {
		super();
		this.emailAdd = emailAdd;
		this.password = password;
		this.role = role;
	}

	public LoginTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmailAdd() {
		return emailAdd;
	}

	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
}
