package com.sim.membermanagement.controller;

public class MemberForm {
    // 웹 등록 화면에서 데이터를 전달 받을 폼 객체

    private String name; // createMembersFrom.html 의 name="name" 과 매칭

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
