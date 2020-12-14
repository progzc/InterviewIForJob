package com.progzc.mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * @Description 标题
 * @Author zhaochao
 * @Date 2020/12/14 22:07
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Title extends JTextField implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void processComponentKeyEvent(KeyEvent keyEvent) {
        mediator.markNote();
    }

    @Override
    public String getName() {
        return "Title";
    }
}
