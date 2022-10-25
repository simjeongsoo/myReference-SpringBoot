package com.sim.test.crudtestpractice;

import com.sim.test.crudtestpractice.repository.MemberRepository;
import com.sim.test.crudtestpractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    //--Spring DI 클래스--//
    //--root에 위치해야 Spring이 스캔--//

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
