package com.progzc.observer;

import java.io.File;

/**
 * @Description 收到通知后在日志中记录一条消息
 * @Author zhaochao
 * @Date 2020/12/20 17:34
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
