package com.sim.membermanagement.repository;

import com.sim.membermanagement.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest { // 다른데서 쓸거 아니라 굳이 public 으로 안함

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 테스트가 종료될 때 마다 이 메서드를 실행
    public void afterEach() {
        repository.clearStore();
    }

    @Test // test 메서드 실행 가능
    public void save() {
        //given
        Member member = new Member();
        member.setName("testName1");
        repository.save(member);

        //when
            // findById의 반환타입이 Optional -> get()으로 꺼냄
        Member result = repository.findById(member.getId()).get();

        //then
//        System.out.println("result = "+(result == member)); // 단순한 방식
//        Assertions.assertEquals(member,result);
        assertThat(member).isEqualTo(result); // save() 한 값과 findById로 꺼낸 값이 같다면 passed
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("testName1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("testName2");
        repository.save(member2);

        //when
        Member result = repository.findByName("testName1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("testName1");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("testName2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

    }
}
