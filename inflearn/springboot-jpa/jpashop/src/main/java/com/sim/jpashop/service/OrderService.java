package com.sim.jpashop.service;

import com.sim.jpashop.domain.*;
import com.sim.jpashop.domain.item.Item;
import com.sim.jpashop.repository.ItemRepository;
import com.sim.jpashop.repository.MemberRepository;
import com.sim.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    //--서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할(도메인 모델 패턴)--//

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
        Order order = Order.createOrder(member, delivery, orderItem); // 파라미터를 여러개 넘기면 여러상품 주문 가능하게 구현할 수 있다.

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
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
