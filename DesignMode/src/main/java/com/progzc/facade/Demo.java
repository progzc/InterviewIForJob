package com.progzc.facade;

import java.io.File;

/**
 * @Description 客户端代码
 * @Author zhaochao
 * @Date 2020/12/12 17:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Demo {
    public static void main(String[] args) {
        VideoConversionFacade converter = new VedioConversion4MpegOgg();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
        // ...
    }
}
