package com.progzc.responsibility;

/**
 * @Description 检查用户登录信息
 * @Author zhaochao
 * @Date 2020/12/12 23:03
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class UserExistsMiddleware extends Middleware {
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    @Override
    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}
