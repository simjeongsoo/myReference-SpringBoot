package com.sim.jpashop.service;

import com.sim.jpashop.domain.Member;
import com.sim.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA의 모든 변경이나 로직들은 트랜잭션 안에서 실행 되어야 한다.
// 클래스 범위에 @Transactional을 추가하면 public 메서드에 적용
@Transactional(readOnly = true)
//@AllArgsConstructor // lombok, 필드 생성자 자동 생성
@RequiredArgsConstructor // lombok, final 필드의 생성자만 생성
public class MemberService {

//    @Autowired
//    private MemberRepository memberRepository; // 필드 인젝션

//    private MemberRepository memberRepository;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) { // setter 인젝션
//        this.memberRepository = memberRepository;
//    }

//    private final MemberRepository memberRepository; // final 권장
//
////    @Autowired // 생성자가 하나인 경우 생략 가능
//    public MemberService(MemberRepository memberRepository) { // 생성자 인젝션
//        this.memberRepository = memberRepository;
//    }

    private final MemberRepository memberRepository; // lombok 생성자 인젝션

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
        // 실무에서는 검증 로직이 있어도 멀티 쓰레드 상황을 고려해서 회원 테이블의 회원명 컬럼에 유니크 제약 조건을 추가하는 것이 안전하다.
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
