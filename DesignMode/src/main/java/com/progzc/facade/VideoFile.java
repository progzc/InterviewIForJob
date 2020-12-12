package com.progzc.facade;

/**
 * @Description 视频文件
 * @Author zhaochao
 * @Date 2020/12/12 17:05
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class VideoFile {
    private String name;
    private String codecType;

    public VideoFile(String name) {
        this.name = name;
        this.codecType = name.substring(name.indexOf(".") + 1);
    }

    public String getCodecType() {
        return codecType;
    }

    public String getName() {
        return name;
    }
}
