package com.sim.jpashop.domain;

import com.sim.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 생성
public class OrderItem {
    // 주문 상품 엔티티
    //--주문한 상품 정보와 주문 금액( orderPrice ), 주문 수량( count ) 정보를 가짐
    // (보통 OrderLine , LineItem 으로 많이 표현)--//

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;                // pk, 식별자

    @ManyToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    @JoinColumn(name = "item_id")   // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    @JoinColumn(name = "order_id")  // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Order order;

    private int orderPrice;         // 주문 가격
    private int count;              // 주문 수량

//    protected OrderItem() {
//        // createOrderItem() 이외의 생성을 막아주는 protected 생성자
//    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // orderPrice 의 변경 가능성이 있기 때문에 파라미터로 따로 세팅

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문 상품 생성시 재고 감소
        return orderItem;
    }

    //==비즈니스 로직==/
    public void cancel() {
        getItem().addStock(count); // 재고수량 원복
    }

    //==조회 로직==//
    /**
     * 주문 상품 전제 가격 조회
     */
    public int getTotalPrice() {
        // 주문 가격 * 주문 수량
        return getOrderPrice() * getCount();
    }
}
