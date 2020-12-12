package com.progzc.flyweight;

import java.awt.*;

/**
 * @Description 每棵树的独特状态
 * @Author zhaochao
 * @Date 2020/12/12 20:37
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Tree {
    private int x;
    private int y;
    private TreeType type;

    public Tree(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}
