package com.sim.membermanagement.repository;

import com.sim.membermanagement.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //spring-data-jpa 가 JpaRepository 를 상속하면 SpringDataJpaMemberRepository 가 구현체를 자동으로 만들어서 스프링 빈에 등록

    // 네이밍 규칙을 지켜 메서드를 만들면 select m from Member m where m.name = ? 로 자동으로 JPQL 을 짜줌
    @Override
    Optional<Member> findByName(String name);
}
