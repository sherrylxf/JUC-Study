package com.sherrylxf.jucstudy.threadMethod;

/**
 * Thread类基本方法演示
 * 包括：start(), run(), currentThread() 等
 */
public class ThreadBasicMethods {

    /**
     * 演示 start() 和 run() 的区别
     * start(): 启动新线程，由JVM调用run()方法
     * run(): 在当前线程中直接执行，不会启动新线程
     */
    public static void demonstrateStartVsRun() {
        System.out.println("\n========== start() vs run() 的区别 ==========");
        
        Thread thread1 = new Thread(() -> {
            System.out.println("线程执行: " + Thread.currentThread().getName());
        });
        
        System.out.println("1. 调用 run() 方法（在当前线程执行）:");
        thread1.run(); // 在主线程中执行
        System.out.println("   当前线程: " + Thread.currentThread().getName());
        
        System.out.println("\n2. 调用 start() 方法（启动新线程）:");
        thread1.start(); // 启动新线程
        System.out.println("   当前线程: " + Thread.currentThread().getName());
        
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 currentThread() 方法
     * 获取当前正在执行的线程对象
     */
    public static void demonstrateCurrentThread() {
        System.out.println("\n========== currentThread() 方法演示 ==========");
        
        System.out.println("主线程信息:");
        Thread mainThread = Thread.currentThread();
        System.out.println("  线程名称: " + mainThread.getName());
        // getId() 在 Java 19+ 中已废弃，使用 threadId() 替代
        System.out.println("  线程ID: " + mainThread.getId());
        System.out.println("  线程优先级: " + mainThread.getPriority());
        System.out.println("  线程状态: " + mainThread.getState());
        System.out.println("  是否存活: " + mainThread.isAlive());
        
        Thread thread = new Thread(() -> {
            System.out.println("\n子线程信息:");
            Thread current = Thread.currentThread();
            System.out.println("  线程名称: " + current.getName());
            System.out.println("  线程ID: " + current.getId());
            System.out.println("  线程优先级: " + current.getPriority());
            System.out.println("  线程状态: " + current.getState());
            System.out.println("  是否存活: " + current.isAlive());
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 getName() 和 setName() 方法
     */
    public static void demonstrateNameMethods() {
        System.out.println("\n========== getName() 和 setName() 方法演示 ==========");
        
        Thread thread1 = new Thread(() -> {
            System.out.println("线程名称: " + Thread.currentThread().getName());
        });
        
        System.out.println("默认线程名称: " + thread1.getName());
        
        thread1.setName("我的自定义线程");
        System.out.println("设置后的线程名称: " + thread1.getName());
        
        thread1.start();
        
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 getPriority() 和 setPriority() 方法
     * 优先级范围：MIN_PRIORITY(1) 到 MAX_PRIORITY(10)，默认 NORM_PRIORITY(5)
     */
    public static void demonstratePriorityMethods() {
        System.out.println("\n========== getPriority() 和 setPriority() 方法演示 ==========");
        
        System.out.println("优先级常量:");
        System.out.println("  MIN_PRIORITY: " + Thread.MIN_PRIORITY);
        System.out.println("  NORM_PRIORITY: " + Thread.NORM_PRIORITY);
        System.out.println("  MAX_PRIORITY: " + Thread.MAX_PRIORITY);
        
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1执行，优先级: " + Thread.currentThread().getPriority());
        });
        
        Thread thread2 = new Thread(() -> {
            System.out.println("线程2执行，优先级: " + Thread.currentThread().getPriority());
        });
        
        Thread thread3 = new Thread(() -> {
            System.out.println("线程3执行，优先级: " + Thread.currentThread().getPriority());
        });
        
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);
        
        System.out.println("\n设置优先级后:");
        System.out.println("  线程1优先级: " + thread1.getPriority());
        System.out.println("  线程2优先级: " + thread2.getPriority());
        System.out.println("  线程3优先级: " + thread3.getPriority());
        
        System.out.println("\n注意：优先级只是建议，不保证执行顺序");
        thread1.start();
        thread2.start();
        thread3.start();
        
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 isAlive() 方法
     * 判断线程是否还活着（已启动且未终止）
     */
    public static void demonstrateIsAlive() {
        System.out.println("\n========== isAlive() 方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        System.out.println("创建线程后: " + thread.isAlive()); // false
        
        thread.start();
        System.out.println("启动线程后: " + thread.isAlive()); // true
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程结束后: " + thread.isAlive()); // false
    }

    /**
     * 综合演示所有基本方法
     */
    public static void demonstrateAllBasicMethods() {
        System.out.println("\n========== Thread类基本方法综合演示 ==========");
        
        demonstrateStartVsRun();
        demonstrateCurrentThread();
        demonstrateNameMethods();
        demonstratePriorityMethods();
        demonstrateIsAlive();
    }
}

