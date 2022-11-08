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
    @Transactional  // 데이터 변경
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel(); // 엔티티의 데이터만 바꾸면 JPA가 알아서 바뀐 변경포인트들을 찾아서 DB에 업데이트 쿼리를 날려줌(터티체킹, 변경내역감지)
                        // order.cancel() 을 호출하면  OrderStatus 가변경되어 Order에 업데이트 쿼리가 날라가고
                        // Item의 stockQuantity 도 업데이트 쿼리가 날라가 원복이 된다.
    }

    /**
     * 검색
     */
}
