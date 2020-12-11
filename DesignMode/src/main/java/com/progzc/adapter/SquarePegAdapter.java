package com.progzc.adapter;

/**
 * @Description 方钉到圆孔的适配器
 * @Author zhaochao
 * @Date 2020/12/11 21:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class SquarePegAdapter extends RoundPeg {
    private SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    @Override
    public double getRadius() {
        double result;
        // Calculate a minimum circle radius, which can fit this peg.
        result = (Math.sqrt(Math.pow((peg.getWidth() / 2), 2) * 2));
        return result;
    }
}
