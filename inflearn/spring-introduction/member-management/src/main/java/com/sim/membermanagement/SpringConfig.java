package com.sim.membermanagement;

import com.sim.membermanagement.repository.JdbcTemplateMemberRepository;
import com.sim.membermanagement.repository.JpaMemberRepository;
import com.sim.membermanagement.repository.MemberRepository;
import com.sim.membermanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final EntityManager em; // 엔티티를 관리하는 역할
    private final DataSource dataSource; // spring boot가 application.property를 스캔해 자체적으로 DB와 연결하는 DataSource 빈 생성

    @Autowired
    public SpringConfig(EntityManager em, DataSource dataSource) {
        this.em = em;
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
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em); // 구현체 교체, OCP
    }
}
