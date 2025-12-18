package com.sherrylxf.jucstudy.threadPool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolExecutor 详细配置演示
 * 核心参数：corePoolSize, maximumPoolSize, keepAliveTime, workQueue, threadFactory, handler
 */
public class ThreadPoolExecutorDemo {

    /**
     * 自定义线程工厂
     */
    static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + "-thread-" + threadNumber.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 自定义拒绝策略
     */
    static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("任务被拒绝: " + r.toString());
            System.out.println("  当前线程池大小: " + executor.getPoolSize());
            System.out.println("  活跃线程数: " + executor.getActiveCount());
            System.out.println("  队列大小: " + executor.getQueue().size());
        }
    }

    /**
     * 演示 ThreadPoolExecutor 的核心参数
     */
    public static void demonstrateCoreParameters() {
        System.out.println("\n========== ThreadPoolExecutor 核心参数演示 ==========");
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                      // corePoolSize: 核心线程数
            5,                      // maximumPoolSize: 最大线程数
            60L,                    // keepAliveTime: 非核心线程空闲存活时间
            TimeUnit.SECONDS,       // unit: 时间单位
            new LinkedBlockingQueue<>(10), // workQueue: 工作队列
            new CustomThreadFactory("MyPool"), // threadFactory: 线程工厂
            new CustomRejectedExecutionHandler() // handler: 拒绝策略
        );
        
        System.out.println("线程池配置:");
        System.out.println("  核心线程数: " + executor.getCorePoolSize());
        System.out.println("  最大线程数: " + executor.getMaximumPoolSize());
        System.out.println("  空闲线程存活时间: " + executor.getKeepAliveTime(TimeUnit.SECONDS) + "秒");
        System.out.println("  队列类型: " + executor.getQueue().getClass().getSimpleName());
        System.out.println("  队列容量: " + ((LinkedBlockingQueue<?>) executor.getQueue()).remainingCapacity());
        
        // 提交任务
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(1000);
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
     * 演示线程池的执行流程
     * 1. 如果当前线程数 < corePoolSize，创建新线程执行任务
     * 2. 如果当前线程数 >= corePoolSize，将任务放入队列
     * 3. 如果队列满了且当前线程数 < maximumPoolSize，创建新线程执行任务
     * 4. 如果队列满了且当前线程数 >= maximumPoolSize，执行拒绝策略
     */
    public static void demonstrateExecutionFlow() {
        System.out.println("\n========== 线程池执行流程演示 ==========");
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,  // 核心线程数
            4,  // 最大线程数
            5L, // 非核心线程空闲存活时间: 5秒
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3), // 队列容量为3
            new CustomThreadFactory("FlowPool"),
            new ThreadPoolExecutor.CallerRunsPolicy() // 调用者运行策略
        );
        
        System.out.println("配置: 核心线程2, 最大线程4, 队列容量3");
        System.out.println("提交15个任务，观察执行流程:\n");
        
        for (int i = 1; i <= 15; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 执行中... (线程: " + 
                    Thread.currentThread().getName() + 
                    ", 当前线程数: " + executor.getPoolSize() + 
                    ", 队列大小: " + executor.getQueue().size() + ")");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            
            try {
                Thread.sleep(100); // 间隔提交，便于观察
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
     * 演示不同的工作队列
     */
    public static void demonstrateWorkQueues() {
        System.out.println("\n========== 不同工作队列演示 ==========");
        
        // 1. LinkedBlockingQueue - 无界队列
        System.out.println("1. LinkedBlockingQueue (无界队列):");
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(
            2, 5, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), // 无界队列
            new CustomThreadFactory("Queue1"),
            new ThreadPoolExecutor.AbortPolicy()
        );
        System.out.println("   队列容量: 无界");
        executor1.shutdown();
        
        // 2. ArrayBlockingQueue - 有界队列
        System.out.println("\n2. ArrayBlockingQueue (有界队列):");
        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(
            2, 5, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), // 有界队列，容量10
            new CustomThreadFactory("Queue2"),
            new ThreadPoolExecutor.AbortPolicy()
        );
        System.out.println("   队列容量: 10");
        executor2.shutdown();
        
        // 3. SynchronousQueue - 同步队列
        System.out.println("\n3. SynchronousQueue (同步队列):");
        ThreadPoolExecutor executor3 = new ThreadPoolExecutor(
            2, 5, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), // 同步队列，不存储元素
            new CustomThreadFactory("Queue3"),
            new ThreadPoolExecutor.AbortPolicy()
        );
        System.out.println("   队列容量: 0 (不存储元素，直接提交)");
        executor3.shutdown();
        
        // 4. PriorityBlockingQueue - 优先级队列
        System.out.println("\n4. PriorityBlockingQueue (优先级队列):");
        ThreadPoolExecutor executor4 = new ThreadPoolExecutor(
            2, 5, 60L, TimeUnit.SECONDS,
            new PriorityBlockingQueue<>(), // 优先级队列
            new CustomThreadFactory("Queue4"),
            new ThreadPoolExecutor.AbortPolicy()
        );
        System.out.println("   队列类型: 按优先级排序");
        executor4.shutdown();
    }

    /**
     * 演示不同的拒绝策略
     */
    public static void demonstrateRejectionPolicies() {
        System.out.println("\n========== 不同拒绝策略演示 ==========");
        
        // 1. AbortPolicy - 直接抛出异常
        System.out.println("1. AbortPolicy (直接抛出异常):");
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(
            2, 3, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2),
            new CustomThreadFactory("Policy1"),
            new ThreadPoolExecutor.AbortPolicy()
        );
        
        try {
            for (int i = 0; i < 10; i++) {
                final int taskId = i;
                executor1.submit(() -> {
                    System.out.println("   任务 " + taskId + " 执行");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (RejectedExecutionException e) {
            System.out.println("   捕获到拒绝异常: " + e.getMessage());
        }
        executor1.shutdown();
        
        // 2. CallerRunsPolicy - 调用者运行
        System.out.println("\n2. CallerRunsPolicy (调用者运行):");
        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(
            2, 3, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2),
            new CustomThreadFactory("Policy2"),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor2.submit(() -> {
                System.out.println("   任务 " + taskId + " 由 " + Thread.currentThread().getName() + " 执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor2.shutdown();
        
        // 3. DiscardPolicy - 直接丢弃
        System.out.println("\n3. DiscardPolicy (直接丢弃):");
        ThreadPoolExecutor executor3 = new ThreadPoolExecutor(
            2, 3, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2),
            new CustomThreadFactory("Policy3"),
            new ThreadPoolExecutor.DiscardPolicy()
        );
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor3.submit(() -> {
                System.out.println("   任务 " + taskId + " 执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor3.shutdown();
        
        // 4. DiscardOldestPolicy - 丢弃最老的任务
        System.out.println("\n4. DiscardOldestPolicy (丢弃最老的任务):");
        ThreadPoolExecutor executor4 = new ThreadPoolExecutor(
            2, 3, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2),
            new CustomThreadFactory("Policy4"),
            new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor4.submit(() -> {
                System.out.println("   任务 " + taskId + " 执行");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor4.shutdown();
        
        try {
            Thread.sleep(2000); // 等待任务执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示
     */
    public static void demonstrateAll() {
        System.out.println("\n========== ThreadPoolExecutor 详细配置综合演示 ==========");
        
        demonstrateCoreParameters();
        demonstrateExecutionFlow();
        demonstrateWorkQueues();
        demonstrateRejectionPolicies();
    }
}

