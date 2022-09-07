package com.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.constant.ItemSellStatus;
import com.kiwi.shop.dto.CartItemDto;
import com.kiwi.shop.entity.CartItem;
import com.kiwi.shop.entity.Item;
import com.kiwi.shop.entity.Member;
import com.kiwi.shop.repository.CartItemRepository;
import com.kiwi.shop.repository.ItemRepository;
import com.kiwi.shop.repository.MemberRepository;
import com.kiwi.shop.repository.OrderRepository;
import com.kiwi.shop.service.CartService;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional  //테스트 클래스에 트랜잭션널 적용시 테스트 실행 후 롤백이 됨
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Item item = saveItem();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        Assertions.assertEquals(item.getId(),cartItem.getItem().getId());
        Assertions.assertEquals(cartItemDto.getCount(),cartItem.getCount());
    }
}
