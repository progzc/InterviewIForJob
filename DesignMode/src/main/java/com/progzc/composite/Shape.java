package com.progzc.composite;

import java.awt.*;

/**
 * @Description 通用形状接口
 * @Author zhaocho
 * @Date 2020/12/12 10:01
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface Shape {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void move(int x, int y);
    boolean isInsideBounds(int x, int y);
    void select();
    void unSelect();
    boolean isSelected();
    void paint(Graphics graphics);
}
