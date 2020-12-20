package com.progzc.state;

/**
 * @Description 客户端代码
 * @Author zhaochao
 * @Date 2020/12/20 20:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
