package com.example.member;

import java.util.Date;

import com.example.member.model.Member;

public class TestHelper {

	public static Member getMemberWithoutId() {
		String firstName = "Fatih";
		String lastName = "Kaygisiz";
		Date dateOfBirth = new Date();
		String postalCode = "";

		return new Member(firstName, lastName, dateOfBirth, postalCode);
	}
	
	public static Member getMemberWithId(Long id) {
		String firstName = "Fatih";
		String lastName = "Kaygisiz";
		Date dateOfBirth = new Date();
		String postalCode = "";

		Member m = new Member(firstName, lastName, dateOfBirth, postalCode);
		m.setId(1L);
		return m;
		
	}
}
