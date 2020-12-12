package com.progzc.composite;

import java.awt.*;

/**
 * @Description 圆形
 * @Author zhaochao
 * @Date 2020/12/12 10:03
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Circle extends BaseShape {
    public int radius;

    public Circle(int x, int y, int radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return radius * 2;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawOval(x, y, getWidth() - 1, getHeight() - 1);
    }
}
