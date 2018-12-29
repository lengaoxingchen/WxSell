package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    OrderDetailRepository repository;
    @Test
    public void saveTest(){
        OrderDetail orderDetail =new OrderDetail();
        orderDetail.setDetailId("100002");
        orderDetail.setOrderId("123457");
        orderDetail.setProductId("1236");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductName("不知道什么鬼");
        orderDetail.setProductPrice(new BigDecimal(500));
        orderDetail.setProductQuantity(5);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("123457");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}