package com.progzc.bridge;

/**
 * @Description 高级远程控制器
 * @Author zhaochao
 * @Date 2020/12/11 23:05
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class AdvancedRemote extends BasicRemote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
