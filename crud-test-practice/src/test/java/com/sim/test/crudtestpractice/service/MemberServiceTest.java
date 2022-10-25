package com.sim.test.crudtestpractice.service;

import com.sim.test.crudtestpractice.domain.Member;
import com.sim.test.crudtestpractice.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
//@ExtendWith(SpringExtension.class)
//@Import({MemberService.class})
//@ExtendWith(MockitoExtension.class)
@SpringBootTest // integration test
@Transactional
public class MemberServiceTest {        // all test passed
    //--Service Layer 단위테스트로 구현해 볼 예정--//
    // service 에서는 id로 테스트 불가 , mock 로 가짜 객체 만들어서 테스트 해야함 -> auto_increment는 어떻게 처리 ?
    // AtomicLong

    //    @MockBean
//    private MemberRepository memberRepository;
//    @Autowired
//    private MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger("Service Test 의 로그");

    //    @BeforeEach // 테스트가 수행되기 전에 각각 실행 (테스트의 독립성을 위해)
//    public void beforeEach() {
//        memberService = new MemberService(memberRepository); // MemberService를 생성하면서 memberRepository 주입
//    }
//=============================================================================================================
    @Test
    @DisplayName("회원가입 테스트")
    void join() throws Exception{                       // test passed
        //given
        Member member = Member.builder()
                .name("testName")
                .email("testEmail")
                .build();

//        given(memberRepository.findByName("testName")).willReturn(Optional.empty());
//        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        Long joinId = memberService.join(member);


        //then
        Member findMember = memberService.findOne(joinId).get();
        logger.debug("logger findMember.getName() = "+findMember.getName());    // 로그 출력 안됌 ㅠㅠ
        logger.debug("joinId = "+findMember.getName());
        logger.debug("member.getId() = " + member.getName());
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }
    //=============================================================================================================
    @Test
    @DisplayName("중복 회원 예외 테스트")
    void validateDuplicateMember() throws Exception{     // test passed
        //given
        Member member1 = Member.builder()
                .name("test1")
                .build();

        Member member2 = Member.builder()
                .name("test1")
                .build();

        //when
        memberService.join(member1);        // member1 회원가입
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // member2 회원가입

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");     // 텍스트로 검증
    }
    //=============================================================================================================
    @Test
    @DisplayName("전체 회원 조회 테스트 ")
    void findAllMembers() throws Exception{               // test passed
        //given
        Member member1 = Member.builder()
                .name("test1")
                .build();

        Member member2 = Member.builder()
                .name("test2")
                .build();
        //when
        memberService.join(member1);
        memberService.join(member2);

        List<Member> allMembers = memberService.findMembers();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
    }
    //=============================================================================================================
    @Test
    @DisplayName("단일 회원 조회")
    void findOne() throws Exception{                 // test passed
        //given
        Member member = Member.builder()
                .name("nameT")
                .email("emailT")
                .build();

        //when
        Long joinId = memberService.join(member);
        Member findMember = memberService.findOne(joinId).get();

        //then
        assertThat(joinId).isEqualTo(findMember.getId());
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
//=============================================================================================================
/*    @Test // 단위테스트
    @DisplayName("전체 회원 조회 테스트")
    public void findAllMembers() throws Exception{          // mockito test passed
        //given

        // 테스트 멤버 객체 초기화
        Member givenMember = Member.builder()
                .name("testName")
                .email("testEmail")
                .build();


        //--가짜 memberRepository--//
        // memberList 에  givenMember 객체 추가
        List<Member> memberList = new ArrayList<>();
        memberList.add(givenMember);
        // memberRepository.findAll() 함수가 호출되면 memberList 반환
        when(memberRepository.findAll()).thenReturn(memberList);

        //when
        //--findAllMembers() 메소드 호출--//
        List<Member> allMembers = memberService.findAllMembers();
        Member findMember = allMembers.get(0);
        log.debug("givenMember = " + givenMember.getName());
        log.debug("findMember = " + findMember.getName());

        //then
        assertThat(givenMember.getName()).isEqualTo(findMember.getName());
//        verify(memberRepository,never()).findAll();
        verify(memberRepository).findAll();
    }*/
}