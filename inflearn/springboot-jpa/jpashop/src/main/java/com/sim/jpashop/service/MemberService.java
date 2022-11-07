package com.sim.jpashop.service;

import com.sim.jpashop.domain.Member;
import com.sim.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA의 모든 변경이나 로직들은 트랜잭션 안에서 실행 되어야 한다.
// 클래스 범위에 @Transactional을 추가하면 public 메서드에 적용
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository; // 필드 인젝션

    //--회원 가입--//
    @Transactional // 데이터를 변경하는 메서드에는 readOnly = true 를 사용하면 안됨
    public Long join(Member member) {
        validateDuplicatedMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        // EXCEPTION
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
