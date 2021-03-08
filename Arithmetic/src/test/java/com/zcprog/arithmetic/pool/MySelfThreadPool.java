package com.zcprog.arithmetic.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description 手写线程池
 * @Author zhaochao
 * @Date 2021/3/8 20:24
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class MySelfThreadPool {
    // 默认线程池中的线程的数量
    private static final int WORK_NUM = 5;
    // 默认处理任务的数量
    private static final int TASK_NUM = 100;
    private final Set<WorkThread> workThreads;//保存线程的集合
    private final BlockingQueue<Runnable> taskQueue;//阻塞有序队列存放任务
    private int workNum;//线程数量
    private int taskNum;//任务数量

    public MySelfThreadPool() {
        this(WORK_NUM, TASK_NUM);
    }

    public MySelfThreadPool(int workNum, int taskNum) {
        if (workNum <= 0) workNum = WORK_NUM;
        if (taskNum <= 0) taskNum = TASK_NUM;
        taskQueue = new ArrayBlockingQueue<>(taskNum);
        this.workNum = workNum;
        this.taskNum = taskNum;
        workThreads = new HashSet<>();
        // 启动一定数量的线程数，从队列中获取任务处理
        for (int i = 0; i < workNum; i++) {
            WorkThread workThread = new WorkThread("thead_" + i);
            workThread.start();
            workThreads.add(workThread);
        }
    }

    /**
     * 线程池执行任务的方法，其实就是往BlockingQueue中添加元素
     * @param task
     */
    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void destroy() {
        System.out.println("ready close thread pool...");
        if (workThreads == null || workThreads.isEmpty()) return;
        for (WorkThread workThread : workThreads) {
            workThread.stopWork();
            workThread = null; // help gc
        }
        workThreads.clear();
    }

    /**
     * 线程池中的工作线程，直接从BlockingQueue中获取任务
     * 然后执行任务而已
     * blockQueue为阻塞队列
     */
    private class WorkThread extends Thread {
        public WorkThread(String name) {
            super();
            setName(name);
        }

        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    Runnable runnable = taskQueue.take();//获取任务
                    if (runnable != null) {
                        System.out.println(getName() + " ready execute:" + runnable.toString());
                        runnable.run();//执行任务
                    }
                    runnable = null;//help gc
                } catch (Exception e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
        }

        public void stopWork() {
            interrupt();
        }
    }
}
