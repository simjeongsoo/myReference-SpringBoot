package com.sim.test.crudtestpractice.repository;

import com.sim.test.crudtestpractice.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest    // 단위 테스트 , JPA 관련 설정만 로드 , @Transactional 내장 , @Entity 스캔
public class MemberRepositoryTest {
    //--기본 JpaRepository 메서드 외의 메서드만 테스트 하면 되지만 공부를 위해 테스트 코드 작성--//

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[insert] - 회원 저장 테스트")
    void insert() throws Exception{                                                                         // Create
        //given
        String name = "test";
        String email = "email";

        Member mem = Member.builder()
                .name(name)
                .email(email)
                .build();

        //when
        Member savedMem = memberRepository.save(mem);

        //then
        assertThat(mem.getName()).isEqualTo(savedMem.getName());
        assertThat(mem.getEmail()).isEqualTo(savedMem.getEmail());
        assertThat(savedMem.getId()).isNotNull();
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("[select] - 저장된 회원 내부 아이디값으로 조회")
    void findById() throws Exception{                                                                         // Read
        //given
        Member givenMember1 = memberRepository.save(Member.builder()
                .name("name1")
                .email("email1")
                .build());

        Member givenMember2 = memberRepository.save(Member.builder()
                .name("name2")
                .email("email2")
                .build());

        //when
        Member findMember1 = memberRepository.findById(givenMember1.getId()).get();
        Member findMember2 = memberRepository.findById(givenMember2.getId()).get();

        //then
        log.info("member1 id = " + givenMember1.getId());
        log.info("member2 id = " + givenMember2.getId());
        log.info("findMember1 getName = " + findMember1.getName());
        log.info("findMember2 getName = " + findMember2.getName());
        assertThat(memberRepository.count()).isEqualTo(2);
        assertThat(givenMember1.getName()).isEqualTo(findMember1.getName());
        assertThat(givenMember2.getName()).isEqualTo(findMember2.getName());
//        assertThat(givenMember1.getId()).isEqualTo(1L); // 직접적인 id 값으로 테스트 x , 존재 여부만 확인
    }

    @Test
    @DisplayName("[select] - 저장된 회원 이름 조회")
    public void findByName() throws Exception{                                                              // Read
        //given
        Member givenMember1 = memberRepository.save(Member.builder()
                .name("name1")
                .build());

        Member givenMember2 = memberRepository.save(Member.builder()
                .name("name2")
                .build());

        //when
        Member findByName = memberRepository.findByName("name1").get();

        //then
        assertThat(givenMember1).isEqualTo(findByName);
//        assertThat(givenMember2).isEqualTo(findByName); //fail test
    }

    @Test
    @DisplayName("[select] - 전체 회원 조회")
    void findAll() throws Exception{                                                                        // Read
        //given
        Member givenMember1 = memberRepository.save(Member.builder()
                .name("name1")
                .email("email1")
                .build());

        Member givenMember2 = memberRepository.save(Member.builder()
                .name("name2")
                .email("email2")
                .build());

        //when
        List<Member> findAll = memberRepository.findAll();

        //then
        assertThat(findAll.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[update] - 회원 정보 수정 테스트")
    void update() throws Exception{                                                                  // Update
        //given
        Member givenMember = memberRepository.save(Member.builder()
                .name("name")
                .email("email")
                .build());

        //when
        memberRepository.findByName(givenMember.getName()).ifPresent(newMember ->{
            newMember.update("updateName","updateEmail");
            // 더티 체킹
            memberRepository.save(newMember);
        });

        //then
        Member updateMember = memberRepository.findByName("updateName").get();
        assertThat("updateName").isEqualTo(updateMember.getName());
        assertThat(memberRepository.count()).isEqualTo(1);
    }


    @Test
    @DisplayName("[delete] - 회원 정보 삭제 테스트")
    void delete() throws Exception{                                                                  // Delete
        //given
        Member givenMember = memberRepository.save(Member.builder()
                .name("name")
                .email("email")
                .build());

        //when
        memberRepository.findByName("name").ifPresent(n ->{
            memberRepository.delete(n);
        });

        //then
        assertThat(memberRepository.count()).isEqualTo(0);
    }
}
