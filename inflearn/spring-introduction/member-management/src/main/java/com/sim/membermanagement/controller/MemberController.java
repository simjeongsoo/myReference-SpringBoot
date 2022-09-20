package com.sim.membermanagement.controller;

import com.sim.membermanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //어노테이션을 달면 아무런기능이 없는 MemberController도 스프링컨테이너가 객체를 만들어 bean에 넣어두고 관리
public class MemberController {

    private final MemberService memberService;

    @Autowired // 의존관계 추가
    public MemberController(MemberService memberService) { // MemberService 주입
        this.memberService = memberService;
    }
}
