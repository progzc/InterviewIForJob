package com.progzc.responsibility;

/**
 * @Description 检查用户信息
 * @Author zhaochao
 * @Date 2020/12/12 23:03
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class RoleCheckMiddleware extends Middleware {
    @Override
    public boolean check(String email, String password) {
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(email, password);
    }
}
