package com.progzc.mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description 删除按钮
 * @Author zhaochao
 * @Date 2020/12/14 22:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class DeleteButton extends JButton implements Component {
    private Mediator mediator;

    public DeleteButton() {
        super("Del");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.deleteNote();
    }

    @Override
    public String getName() {
        return "DelButton";
    }
}
