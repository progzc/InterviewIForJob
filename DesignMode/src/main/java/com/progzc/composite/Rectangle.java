package com.progzc.composite;

import java.awt.*;

/**
 * @Description 三角形
 * @Author zhaochao
 * @Date 2020/12/12 10:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Rectangle extends BaseShape {
    public int width;
    public int height;

    public Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawRect(x, y, getWidth() - 1, getHeight() - 1);
    }
}
