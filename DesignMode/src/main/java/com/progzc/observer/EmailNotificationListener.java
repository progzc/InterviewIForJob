package com.progzc.observer;

import java.io.File;

/**
 * @Description 收到通知后发送邮件
 * @Author zhaochao
 * @Date 2020/12/20 17:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class EmailNotificationListener implements EventListener {
    private String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}
