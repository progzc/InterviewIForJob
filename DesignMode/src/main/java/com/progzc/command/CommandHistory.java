package com.progzc.command;

import java.util.Stack;

/**
 * @Description 命令历史
 * @Author zhaochao
 * @Date 2020/12/14 17:06
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CommandHistory {
    private Stack<Command> history = new Stack<>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
