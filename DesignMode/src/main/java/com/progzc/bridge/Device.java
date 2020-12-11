package com.progzc.bridge;

/**
 * @Description 所有设备的通用接口
 * @Author zhaocho
 * @Date 2020/12/11 23:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int percent);
    int getChannel();
    void setChannel(int channel);
    void printStatus();
}
