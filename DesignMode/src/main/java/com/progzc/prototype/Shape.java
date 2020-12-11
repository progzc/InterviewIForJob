package com.progzc.prototype;

import java.util.Objects;

/**
 * @Description 通用形状接口
 * @Author zhaochao
 * @Date 2020/12/11 17:21
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class Shape {
    public int x;
    public int y;
    public String color;
    public Object object;

    public Shape() {
    }

    public Shape(Shape target) {
        if (target != null) {
            this.x = target.x;
            this.y = target.y;
            this.color = target.color;
            this.object = target.object;
        }
    }

    @Override
    public abstract Shape clone();

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Shape)) {
            return false;
        }
        Shape shape2 = (Shape) object2;
        return shape2.x == x && shape2.y == y && Objects.equals(shape2.color, color) && shape2.object == object;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "x=" + x +
                ", y=" + y +
                ", color='" + color + '\'' +
                ", object=" + object +
                '}';
    }
}
