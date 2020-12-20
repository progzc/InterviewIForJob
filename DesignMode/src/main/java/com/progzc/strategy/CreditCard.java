package com.progzc.strategy;

/**
 * @Description 信用卡类
 * @Author zhaochao
 * @Date 2020/12/20 16:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CreditCard {
    private int amount;
    private String number;
    private String date;
    private String cvv;

    CreditCard(String number, String date, String cvv) {
        this.amount = 100_000;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
