package com.progzc.command;

/**
 * @Description 抽象基础命令
 * @Author zhaochao
 * @Date 2020/12/14 17:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class Command {
    public Editor editor;
    private String backup;

    Command(Editor editor) {
        this.editor = editor;
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract boolean execute();
}
