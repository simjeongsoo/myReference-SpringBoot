package com.sim.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // jpa 내장 타입
@Getter
public class Address {
    //--값 타입(임베디드 타입), 회원(Member)과 배송(Delivery)에서 사용--//

    private String city;
    private String street;
    private String zipcode;

}
