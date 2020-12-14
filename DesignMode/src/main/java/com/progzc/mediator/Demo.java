package com.progzc.mediator;

import javax.swing.*;

/**
 * @Description 客户端代码
 * @Author zhaochao
 * @Date 2020/12/14 22:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    public static void main(String[] args) {
        Mediator mediator = new Editor();

        mediator.registerComponent(new Title());
        mediator.registerComponent(new TextBox());
        mediator.registerComponent(new AddButton());
        mediator.registerComponent(new DeleteButton());
        mediator.registerComponent(new SaveButton());
        mediator.registerComponent(new List(new DefaultListModel()));
        mediator.registerComponent(new Filter());

        mediator.createGUI();
    }
}
