package com.sim.jpashop.service;

import com.sim.jpashop.domain.item.Book;
import com.sim.jpashop.domain.item.Item;
import com.sim.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    //--상품 서비스는 상품 리포지토리에 단순히 위임만 하는 클래스
    // 단순 위임을 위한 Service 클래스이기 때문에 Controller 에서 직접 Repository로 접근해도 무방--//

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 영속성 컨텍스트가 자동 변경
     * */
    @Transactional // spring의 트랜잭션에 의해 커밋 -> JPA flush() : 변경점을 찾아 업데이트
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        //--변경 감지 기능(dirty checking) 사용--//
        Item findItem = itemRepository.findOne(itemId); // 영속상태

        // setter 없이 엔티티 안에서 직접 추적할 수 있는 메서드를 만들도록 설계
//        findItem.change(name, price, stockQuantity);
        findItem.setName(name);                         // 값 세팅
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
//        itemRepository.save(findItem);                // 필요없음
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
