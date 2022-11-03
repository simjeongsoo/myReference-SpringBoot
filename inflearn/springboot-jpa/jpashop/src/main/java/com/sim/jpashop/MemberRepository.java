package com.sim.jpashop;
// 엔티티 같은것들을 찾아주는 역할
// DAO 와 유사

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // springboot 자동 주입
    private EntityManager em;

    public Long save(Member member) {
        // return 값을 Member객체가 아닌 Long으로 받는 이유
        // commend와 쿼리를 분리
        // save를 동작하고 나면 사이드이펙트를 일으키는 커멘드 성이기 때문에 리턴값을 만들지 않음
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
