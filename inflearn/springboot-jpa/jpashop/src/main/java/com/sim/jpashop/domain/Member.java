package com.sim.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    // 회원 엔티티
    //--name과, 임베디드 타입인 주소( Address ), 그리고 주문( orders ) 리스트를 가진다.--//

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;                // pk, 식별자

    private String name;            // 회원 이름

    @Embedded   // 내장 타입 포함
    private Address address;        // 주소 정보

    @OneToMany(mappedBy = "member") // Order 테이블에 있는 "memeber" 필드에 의해서 매핑되었다는 의미 , 읽기 전용
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화(null 무넺에서 안전)
}
