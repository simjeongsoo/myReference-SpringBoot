package com.sim.membermanagement;

import com.sim.membermanagement.repository.MemberRepository;
import com.sim.membermanagement.repository.MemoryMemberRepository;
import com.sim.membermanagement.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean // bean 등록 어노테이션
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
