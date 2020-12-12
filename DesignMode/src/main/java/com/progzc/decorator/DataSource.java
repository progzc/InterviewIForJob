package com.progzc.decorator;

/**
 * @Description 定义了读取和写入操作的通用数据接口
 * @Author zhaocho
 * @Date 2020/12/12 11:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface DataSource {
    void writeData(String data);
    String readData();
}
