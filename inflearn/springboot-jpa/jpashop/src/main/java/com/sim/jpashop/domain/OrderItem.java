package com.sim.jpashop.domain;

import com.sim.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    // 주문 상품 엔티티
    //--주문한 상품 정보와 주문 금액( orderPrice ), 주문 수량( count ) 정보를 가짐
    // (보통 OrderLine , LineItem 으로 많이 표현)--//

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;                // pk, 식별자

    @ManyToOne
    @JoinColumn(name = "item_id")   // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")  // fk , 연관 관계의 주인(수정과 업데이트가 이루어짐)
    private Order order;

    private int orderPrice;         // 주문 가격
    private int count;              // 주문 수량

}
