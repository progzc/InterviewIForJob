package com.progzc.proxy;

import java.util.HashMap;

/**
 * @Description 远程服务接口
 * @Author zhaocho
 * @Date 2020/12/12 21:50
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface ThirdPartyYouTubeLib {
    HashMap<String, Video> popularVideos();

    Video getVideo(String videoId);
}
