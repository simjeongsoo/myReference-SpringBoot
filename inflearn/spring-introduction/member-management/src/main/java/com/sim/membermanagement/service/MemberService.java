package com.sim.membermanagement.service;

import com.sim.membermanagement.domain.Member;
import com.sim.membermanagement.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //for jpa
public class MemberService {

    // 기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성 -> private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 회원 리포지토리의 코드가 회원 서비스 코드를 DI 가능하게 변경
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        long start = System.currentTimeMillis(); // 메서드 시간 측정 로직
        try {
            validateDuplicateMember(member); // 중복회원 검증
            memberRepository.save(member);
            return member.getId();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs+"ms");
        }
        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        // result 가 Optional이기 때문에 가능한 문법
        result.ifPresent(m -> { // result가 null이 아니라면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); // 가독성 있게 함수로 추출하여 관리 -> validateDuplicateMember()
         */
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
        long start = System.currentTimeMillis(); // 메서드 시간 측정 로직
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
