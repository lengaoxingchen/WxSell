package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    //查询所有在架的商品列表
    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);
    //加库存
    void increaseStock(List<CartDto> cartDtoList);
    //减库存
    void decreaseStock(List<CartDto> cartDtoList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
