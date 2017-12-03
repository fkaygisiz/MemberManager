package com.example.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.member.dao.MemberRepository;
import com.example.member.model.Member;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public Member getMember(Long id) {
		return memberRepository.findOne(id);
	}

	public Member saveMember(Member m) {
		return memberRepository.save(m);
	}

	public void deleteMember(Member m) {
		memberRepository.delete(m);
	}

	public void updateMember(Member m) {
		memberRepository.save(m);
	}

	public List<Member> getAllMembers() {
		return (List<Member>) memberRepository.findAll();
	}

}
