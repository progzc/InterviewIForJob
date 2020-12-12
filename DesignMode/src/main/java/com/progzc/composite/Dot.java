package com.progzc.composite;

import java.awt.*;

/**
 * @Description 点
 * @Author zhaochao
 * @Date 2020/12/12 10:02
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Dot extends BaseShape {
    private final int DOT_SIZE = 3;

    public Dot(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public int getWidth() {
        return DOT_SIZE;
    }

    @Override
    public int getHeight() {
        return DOT_SIZE;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.fillRect(x - 1, y - 1, getWidth(), getHeight());
    }
}
