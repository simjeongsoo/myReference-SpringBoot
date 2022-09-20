package com.sim.membermanagement.service;

import com.sim.membermanagement.domain.Member;
import com.sim.membermanagement.repository.MemberRepository;
import com.sim.membermanagement.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 x

        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        // result 가 Optional이기 때문에 가능한 문법
        result.ifPresent(m -> { // result가 null이 아니라면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); // 가독성 있게 함수로 추출하여 관리 -> validateDuplicateMember()
         */

        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // findByName 반환값이 Optional인 것을 이용
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
