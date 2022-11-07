package com.sim.jpashop.service;

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

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
