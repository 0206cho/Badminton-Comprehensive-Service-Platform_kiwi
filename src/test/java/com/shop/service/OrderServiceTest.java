package com.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.constant.ItemSellStatus;
import com.kiwi.constant.OrderStatus;
import com.kiwi.shop.dto.OrderDto;
import com.kiwi.shop.entity.Item;
import com.kiwi.shop.entity.Member;
import com.kiwi.shop.entity.Order;
import com.kiwi.shop.entity.OrderItem;
import com.kiwi.shop.repository.ItemRepository;
import com.kiwi.shop.repository.MemberRepository;
import com.kiwi.shop.repository.OrderRepository;
import com.kiwi.shop.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@Transactional  //테스트 클래스에 트랜잭션널 적용시 테스트 실행 후 롤백이 됨
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

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
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto,member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();

        Assertions.assertEquals(totalPrice,order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());
        Long orderId = orderService.order(orderDto,member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        Assertions.assertEquals(OrderStatus.CANCEL,order.getOrderStatus());
        Assertions.assertEquals(100, item.getStockNumber());
    }
}
