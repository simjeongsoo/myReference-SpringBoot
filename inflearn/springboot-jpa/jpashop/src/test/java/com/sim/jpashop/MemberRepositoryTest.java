package com.sim.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

// spring 종속적으로 설계 하기 때문에 springframework 의 transaction 사용
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional  // entity manager를 통한 모든 데이터 변경은 항상 transaction 안에서 이루어 져야 함
    @Rollback(value = false)
    public void memberTest() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // true , 같은 트랜잭션 안에서 저장하고 조회하면 같은 영속성 컨텍스트가 같음 , id 값이 같으면 같은 entity로 인식
        // JPA 엔티티 동일성 보장
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member = " + (findMember == member));

    }
}