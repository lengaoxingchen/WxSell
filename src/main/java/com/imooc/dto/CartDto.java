package com.imooc.dto;

import lombok.Getter;

/**
 * 购物车
 */
@Getter
public class CartDto {
    //商品Id
    private String productId;
    //购买数量
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
