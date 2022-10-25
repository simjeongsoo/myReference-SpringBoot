package com.sim.test.crudtestpractice.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    // 웹 등록 화면에서 데이터를 전달 받을 폼 객체

    private String formName; // createMembersFrom.html 의 name="name" 과 매칭

    private String formEmail;

}
