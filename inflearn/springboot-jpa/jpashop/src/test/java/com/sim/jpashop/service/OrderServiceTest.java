package com.sim.jpashop.service;

import com.sim.jpashop.domain.Address;
import com.sim.jpashop.domain.Member;
import com.sim.jpashop.domain.Order;
import com.sim.jpashop.domain.OrderStatus;
import com.sim.jpashop.domain.item.Book;
import com.sim.jpashop.domain.item.Item;
import com.sim.jpashop.exception.NotEnoughStockException;
import com.sim.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        // 테스트를 위한 회원과 상품 생성
        Member member = createMember();
        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        // 실제 상품 주문
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //then
        // 주문 가격이 올바른지, 주문 후 재고 수량이 정확히 줄었는지 검증
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고

        int orderCount = 11; // 재고보다 많은 수량(재고 : 10)

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        // NotEnoughStockException
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        // 주문을 취소하면 그만큼 재고가 증가해야 한다.

        //given
        // 주문
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        // 주문 취소
        orderService.cancelOrder(orderId);

        //then
        // 주문상태가 주문 취소 상태인지( CANCEL ), 취소한 만큼 재고가 증가했는지 검증
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getOrderStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가 해야 한다.", 10, item.getStockQuantity());

    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회워1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }
}