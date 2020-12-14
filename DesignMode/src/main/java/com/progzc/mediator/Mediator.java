package com.progzc.mediator;

import javax.swing.*;

/**
 * @Description 定义通用的中介者接口
 * @Author zhaochao
 * @Date 2020/12/14 22:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface Mediator {
    void addNewNote(Note note);

    void deleteNote();

    void getInfoFromList(Note note);

    void saveChanges();

    void markNote();

    void clear();

    void sendToFilter(ListModel listModel);

    void setElementsList(ListModel list);

    void registerComponent(Component component);

    void hideElements(boolean flag);

    void createGUI();
}
