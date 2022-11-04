package com.sim.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    // 주문 엔티티
    //--한 번 주문시 여러 상품을 주문할 수 있으므로 주문(Order)과 주문상품( OrderItem )은 일대다 관계
    // 주문은 상품을 주문한 회원과 배송 정보, 주문 날짜, 주문 상태( status )를 가짐
    // 주문 상태는 열 거형을 사용했는데 주문( ORDER ), 취소( CANCEL )을 표현할 수 있음--//

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;                    // pk, 식별자

    @ManyToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    @JoinColumn(name = "member_id")     // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Member member;

    @OneToMany(mappedBy = "order")      // OrderItem 테이블에 있는 "order" 필드에 의해서 매핑되었다는 의미 , 읽기 전용
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    @JoinColumn(name = "delivery_id")   // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Delivery delivery;

    private LocalDateTime orderDate;    // 주문 시간

    @Enumerated(EnumType.STRING)        // default : ORDINAL, ORDINAL로 구현 시 추가된 데이터에 의해 로직이 꼬임
    private OrderStatus orderStatus;    // 주문 상태 [ORDER, CANCEL]
}
