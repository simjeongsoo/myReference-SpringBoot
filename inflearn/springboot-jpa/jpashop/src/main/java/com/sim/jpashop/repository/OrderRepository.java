package com.sim.jpashop.repository;

import com.sim.jpashop.domain.Order;
import com.sim.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //--검색--//
    public List<Order> findAll(OrderSearch orderSearch) {

        //==데이터가 없을 경우 동적 쿼리, 실무x [ver1.JPQL로 처리]==//
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

        /*
        // 데이터가 전부 있을때의 검색 쿼리 로직
        return em.createQuery("select o from Order o join o.member m" +
                        " where o.orderStatus = :orderstatus" +
                        " and m.name like :name", Order.class)
                .setParameter("orderstatus", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(100) // 페이징
                .setMaxResults(1000) // 최대 1000건
                .getResultList();
                */

    }
