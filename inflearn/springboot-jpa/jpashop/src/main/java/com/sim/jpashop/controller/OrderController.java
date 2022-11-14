package com.sim.jpashop.controller;

import com.sim.jpashop.domain.Member;
import com.sim.jpashop.domain.Order;
import com.sim.jpashop.domain.OrderSearch;
import com.sim.jpashop.domain.item.Item;
import com.sim.jpashop.service.ItemService;
import com.sim.jpashop.service.MemberService;
import com.sim.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    // 상품 주문 컨트롤러
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // 커멘드성 기능(주문 등)은 Controller 레벨에서 식별자만 넘기고 핵심 비즈니스 서비스에서 엔티티를 찾는 로직으로 짜야함
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    // 주문 목록 검색 컨트롤러
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    // 주문 취소 컨트롤러
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
