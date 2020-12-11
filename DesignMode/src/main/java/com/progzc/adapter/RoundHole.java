package com.progzc.adapter;

/**
 * @Description 圆孔
 * @Author zhaochao
 * @Date 2020/12/11 21:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class RoundHole {
    private double radius;

    public RoundHole(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public boolean fits(RoundPeg peg) {
        boolean result;
        result = (this.getRadius() >= peg.getRadius());
        return result;
    }
}
