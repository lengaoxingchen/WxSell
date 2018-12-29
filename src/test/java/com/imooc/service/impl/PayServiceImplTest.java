package com.imooc.service.impl;

import com.imooc.dto.OrderDto;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    @Autowired
    PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDto orderDto = orderService.findOne("1545817257561168460");
        payService.create(orderDto);
    }

    @Test
    public void refund() {
        OrderDto orderDto = orderService.findOne("1545817257561168460");
        payService.refund(orderDto);
    }
}