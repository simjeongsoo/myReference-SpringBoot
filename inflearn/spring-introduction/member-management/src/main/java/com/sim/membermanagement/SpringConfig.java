package com.sim.membermanagement;

import com.sim.membermanagement.repository.JdbcTemplateMemberRepository;
import com.sim.membermanagement.repository.MemberRepository;
import com.sim.membermanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource; // spring boot가 application.property를 스캔해 자체적으로 DB와 연결하는 DataSource 빈 생성

    @Autowired // DI
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean // bean 등록 어노테이션
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource); // 구현체 교체, OCP
    }
}
