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

    // JPA 스펙상 엔티티나 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는protected 로 설정해야 한다.
    protected Address() {
    }

    // 값 타입은 변경 불가능하게 설계해야 함, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스로 만듬
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
