package com.progzc.command;

/**
 * @Description 从剪贴板粘贴文字
 * @Author zhaochao
 * @Date 2020/12/14 17:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class PasteCommand extends Command {

    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        if (editor.clipboard == null || editor.clipboard.isEmpty()) {
            return false;
        }

        backup();
        editor.textField.insert(editor.clipboard, editor.textField.getCaretPosition());
        return true;
    }
}
