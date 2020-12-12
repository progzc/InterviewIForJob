package com.progzc.proxy;

/**
 * @Description 视频文件
 * @Author zhaochao
 * @Date 2020/12/12 21:51
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Video {
    public String id;
    public String title;
    public String data;

    Video(String id, String title) {
        this.id = id;
        this.title = title;
        this.data = "Random video.";
    }
}
