package com.sim.membermanagement.controller;

import com.sim.membermanagement.domain.Member;
import com.sim.membermanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //어노테이션을 달면 아무런기능이 없는 MemberController도 스프링컨테이너가 객체를 만들어 bean에 넣어두고 관리
public class MemberController {

    private final MemberService memberService;

    @Autowired // 의존관계 추가
    public MemberController(MemberService memberService) { // MemberService 주입
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // 회원등록 폼
    public String createForm() {
        return "members/createMembersForm";
    }

    @PostMapping("/members/new") // 회원등록 기능
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

//        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }
}
