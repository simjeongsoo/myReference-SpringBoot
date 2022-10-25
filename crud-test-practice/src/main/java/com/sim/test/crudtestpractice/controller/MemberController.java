package com.sim.test.crudtestpractice.controller;

import com.sim.test.crudtestpractice.domain.Member;
import com.sim.test.crudtestpractice.form.MemberForm;
import com.sim.test.crudtestpractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // Get 요청이 들어오면 메서드 실행
    public String createForm() {            // 회원 가입 페이지 리턴 api
        return "members/createMembersForm"; // createMembersForm 페이지 뿌려줌
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {      // 회원 등록 api

        // 멤버 엔티티에 데이터 저장
        Member member = Member.builder()
                .name(form.getFormName())
                .email(form.getFormEmail())
                .build();

        // 회원가입 로직 실행
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {       // 회원 목록 조회 api
        List<Member> allMembers = memberService.findMembers();
        model.addAttribute("allMembers", allMembers);
        return "members/memberList";
    }
}
