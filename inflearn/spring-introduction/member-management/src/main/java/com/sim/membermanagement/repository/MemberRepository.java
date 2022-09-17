package com.sim.membermanagement.repository;

import com.sim.membermanagement.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    // 아직 데이터 저장소가 선정되지 않았다는 시나리오 하에, 우선 인터페이스로 구현클래스를 변경 할 수 있도록 설계
    // 4가지 기능 인터페이스
    Member save(Member member); // 저장 기능

    Optional<Member> findById(Long id); // id 값으로 회원 찾는 기능
    // Optional : Java8의 기능, Optional로 감싸서 반환, null값을 포함하여 반환

    Optional<Member> findByName(String name);

    List<Member> findAll();
}
