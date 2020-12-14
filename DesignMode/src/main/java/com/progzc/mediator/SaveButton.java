package com.progzc.mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description 保存按钮
 * @Author zhaochao
 * @Date 2020/12/14 22:06
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class SaveButton extends JButton implements Component {
    private Mediator mediator;

    public SaveButton() {
        super("Save");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.saveChanges();
    }

    @Override
    public String getName() {
        return "SaveButton";
    }
}
