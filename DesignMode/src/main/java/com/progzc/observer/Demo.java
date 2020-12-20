package com.progzc.observer;

/**
 * @Description 客户端代码
 * @Author zhaochao
 * @Date 2020/12/20 17:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
