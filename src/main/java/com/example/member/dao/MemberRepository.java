package com.example.member.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.member.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

}
