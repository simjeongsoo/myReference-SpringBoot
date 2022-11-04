package com.sim.jpashop.service;

import com.sim.jpashop.domain.Member;
import com.sim.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //--회원 가입--//
    public Long join(Member member) {
        validateDuplicatedMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        // 중복 회원 검증 메서드
        List<Member> findMembers = memberRepository.findName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //--전체 회원 조회--//
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //--단일 회원 조회--//
    public Member findOne(Member member) {
        return memberRepository.findOne(member.getId());
    }
}
