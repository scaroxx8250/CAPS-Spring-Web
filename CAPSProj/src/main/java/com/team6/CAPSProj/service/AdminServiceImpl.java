package com.team6.CAPSProj.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.team6.CAPSProj.model.Admin;
import com.team6.CAPSProj.repo.AdminRepository;

public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository arepo;
	@Override
	public Admin findByEmailAndPassword(String email, String password) {
		return arepo.findByEmailAndPassword(email, password);
	}

}
