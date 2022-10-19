package com.sim.jwt.repository;

import com.sim.jwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // username을 기준으로 User 정보를 가져올 때 권한 정보도 같이 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    // @EntityGraph : 쿼리가 수행 될 때 Lazy 조회가 아니고 Eager 조회로 authorities 정보를 같이 가져옴

}
