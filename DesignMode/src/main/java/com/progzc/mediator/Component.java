package com.progzc.mediator;

/**
 * @Description 组件接口
 * @Author zhaochao
 * @Date 2020/12/14 21:03
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface Component {
    void setMediator(Mediator mediator);

    String getName();
}
