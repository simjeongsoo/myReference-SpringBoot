package com.sim.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    // 배송 엔티티
    //--주문시 하나의 배송 정보를 생성
    // 주문(Order)과 배송(Delivery)은 일대일 관계 --//

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;                    // pk, 식별자

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)    //  Order 테이블에 있는 "delivery" 필드에 의해서 매핑되었다는 의미 , 읽기 전용
    private Order order;

    @Embedded
    private Address address;            // 주소 정보

    @Enumerated(EnumType.STRING)        // default : ORDINAL, ORDINAL로 구현 시 추가된 데이터에 의해 로직이 꼬임
    private DeliveryStatus status;      // [READY(준비), COMP(배송)]
}
