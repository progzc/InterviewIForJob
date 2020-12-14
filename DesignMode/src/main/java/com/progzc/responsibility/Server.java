package com.progzc.responsibility;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 授权目标
 * @Author zhaochao
 * @Date 2020/12/12 23:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Server {
    private Map<String, String> users = new HashMap<>();
    private Middleware middleware;

    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    public boolean logIn(String email, String password) {
        if (middleware.check(email, password)) {
            System.out.println("Authorization have been successful!");
            return true;
        }
        return false;
    }

    public void register(String email, String password) {
        users.put(email, password);
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }
}
