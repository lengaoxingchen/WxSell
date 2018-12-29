package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123");
        Assert.assertEquals("皮蛋粥", productInfo.getProductName());
    }

    @Test
    public void findAll() {
        //pagerequest实现了pageable
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        productInfo.setProductDescription("很好喝的虾");
        productInfo.setProductIcon("http://www.baidu.com");
        productInfo.setCategoryType(4);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
    @Test
    public void onSole(){
        ProductInfo productInfo = productService.onSale("123");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }

    @Test
    public void offSole(){
        ProductInfo productInfo = productService.offSale("123");
        Assert.assertEquals(ProductStatusEnum.Down,productInfo.getProductStatusEnum());
    }
}