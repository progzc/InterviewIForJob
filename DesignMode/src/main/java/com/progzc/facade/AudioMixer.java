package com.progzc.facade;

import java.io.File;

/**
 * @Description
 * @Author zhaochao
 * @Date 2020/12/12 17:09
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class AudioMixer {
    public File fix(VideoFile result){
        System.out.println("AudioMixer: fixing audio...");
        return new File("tmp");
    }
}
