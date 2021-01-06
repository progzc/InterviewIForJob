package org.fenixsoft.jvm;

/**
 * @Description 线程创建过多导致的异常
 * @Author zhaochao
 * @Date 2021/1/6 11:20
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            System.out.println("***********" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }
}
