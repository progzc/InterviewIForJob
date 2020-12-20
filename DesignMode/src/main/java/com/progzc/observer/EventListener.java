package com.progzc.observer;

import java.io.File;

/**
 * @Description 通用观察者接口
 * @Author zhaocho
 * @Date 2020/12/20 17:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface EventListener {
    void update(String eventType, File file);
}
