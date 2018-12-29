package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    private final String buyerOpenid = "wxm123456";
    private final String ORDER_ID = "1545817552889429173";
    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("北京市朝阳区朝阳东里1号院");
        orderDto.setBuyerName("小明");
        orderDto.setBuyerPhone("12365856965");
        orderDto.setBuyerOpenid(buyerOpenid);

        //购物陈
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("123");
        orderDetail1.setProductQuantity(2);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("1234");
        orderDetail2.setProductQuantity(5);

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        orderDto.setOrderDetailList(orderDetailList);
        OrderDto result = orderService.create(orderDto);
        log.info("创建订单 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDto result = orderService.findOne(ORDER_ID);
        log.info("查询单个订单 result = {}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderDto> orderDtoPage = orderService.findList(buyerOpenid, pageRequest);
        log.info("订单列表查询结果: result = {}", orderDtoPage);
        Assert.assertNotEquals(0, orderDtoPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
    @Test
    public void list() {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderDto> orderDtoPage = orderService.findList(pageRequest);
        log.info("订单列表查询结果: result = {}", orderDtoPage);
        Assert.assertNotEquals(0, orderDtoPage.getTotalElements());
    }

}