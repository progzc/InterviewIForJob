package com.progzc.facade;

import java.io.File;

/**
 * @Description
 * @Author zhaochao
 * @Date 2020/12/12 17:09
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface VideoConversionFacade {
    File convertVideo(String fileName, String format);
}
