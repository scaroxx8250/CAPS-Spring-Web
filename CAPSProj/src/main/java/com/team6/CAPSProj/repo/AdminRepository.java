package com.team6.CAPSProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team6.CAPSProj.model.Admin;
import com.team6.CAPSProj.model.Lecturer;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	 Admin findByEmailAndPassword(String email, String password);
}
