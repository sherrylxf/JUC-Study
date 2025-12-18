package com.sherrylxf.jucstudy.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池关闭演示
 * shutdown(), shutdownNow(), awaitTermination() 等方法
 */
public class ThreadPoolShutdownDemo {

    /**
     * 演示 shutdown() - 优雅关闭
     * 不再接受新任务，但会等待已提交的任务完成
     */
    public static void demonstrateShutdown() {
        System.out.println("\n========== shutdown() 优雅关闭演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // 提交一些任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 执行中...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("任务 " + taskId + " 被中断");
                }
                System.out.println("任务 " + taskId + " 完成");
            });
        }
        
        System.out.println("调用 shutdown()...");
        executor.shutdown();
        System.out.println("shutdown() 后，线程池状态:");
        System.out.println("  isShutdown: " + executor.isShutdown());
        System.out.println("  isTerminated: " + executor.isTerminated());
        
        // 尝试提交新任务（会被拒绝）
        try {
            executor.submit(() -> System.out.println("新任务"));
        } catch (Exception e) {
            System.out.println("新任务被拒绝: " + e.getClass().getSimpleName());
        }
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("所有任务完成，线程池已终止");
    }

    /**
     * 演示 shutdownNow() - 立即关闭
     * 尝试停止所有正在执行的任务，返回等待执行的任务列表
     */
    public static void demonstrateShutdownNow() {
        System.out.println("\n========== shutdownNow() 立即关闭演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // 提交一些任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 执行中...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("任务 " + taskId + " 被中断");
                    return;
                }
                System.out.println("任务 " + taskId + " 完成");
            });
        }
        
        try {
            Thread.sleep(500); // 让一些任务开始执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("调用 shutdownNow()...");
        var pendingTasks = executor.shutdownNow();
        System.out.println("shutdownNow() 后:");
        System.out.println("  isShutdown: " + executor.isShutdown());
        System.out.println("  isTerminated: " + executor.isTerminated());
        System.out.println("  等待执行的任务数: " + pendingTasks.size());
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程池已终止");
    }

    /**
     * 演示 awaitTermination() - 等待线程池终止
     */
    public static void demonstrateAwaitTermination() {
        System.out.println("\n========== awaitTermination() 等待终止演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // 提交一些任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 执行中...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务 " + taskId + " 完成");
            });
        }
        
        executor.shutdown();
        
        System.out.println("等待线程池终止（最多等待5秒）...");
        try {
            boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
            if (terminated) {
                System.out.println("线程池在5秒内终止");
            } else {
                System.out.println("等待超时，线程池仍在运行");
                System.out.println("强制关闭...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("等待被中断");
            executor.shutdownNow();
        }
    }

    /**
     * 演示正确的关闭流程
     */
    public static void demonstrateProperShutdown() {
        System.out.println("\n========== 正确的线程池关闭流程 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // 提交任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 执行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // 正确的关闭流程
        System.out.println("步骤1: 调用 shutdown() 停止接受新任务");
        executor.shutdown();
        
        try {
            System.out.println("步骤2: 等待已提交的任务完成（最多60秒）");
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("步骤3: 超时后强制关闭");
                executor.shutdownNow();
                
                System.out.println("步骤4: 再次等待（最多60秒）");
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("线程池未能正常关闭");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("等待被中断，强制关闭");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("线程池已正确关闭");
    }

    /**
     * 综合演示所有关闭方法
     */
    public static void demonstrateAllShutdown() {
        System.out.println("\n========== 线程池关闭方法综合演示 ==========");
        
        demonstrateShutdown();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        demonstrateShutdownNow();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        demonstrateAwaitTermination();
        demonstrateProperShutdown();
    }
}

