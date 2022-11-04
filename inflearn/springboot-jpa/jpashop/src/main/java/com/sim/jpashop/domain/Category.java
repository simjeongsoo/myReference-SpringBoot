package com.sim.jpashop.domain;

import com.sim.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    // 카테고리 엔티티
    //--계층구조 매핑, 자기자신을 self 매핑, 상품(Item)과 다대다 매핑으로 구현
    // parent , child 로 부모, 자식 카테고리를 연결--//


    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;                                            // pk, 식별자

    private String name;                                        // 카테고리 이름

    @ManyToMany // 실무에서 사용 x , 필드 추가 불가능
    // 관계형 DB는 양쪽에 컬렉션관계를 가질 수 없기때문에 중간 테이블을 만들어야함
    @JoinTable(name = "category_item",                          // 조인 테이블명
            joinColumns = @JoinColumn(name = "category_id"),    // 현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "item_id")  // 반대방향 엔티티를 참조하는 외래키
    )
    private List<Item> items = new ArrayList<>();


    //--셀프로 양방향 연관관계를 매핑--//
    @ManyToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 지연로딩으로 설정, , @xToOne 관계는 기본이 즉시로딩
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
