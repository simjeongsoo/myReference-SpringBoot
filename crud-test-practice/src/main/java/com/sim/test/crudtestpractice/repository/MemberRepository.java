package com.sim.test.crudtestpractice.repository;

import com.sim.test.crudtestpractice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //--org.springframework.data.repository.Repository를 상속한 interface 클래스는
    // @Repository Annotation을 생략해도 Spring 로딩시 자동으로 Repository로 등록 한다.--//

    // CRUD 처리를 위한 공통 인터페이스 제공
    // 공통 메소드가 아닐 경우에도 스프링 데이터 JPA가 메소드 이름을 분석해서 JPQL을 실행
    Optional<Member> findByName(String name);

    List<Member> findAll();
}
