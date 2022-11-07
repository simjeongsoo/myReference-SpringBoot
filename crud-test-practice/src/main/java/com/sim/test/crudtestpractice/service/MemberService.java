package com.sim.test.crudtestpractice.service;

import com.sim.test.crudtestpractice.domain.Member;
import com.sim.test.crudtestpractice.dto.MemberJoinRequestDto;
import com.sim.test.crudtestpractice.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional  // for jpa
public class MemberService {
    //--리포지토리와 도메인을 활용하여 비즈니스 로직을 구현하는 단계--//

    private final MemberRepository memberRepository;

    //DI
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입 로직 구현
     */
    //===========================================================================
    public Long joinGetEntity(Member member) {
        validateDuplicateMemberGetEntity(member);        // 중복 회원 조회 로직
        memberRepository.save(member);          // 회원 정보 DB 저장 로직
        return member.getId();                  // 회원 id 값 반환
    }

    private void validateDuplicateMemberGetEntity(Member member) {
        //--중복 회원 조회--//
        memberRepository.findByName(member.getName()).ifPresent(i -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    //===========================================================================
    
    public Long join(MemberJoinRequestDto joinRequestDto) {
        // Controller 계층에서 dto로 데이터를 받아 Service 계층에서 entity로 변환
        validateDuplicateMember(joinRequestDto);
        return memberRepository.save(joinRequestDto.toEntity()).getId();
    }

    private void validateDuplicateMember(MemberJoinRequestDto joinRequestDto) {
        memberRepository.findByName(joinRequestDto.toEntity().getName()).ifPresent(i->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * id로 회원 조회 (null일 경우를 대비해 Optional로 반환)
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
