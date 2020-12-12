package com.progzc.composite;

import java.awt.*;

/**
 * @Description 客户端代码
 * @Author zhaochao
 * @Date 2020/12/12 10:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    public static void main(String[] args) {
        ImageEditor editor = new ImageEditor();

        editor.loadShapes(
                new Circle(10, 10, 10, Color.BLUE),

                new CompoundShape(
                    new Circle(110, 110, 50, Color.RED),
                    new Dot(160, 160, Color.RED)
                ),

                new CompoundShape(
                    new Rectangle(250, 250, 100, 100, Color.GREEN),
                    new Dot(240, 240, Color.GREEN),
                    new Dot(240, 360, Color.GREEN),
                    new Dot(360, 360, Color.GREEN),
                    new Dot(360, 240, Color.GREEN)
                )
        );
    }
}
