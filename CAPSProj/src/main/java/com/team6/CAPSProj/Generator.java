package com.team6.CAPSProj;

import java.util.UUID;

import org.springframework.boot.SpringApplication;

public class Generator {


	public static void main(String[] args) {
		
		for(int i = 0; i < 10; i++)
		{
			String uuid = UUID.randomUUID().toString().substring(0, 5);
			String email = uuid + "@gmail.com";
			System.out.println(email);

		}
		
		System.out.println();
		System.out.println();
	}
}
