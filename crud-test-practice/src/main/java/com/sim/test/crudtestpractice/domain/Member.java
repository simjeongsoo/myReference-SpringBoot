package com.sim.test.crudtestpractice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Builder
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void update(String name, String email) {
        //--더티 체킹--//
        this.name = name;
        this.email = email;
    }
}
