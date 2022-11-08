package com.sim.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 생성
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

    // 1. OrderItem 테이블에 있는 "order" 필드에 의해서 매핑되었다는 의미 , 읽기 전용
    // 2. orderItems컬렉션에 데이터를 넣어두고 Order만 저장하면 컬렉션도 같이 저장
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    // Order를 persist 할때 delivery 엔티티도 같이 persist
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")   // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Delivery delivery;

    // order_date
    private LocalDateTime orderDate;    // 주문 시간

    // order_status
    @Enumerated(EnumType.STRING)        // default : ORDINAL, ORDINAL로 구현 시 추가된 데이터에 의해 로직이 꼬임
    private OrderStatus orderStatus;    // 주문 상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        // 생성 시점 변경시 이 메서드만 수정하면 됨
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);    // 주문 상태 세팅
        order.setOrderDate(LocalDateTime.now());    // 주문 시간 세팅
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            // 배송 완료시 취소 불가
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);    // OrderStatus 변경
        for (OrderItem orderItem : orderItems) {
            // 재고 원복 loop

            orderItem.cancel(); // orderItem 수량 원복
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;

        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}