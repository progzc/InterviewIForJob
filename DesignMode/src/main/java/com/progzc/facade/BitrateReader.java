package com.progzc.facade;

/**
 * @Description
 * @Author zhaochao
 * @Date 2020/12/12 17:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class BitrateReader {
    public static VideoFile read(VideoFile file, Codec codec) {
        System.out.println("BitrateReader: reading file...");
        return file;
    }

    public static VideoFile convert(VideoFile buffer, Codec codec) {
        System.out.println("BitrateReader: writing file...");
        return buffer;
    }
}
