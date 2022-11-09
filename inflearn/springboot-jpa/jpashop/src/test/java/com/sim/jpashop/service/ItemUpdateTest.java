package com.sim.jpashop.service;

import com.sim.jpashop.domain.item.Book;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    //--JPA - 준영속 엔티티--//

    @Autowired EntityManager em;

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class, 1L);

        // TX(트랜잭션)
        book.setName("adfasd");

        // JPA 변경감지 == dirty checking
        // TX commit
    }
    
}
