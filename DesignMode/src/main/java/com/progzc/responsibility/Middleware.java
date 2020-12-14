package com.progzc.responsibility;

/**
 * @Description 基础验证接口
 * @Author zhaochao
 * @Date 2020/12/12 23:01
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class Middleware {
    private Middleware next;

    public Middleware linkWith(Middleware next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(String email, String password);

    protected boolean checkNext(String email, String password) {
        if (next == null) {
            return true;
        }
        return next.check(email, password);
    }
}
