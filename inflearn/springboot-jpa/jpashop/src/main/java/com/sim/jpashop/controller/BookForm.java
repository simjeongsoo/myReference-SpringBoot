package com.sim.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    //--상품 등록 폼 객체--//

    private Long id; // 상품 수정을 위한 id 값

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

}
