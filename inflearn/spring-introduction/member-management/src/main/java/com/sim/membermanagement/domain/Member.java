package com.sim.membermanagement.domain;

import javax.persistence.*;

@Entity
public class Member {
    // 비즈니스 요구사항에서 필요한 데이터 2 가지 필드값

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템이 구분하기 위한 id 값(sequence 값이 넘어옴)
    private String name; // 고객이 회원가입할 때 적는 이름값


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
