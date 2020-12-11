package com.progzc.bridge;

/**
 * @Description 所有远程控制器的通用接口
 * @Author zhaocho
 * @Date 2020/12/11 23:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class Remote {
    protected Device device;

    public Remote(Device device){
        this.device = device;
    }

    abstract void power();
    abstract void volumeDown();
    abstract void volumeUp();
    abstract void channelDown();
    abstract void channelUp();
}
