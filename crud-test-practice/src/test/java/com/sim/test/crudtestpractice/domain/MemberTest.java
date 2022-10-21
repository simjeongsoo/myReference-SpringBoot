package com.sim.test.crudtestpractice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @DisplayName("도메인 테스트")
    @Test
    void Member() throws Exception{
        //given
        String name = "testName";
        String email = "testEmail";

        //when
        Member member = Member.builder()
                .name(name)
                .email(email)
                .build();

        //then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(email);
        // assertThat(검증대상)
    }
}
