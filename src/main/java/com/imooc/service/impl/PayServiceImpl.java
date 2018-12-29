package com.imooc.service.impl;

import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getOrderId());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] 发起支付 request = {}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付] 发起支付 response = {}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名

        //2.支付的状态

        //3.支付的金额校验

        //4.支付人(下单人 == 支付人)

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付]异步通知, payResponse={}", payResponse);
        //查询订单
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if (orderDto == null) {
            log.info("[微信支付]异步通知, 订单不存在 orderId = {}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //判断金额是否一致(0.10    0.1)
        // 微信的金额是double类型的.我们的金额是BigDecimal类型的.无法直接比较.使用compareTo()
        //compareTo() 两个结果不可以直接比较.需要判断两个比较的结果是否等于0
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDto.getOrderAmount().doubleValue())) {
            log.info("[微信支付]异步通知,订单金额不一致 orderId = {}, 微信通知金额 = {} 系统金额= {}", payResponse.getOrderId(), payResponse.getOrderAmount(), orderDto.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改订单状态
        orderService.paid(orderDto);
        //修改订单的支付状态
        return payResponse;
    }

    /**
     * 退款
     *
     * @param orderDto
     */
    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request = {}", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] response = {}", JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
