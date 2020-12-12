package com.progzc.decorator;

/**
 * @Description 抽象基础装饰
 * @Author zhaochao
 * @Date 2020/12/12 11:01
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class DataSourceDecorator implements DataSource {
    private DataSource wrappee;

    DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}
