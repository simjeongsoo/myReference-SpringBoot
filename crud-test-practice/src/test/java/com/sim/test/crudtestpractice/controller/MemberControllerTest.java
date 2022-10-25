package com.sim.test.crudtestpractice.controller;

import com.sim.test.crudtestpractice.domain.Member;
import com.sim.test.crudtestpractice.form.MemberForm;
import com.sim.test.crudtestpractice.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)     // 웹 계층의 bean만 꺼내와 테스트
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;    // MemberController 에서 MemberService 에 의존하기때문에 MockBean 으로 대체

    private final AtomicLong count = new AtomicLong();  // join 시 id 값 autoincrement 를 위한 count

    @DisplayName("[회원가입 Controller test] - 회원 가입 페이지 리턴 api")
    @Test
    void createForm() throws Exception{
        //when & then
        this.mvc.perform(get("/members/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[회원가입 Controller test] - 회원가입 서비스 로직 호출 api")
    @Test
    void create() throws Exception{
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setFormName("testName");
        memberForm.setFormEmail("testEmail");

        Member member = Member.builder()
                .name(memberForm.getFormName())
                .email(memberForm.getFormEmail())
                .build();

        given(memberService.join(any())).willReturn(count.incrementAndGet());

        // when & then
        Long joinId = memberService.join(member);

        this.mvc.perform(post("/members/new"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertThat(joinId).isNotNull();
    }

    @DisplayName("[회원가입 Controller test] - 회원 목록 조회 api")
    @Test
    void list() throws Exception{
        //given
        Member member = Member.builder()
                .name("testName")
                .email("testEmail")
                .build();
        List<Member> list = new ArrayList<>();
        list.add(member);

        given(memberService.findMembers()).willReturn(list);

        //when & then
        this.mvc.perform(get("/members").accept(MediaType.TEXT_HTML))
                .andExpect(model().attribute("allMembers",list))    // model test
                .andDo(print())
                .andExpect(status().isOk());
    }
}
