package com.sim.jpashop.service;

import com.sim.jpashop.domain.Delivery;
import com.sim.jpashop.domain.Member;
import com.sim.jpashop.domain.Order;
import com.sim.jpashop.domain.OrderItem;
import com.sim.jpashop.domain.item.Item;
import com.sim.jpashop.repository.ItemRepository;
import com.sim.jpashop.repository.MemberRepository;
import com.sim.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional // 데이터 변경
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성 , 단순하게 회원에 있는 주소 정보로 세팅
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
//        OrderItem orderItem1 = new OrderItem()    // 'OrderItem()' has protected access in 'com.sim.jpashop.domain.OrderItem'

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        // cascade , delivery, orderItems 컬렉션에 데이터를 넣어두고 Order만 저장하면 컬렉션도 같이 저장
        // cascade는 참조하는
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 취소
     */

    /**
     * 검색
     */
}
