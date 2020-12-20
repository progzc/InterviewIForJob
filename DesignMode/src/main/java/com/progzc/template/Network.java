package com.progzc.template;

/**
 * @Description 基础社交网络类
 * @Author zhaochao
 * @Date 2020/12/20 20:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public abstract class Network {
    String userName;
    String password;

    Network() {
    }

    public boolean post(String message) {
        // Authenticate before posting. Every network uses a different
        // authentication method.
        if (logIn(this.userName, this.password)) {
            // Send the post data.
            boolean result = sendData(message.getBytes());
            logOut();
            return result;
        }
        return false;
    }

    abstract boolean logIn(String userName, String password);

    abstract boolean sendData(byte[] data);

    abstract void logOut();
}
