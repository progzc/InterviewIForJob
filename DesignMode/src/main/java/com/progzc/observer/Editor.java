package com.progzc.observer;

import java.io.File;

/**
 * @Description 具体发布者，由其他对象追踪
 * @Author zhaochao
 * @Date 2020/12/20 17:31
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager("open", "save");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}
