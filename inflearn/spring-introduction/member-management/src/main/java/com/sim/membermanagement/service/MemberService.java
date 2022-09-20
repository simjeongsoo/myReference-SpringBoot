package com.sim.membermanagement.service;

import com.sim.membermanagement.domain.Member;
import com.sim.membermanagement.repository.MemberRepository;
import com.sim.membermanagement.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    // 기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성 -> private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 회원 리포지토리의 코드가 회원 서비스 코드를 DI 가능하게 변경
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
