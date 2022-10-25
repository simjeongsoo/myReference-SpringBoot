package com.sim.test.crudtestpractice.service;

import com.sim.test.crudtestpractice.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional  // for jpa
public class MemberService {
    //--리포지토리와 도메인을 활용하여 비즈니스 로직을 구현하는 단계--//

    private final MemberRepository memberRepository;

    //DI
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


}
