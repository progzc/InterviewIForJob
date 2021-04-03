package com.zcprog.test;

import java.util.concurrent.*;

/**
 * @Description 使用JDK处理响应超时
 * @Author zhaochao
 * @Date 2021/4/3 12:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class KS_ResponseTimeout {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000 * 7);
                return "线程执行完成！";
            }
        };
        try {
            Future<String> future = threadPool.submit(callable);
            // 任务处理超时时间设为1秒
            String ans = future.get(6000 * 1, TimeUnit.MILLISECONDS);
            System.out.println("任务成功返回:" + ans);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦...");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}
