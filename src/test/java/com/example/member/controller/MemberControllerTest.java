package com.example.member.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.member.TestHelper;
import com.example.member.model.Member;
import com.example.member.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {

	@Autowired
	private MemberController memberController;

	@MockBean
	private MemberService memberService;

	@Test
	public void shouldSaveMemberWhenMemberDoesNotExist() throws URISyntaxException {
		Member m = TestHelper.getMemberWithoutId();
		Member savedMember = TestHelper.getMemberWithId(1L);
		when(memberService.saveMember(m)).thenReturn(savedMember);
		ResponseEntity<Member> rs = memberController.saveMember(m);
		assertTrue(rs.getStatusCode() == HttpStatus.CREATED);
		assertTrue(rs.getBody().getId().equals(1L));
	}

	@Test
	public void shouldGetForbiddenErrorFromSaveMemberMethodWhenMemberIdExists() throws URISyntaxException {
		ResponseEntity rs = memberController.saveMember(new Member(1L));
		assertTrue(rs.getStatusCode() == HttpStatus.FORBIDDEN);
	}

	@Test
	public void shouldGetMemberFromGetMemberMethodWhenMemberExists() {
		Member m = TestHelper.getMemberWithId(1L);
		when(memberService.getMember(1L)).thenReturn(m);

		ResponseEntity<Member> re = memberController.getMember(1L);
		assertTrue(re.getStatusCode() == HttpStatus.OK);
		assertTrue(re.getBody().getId().equals(1L));
		assertTrue("Fatih".equals(re.getBody().getFirstName()));
	}

	@Test
	public void shouldGetNoContentErrorFromGetMemberMethodWhenMemberDoesNotExist() {
		when(memberService.getMember(anyLong())).thenReturn(null);
		ResponseEntity<Member> re = memberController.getMember(1L);
		assertTrue(re.getStatusCode() == HttpStatus.NO_CONTENT);
	}

	@Test
	public void shouldGetNoContentErrorFromDeleteMemberMethodWhenMemberDoesNotExist() {
		when(memberService.getMember(anyLong())).thenReturn(null);
		ResponseEntity re = memberController.deleteMember(1L);
		assertTrue(re.getStatusCode() == HttpStatus.NO_CONTENT);
	}

	@Test
	public void shouldDeleteMemberWhenMemberExists() {
		when(memberService.getMember(anyLong())).thenReturn(new Member(1L));
		doNothing().when(memberService).deleteMember(new Member(1L));
		ResponseEntity re = memberController.deleteMember(1L);
		assertTrue(re.getStatusCode() == HttpStatus.OK);
	}

	@Test
	public void shouldGetNoContentFromUpdateMemberMethodWhenMemberDoesNotExist() {
		Member m = TestHelper.getMemberWithId(1L);
		when(memberService.getMember(anyLong())).thenReturn(null);
		ResponseEntity<Member> re = memberController.updateMember(m, 1L);
		assertTrue(re.getStatusCode() == HttpStatus.NO_CONTENT);
	}

	@Test
	public void shouldGetConflictErrorFromUpdateMemberMethodWhenMemberExists() {
		Member m = TestHelper.getMemberWithId(1L);
		when(memberService.getMember(1L)).thenReturn(m);

		ResponseEntity re = memberController.updateMember(m, 2L);
		assertTrue(re.getStatusCode() == HttpStatus.CONFLICT);
	}

	@Test
	public void shouldUpdateMemberWhenMemberExists() {
		Member memberFromDB = TestHelper.getMemberWithId(1L);
		when(memberService.getMember(1L)).thenReturn(memberFromDB);
		doNothing().when(memberService).updateMember(any());
		Member memberToBeUpdated = TestHelper.getMemberWithId(1L);
		memberToBeUpdated.setFirstName("newName");
		ResponseEntity re = memberController.updateMember(memberToBeUpdated, 1L);
		verify(memberService).updateMember(memberToBeUpdated);
		assertTrue(re.getStatusCode() == HttpStatus.OK);
	}

	@Test
	public void shouldGetAllMembers() {

		Member m = TestHelper.getMemberWithId(1L);
		Member m2 = TestHelper.getMemberWithId(1L);

		List<Member> memberList = new ArrayList<Member>() {
			{
				add(m);
				add(m2);
			}
		};

		when(memberService.getAllMembers()).thenReturn(memberList);

		ResponseEntity<List<Member>> re = memberController.getAll();
		assertTrue(re.getStatusCode() == HttpStatus.OK);
		assertTrue(re.getBody().size() == 2);
	}
}
