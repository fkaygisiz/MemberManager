package com.example.member.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.member.model.Member;
import com.example.member.service.MemberService;

@RestController
@RequestMapping(MemberController.MEMBER_CONTROLLER_PATH)
public class MemberController {

	static final String MEMBER_CONTROLLER_PATH = "/members";

	@Autowired
	private MemberService memberService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Member> saveMember(@RequestBody Member m) throws URISyntaxException {
		if (m.getId() != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Member savedMember = memberService.saveMember(m);
		return ResponseEntity.created(new URI(MEMBER_CONTROLLER_PATH + "/" + savedMember.getId())).body(savedMember);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Member> getMember(@PathVariable Long id) {
		Member member = memberService.getMember(id);
		if (member == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(member);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity deleteMember(@PathVariable Long id) {
		Member member = memberService.getMember(id);
		if (member == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		memberService.deleteMember(new Member(id));
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity updateMember(@RequestBody Member memberToBeUpdated, @PathVariable Long id) {
		Member memberFromDB = memberService.getMember(memberToBeUpdated.getId());
		if (memberFromDB == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else if (!id.equals(memberFromDB.getId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		memberService.updateMember(memberToBeUpdated);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Member>> getAll() {
		List<Member> allMembers = memberService.getAllMembers();
		return ResponseEntity.ok(allMembers);
	}

}
