package com.sherrylxf.jucstudy.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池基本概念演示
 * 线程池的优势：降低资源消耗、提高响应速度、提高线程的可管理性
 */
public class ThreadPoolBasicDemo {

    /**
     * 演示为什么需要线程池
     * 传统方式：每次创建新线程，开销大
     */
    public static void demonstrateWithoutThreadPool() {
        System.out.println("\n========== 传统方式：不使用线程池 ==========");
        
        long startTime = System.currentTimeMillis();
        
        // 创建100个线程
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            new Thread(() -> {
                System.out.println("任务 " + taskId + " 由线程 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(100); // 模拟任务执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("创建100个线程耗时: " + (endTime - startTime) + "ms");
        System.out.println("问题：频繁创建和销毁线程，资源消耗大");
    }

    /**
     * 演示使用线程池的优势
     */
    public static void demonstrateWithThreadPool() {
        System.out.println("\n========== 使用线程池 ==========");
        
        // 创建固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        long startTime = System.currentTimeMillis();
        
        // 提交100个任务
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由线程 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(100); // 模拟任务执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("提交100个任务耗时: " + (endTime - startTime) + "ms");
        System.out.println("优势：线程复用，资源消耗小");
        
        // 关闭线程池
        executor.shutdown();
    }

    /**
     * 演示线程池的基本使用
     */
    public static void demonstrateBasicUsage() {
        System.out.println("\n========== 线程池基本使用 ==========");
        
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // 提交任务的方式
        System.out.println("1. 使用 submit() 提交 Runnable 任务:");
        executor.submit(() -> {
            System.out.println("   Runnable任务执行");
        });
        
        System.out.println("2. 使用 submit() 提交 Callable 任务:");
        executor.submit(() -> {
            System.out.println("   Callable任务执行");
            return "任务结果";
        });
        
        System.out.println("3. 使用 execute() 提交 Runnable 任务:");
        executor.execute(() -> {
            System.out.println("   execute()提交的任务执行");
        });
        
        // 关闭线程池
        executor.shutdown();
        
        try {
            // 等待所有任务完成
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("所有任务执行完成");
    }

    /**
     * 演示线程池的状态
     */
    public static void demonstrateThreadPoolState() {
        System.out.println("\n========== 线程池状态演示 ==========");
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        
        System.out.println("线程池创建后:");
        System.out.println("  核心线程数: " + executor.getCorePoolSize());
        System.out.println("  最大线程数: " + executor.getMaximumPoolSize());
        System.out.println("  当前线程数: " + executor.getPoolSize());
        System.out.println("  活跃线程数: " + executor.getActiveCount());
        System.out.println("  队列中的任务数: " + executor.getQueue().size());
        System.out.println("  已完成任务数: " + executor.getCompletedTaskCount());
        
        // 提交一些任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("任务 " + taskId + " 完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n提交任务后:");
        System.out.println("  当前线程数: " + executor.getPoolSize());
        System.out.println("  活跃线程数: " + executor.getActiveCount());
        System.out.println("  队列中的任务数: " + executor.getQueue().size());
        
        executor.shutdown();
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示基本概念
     */
    public static void demonstrateAllBasic() {
        System.out.println("\n========== 线程池基本概念综合演示 ==========");
        
        demonstrateWithoutThreadPool();
        
        try {
            Thread.sleep(2000); // 等待上面的线程执行完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        demonstrateWithThreadPool();
        demonstrateBasicUsage();
        demonstrateThreadPoolState();
    }
}








