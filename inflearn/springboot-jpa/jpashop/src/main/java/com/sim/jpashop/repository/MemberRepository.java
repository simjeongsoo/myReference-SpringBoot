package com.sim.jpashop.repository;

import com.sim.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext         // spring이 EntityManager를 생성 후 주입
    private EntityManager em;

//    @PersistenceUnit            // EntityManagerFactory 주입
//    private EntityManagerFactory emf;

    //--저장--//
    private void save(Member member) {
        // em이 persist하면 영속성 컨텍스트에 member엔티티를 넣고,
        // 나중에 transaction이 commit되는 시점에 db에 insert쿼리를 날림
        em.persist(member);
    }

    //--단건 조회--//
    private Member findOne(Long id) {
        // jpa의 find()메서드사용 , 단건조회
        // (return 타입, pk)
        return em.find(Member.class, id);
    }

    //--목록 조회--//
    public List<Member> findAll() {
        // JPQL은 from의 대상이 table이 아니라 entity이다.
        List<Member> result = em.createQuery("select m from Member m", Member.class) // (JPQL, 반환타입)
                .getResultList();
        return result;
    }

    //--이름으로 검색--//
    public List<Member> findName(String name) {
        // 파라미터 바인딩으로 특정 회원을 이름으로 검색
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name) // "name"이 :name 에 바인딩
                .getResultList();
        return result;
    }
}
