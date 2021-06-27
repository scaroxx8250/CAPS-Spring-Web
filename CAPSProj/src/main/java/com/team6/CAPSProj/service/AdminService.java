package com.team6.CAPSProj.service;

import com.team6.CAPSProj.model.Admin;

public interface AdminService {
	public Admin findByEmailAndPassword(String email, String password);
}
