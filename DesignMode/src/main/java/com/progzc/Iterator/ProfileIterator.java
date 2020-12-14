package com.progzc.Iterator;

/**
 * @Description 定义档案接口
 * @Author zhaocho
 * @Date 2020/12/14 18:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface ProfileIterator {
    boolean hasNext();

    Profile getNext();

    void reset();
}
