package com.sim.membermanagement.repository;

import com.sim.membermanagement.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // persist() 저장메서드
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 리스트에서 스캔하는 경우는 jpql 쿼리를 작성 해야함
        List<Member> result = em.createQuery("select m from Member as m where m.name = :name", Member.class)//jpql
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class) // jpql 쿼리언어
                .getResultList();
    }
}
