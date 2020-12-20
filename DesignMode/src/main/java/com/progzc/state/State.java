package com.progzc.state;

/**
 * @Description 通用状态接口
 * @Author zhaochao
 * @Date 2020/12/20 20:52
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class State {
    Player player;

    /**
     * Context passes itself through the state constructor. This may help a
     * state to fetch some useful context data if needed.
     */
    State(Player player) {
        this.player = player;
    }

    public abstract String onLock();

    public abstract String onPlay();

    public abstract String onNext();

    public abstract String onPrevious();
}
