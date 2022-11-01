package com.sim.test.crudtestpractice.dto;

import com.sim.test.crudtestpractice.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberJoinRequestDto {

    private String name;
    private String email;

    @Builder
    public MemberJoinRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    //--Dto to Entity--//
    public Member toEntity() {
        return Member.builder().name(name).email(email).build();
    }

}
