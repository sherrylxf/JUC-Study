package com.sherrylxf.jucstudy.threadState;

/**
 * 线程状态演示类
 * Java线程有6种状态：NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED
 */
public class ThreadStateDemo {

    /**
     * 演示线程的6种状态
     */
    public static void demonstrateThreadStates() {
        System.out.println("========== 线程状态演示 ==========");
        
        // 1. NEW - 新建状态：线程被创建但尚未启动
        Thread thread1 = new Thread(() -> {
            System.out.println("线程执行中...");
        });
        System.out.println("1. NEW状态: " + thread1.getState());
        
        // 2. RUNNABLE - 可运行状态：线程正在JVM中执行，可能在等待CPU时间片
        thread1.start();
        System.out.println("2. RUNNABLE状态: " + thread1.getState());
        
        // 等待线程执行完成
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 3. TERMINATED - 终止状态：线程执行完毕
        System.out.println("3. TERMINATED状态: " + thread1.getState());
        
        System.out.println("\n========== 状态说明 ==========");
        System.out.println("NEW: 线程被创建但尚未调用start()方法");
        System.out.println("RUNNABLE: 线程正在运行或等待CPU时间片（包括READY和RUNNING）");
        System.out.println("BLOCKED: 线程被阻塞，等待获取监视器锁");
        System.out.println("WAITING: 线程无限期等待，需要其他线程唤醒");
        System.out.println("TIMED_WAITING: 线程在指定时间内等待");
        System.out.println("TERMINATED: 线程执行完毕，已经终止");
    }
}




