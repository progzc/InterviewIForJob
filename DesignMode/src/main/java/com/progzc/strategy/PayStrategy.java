package com.progzc.strategy;

/**
 * @Description 通用的支付方法接口
 * @Author zhaochao
 * @Date 2020/12/20 16:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface PayStrategy {
    boolean pay(int paymentAmount);

    void collectPaymentDetails();
}
