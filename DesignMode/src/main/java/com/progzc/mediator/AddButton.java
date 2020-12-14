package com.progzc.mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description 增加按钮
 * @Author zhaochao
 * @Date 2020/12/14 22:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class AddButton extends JButton implements Component {
    private Mediator mediator;

    public AddButton() {
        super("Add");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.addNewNote(new Note());
    }

    @Override
    public String getName() {
        return "AddButton";
    }
}
