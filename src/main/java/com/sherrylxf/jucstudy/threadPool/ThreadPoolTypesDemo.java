package com.sherrylxf.jucstudy.threadPool;

import java.util.concurrent.*;

/**
 * 各种预定义线程池类型演示
 * Executors 工具类提供的线程池
 */
public class ThreadPoolTypesDemo {

    /**
     * 演示 FixedThreadPool - 固定大小的线程池
     * 特点：核心线程数 = 最大线程数，使用无界队列
     */
    public static void demonstrateFixedThreadPool() {
        System.out.println("\n========== FixedThreadPool (固定大小线程池) ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        System.out.println("特点:");
        System.out.println("  - 核心线程数 = 最大线程数 = 5");
        System.out.println("  - 使用 LinkedBlockingQueue (无界队列)");
        System.out.println("  - 适用于执行长期任务，负载较重的服务器");
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
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
     * 演示 CachedThreadPool - 缓存线程池
     * 特点：核心线程数为0，最大线程数为Integer.MAX_VALUE，使用SynchronousQueue
     */
    public static void demonstrateCachedThreadPool() {
        System.out.println("\n========== CachedThreadPool (缓存线程池) ==========");
        
        ExecutorService executor = Executors.newCachedThreadPool();
        
        System.out.println("特点:");
        System.out.println("  - 核心线程数 = 0");
        System.out.println("  - 最大线程数 = Integer.MAX_VALUE");
        System.out.println("  - 使用 SynchronousQueue (不存储元素)");
        System.out.println("  - 线程空闲60秒后回收");
        System.out.println("  - 适用于执行大量短期异步任务");
        
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
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
     * 演示 SingleThreadExecutor - 单线程线程池
     * 特点：只有一个线程，保证任务顺序执行
     */
    public static void demonstrateSingleThreadExecutor() {
        System.out.println("\n========== SingleThreadExecutor (单线程线程池) ==========");
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        System.out.println("特点:");
        System.out.println("  - 核心线程数 = 最大线程数 = 1");
        System.out.println("  - 使用 LinkedBlockingQueue (无界队列)");
        System.out.println("  - 保证任务按提交顺序执行");
        System.out.println("  - 适用于需要顺序执行的任务");
        
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
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
     * 演示 ScheduledThreadPool - 定时任务线程池
     * 特点：可以执行定时任务和周期性任务
     */
    public static void demonstrateScheduledThreadPool() {
        System.out.println("\n========== ScheduledThreadPool (定时任务线程池) ==========");
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        
        System.out.println("特点:");
        System.out.println("  - 核心线程数 = 3");
        System.out.println("  - 最大线程数 = Integer.MAX_VALUE");
        System.out.println("  - 使用 DelayedWorkQueue");
        System.out.println("  - 适用于执行定时任务和周期性任务");
        
        System.out.println("\n1. 延迟执行任务:");
        executor.schedule(() -> {
            System.out.println("   延迟2秒执行的任务");
        }, 2, TimeUnit.SECONDS);
        
        System.out.println("2. 周期性执行任务 (固定延迟):");
        ScheduledFuture<?> future1 = executor.scheduleWithFixedDelay(() -> {
            System.out.println("   周期性任务执行 (固定延迟3秒)");
        }, 1, 3, TimeUnit.SECONDS);
        
        System.out.println("3. 周期性执行任务 (固定频率):");
        ScheduledFuture<?> future2 = executor.scheduleAtFixedRate(() -> {
            System.out.println("   周期性任务执行 (固定频率2秒)");
        }, 1, 2, TimeUnit.SECONDS);
        
        try {
            Thread.sleep(10000); // 运行10秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 取消周期性任务
        future1.cancel(true);
        future2.cancel(true);
        
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
     * 演示 WorkStealingPool - 工作窃取线程池 (Java 8+)
     * 特点：使用 ForkJoinPool，适合任务可以分解的场景
     */
    public static void demonstrateWorkStealingPool() {
        System.out.println("\n========== WorkStealingPool (工作窃取线程池) ==========");
        
        ExecutorService executor = Executors.newWorkStealingPool();
        
        System.out.println("特点:");
        System.out.println("  - 基于 ForkJoinPool");
        System.out.println("  - 默认线程数 = CPU核心数");
        System.out.println("  - 使用工作窃取算法");
        System.out.println("  - 适用于可以分解的任务");
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
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
     * 综合演示所有线程池类型
     */
    public static void demonstrateAllTypes() {
        System.out.println("\n========== 各种线程池类型综合演示 ==========");
        
        demonstrateFixedThreadPool();
        demonstrateCachedThreadPool();
        demonstrateSingleThreadExecutor();
        demonstrateScheduledThreadPool();
        demonstrateWorkStealingPool();
    }
}

