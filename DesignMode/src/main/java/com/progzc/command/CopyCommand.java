package com.progzc.command;

/**
 * @Description 将所选文字复制到剪贴板
 * @Author zhaochao
 * @Date 2020/12/14 17:07
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CopyCommand extends Command {

    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        editor.clipboard = editor.textField.getSelectedText();
        return false;
    }
}
