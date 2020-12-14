package com.progzc.mediator;

/**
 * @Description 笔记类
 * @Author zhaochao
 * @Date 2020/12/14 22:09
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Note {
    private String name;
    private String text;

    public Note() {
        name = "New note";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return name;
    }
}
