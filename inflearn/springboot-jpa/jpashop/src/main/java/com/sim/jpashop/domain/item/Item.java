package com.sim.jpashop.domain.item;

import com.sim.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//--상속 관계 매핑--//
// 앨범, 도서, 영화 타입을 통합해서 하나의 테이블로 만들었다. (싱글 테이블 전략)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // 한 테이블에 모두 넣음(SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")                    // 상속관계 데이터 구분을 위함 , DTYPE 컬럼으로 타입을 구분
@Getter @Setter
public abstract class Item {
    // 상품 엔티티
    // 이름, 가격, 재고수량( stockQuantity )을 가짐, 상품을 주문하면 재고수량이 줄어든다.
    // 상품의 종류로는 도서, 음반, 영화가 있는데 각각은 사용하는 속성이 조금씩 다르다.

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;        // 상품 이름

    private int price;          // 상품 가격

    private int stockQuantity;  // 상품 재고

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
