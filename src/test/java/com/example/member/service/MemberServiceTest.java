package com.example.member.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.member.TestHelper;
import com.example.member.dao.MemberRepository;
import com.example.member.model.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void shouldSaveMember() {
		Member m = TestHelper.getMemberWithoutId();

		Member savedMember = memberService.saveMember(m);
		System.out.println("savedMember.getId() : " + savedMember.getId());
		assertNotNull(savedMember.getId());
	}

	@Test
	public void shouldUpdateMember() {
		Member m = TestHelper.getMemberWithoutId();
		Member savedMember = memberRepository.save(m);
		Long savedMemberId = savedMember.getId();

		savedMember.setFirstName("FatihUpdated");
		memberService.updateMember(savedMember);
		Member foundMember = memberRepository.findOne(savedMemberId);

		assertNotNull("FatihUpdated".equals(foundMember.getFirstName()));
	}

	@Test
	public void shouldDeleteMember() {
		Member m = TestHelper.getMemberWithoutId();
		Member savedMember = memberRepository.save(m);
		Long savedMemberId = savedMember.getId();
		assertNotNull(savedMemberId);

		memberService.deleteMember(savedMember);

		Member memberFromDb = memberRepository.findOne(savedMemberId);
		assertNull(memberFromDb);

	}

	@Test
	public void shouldGetMemberWhenMemberExists() {
		Member m = TestHelper.getMemberWithoutId();
		Member savedMember = memberRepository.save(m);
		Long savedMemberId = savedMember.getId();
		assertNotNull(savedMemberId);

		Member member = memberService.getMember(savedMemberId);
		assertNotNull(member);

	}
	
	@Test
	public void shouldGetNullWhenMemberDoesNotExist() {
		memberRepository.deleteAll();

		Member member = memberService.getMember(1L);
		assertNull(member);

	}
	
	@Test
	public void shouldGetAllMembers() {
		memberRepository.deleteAll();
		memberRepository.save(TestHelper.getMemberWithoutId());
		memberRepository.save(TestHelper.getMemberWithoutId());
		memberRepository.save(TestHelper.getMemberWithoutId());
		List<Member> allMembers = memberService.getAllMembers();
		assertTrue(allMembers.size() == 3);

	}
}
