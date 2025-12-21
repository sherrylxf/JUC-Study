package com.sherrylxf.jucstudy.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal演示
 * 面试重点：ThreadLocal的作用、原理、内存泄漏问题
 */
public class ThreadLocalDemo {

    // 普通变量 - 线程不安全
    private static int normalValue = 0;
    
    // ThreadLocal变量 - 线程安全
    private static final ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();
    
    // ThreadLocal with initial value
    private static final ThreadLocal<Integer> threadLocalWithInit = ThreadLocal.withInitial(() -> 100);
    
    // ThreadLocal for SimpleDateFormat (解决线程安全问题)
    private static final ThreadLocal<SimpleDateFormat> dateFormat = 
        ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * 演示普通变量的线程安全问题
     */
    public static void demonstrateNormalVariableProblem() {
        System.out.println("\n========== 普通变量的线程安全问题 ==========");
        
        normalValue = 0;
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                normalValue++;
                System.out.println("线程1: normalValue = " + normalValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                normalValue++;
                System.out.println("线程2: normalValue = " + normalValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终结果: " + normalValue);
        System.out.println("问题: 两个线程共享同一个变量，导致数据混乱");
    }

    /**
     * 演示ThreadLocal的基本使用
     */
    public static void demonstrateBasicUsage() {
        System.out.println("\n========== ThreadLocal基本使用 ==========");
        
        Thread thread1 = new Thread(() -> {
            threadLocalValue.set(1);
            System.out.println("线程1设置值: " + threadLocalValue.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1读取值: " + threadLocalValue.get());
        });
        
        Thread thread2 = new Thread(() -> {
            threadLocalValue.set(2);
            System.out.println("线程2设置值: " + threadLocalValue.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2读取值: " + threadLocalValue.get());
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 每个线程都有自己独立的ThreadLocal值");
    }

    /**
     * 演示ThreadLocal with initial value
     */
    public static void demonstrateWithInitial() {
        System.out.println("\n========== ThreadLocal withInitial() ==========");
        
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1初始值: " + threadLocalWithInit.get());
            threadLocalWithInit.set(threadLocalWithInit.get() + 10);
            System.out.println("线程1修改后: " + threadLocalWithInit.get());
        });
        
        Thread thread2 = new Thread(() -> {
            System.out.println("线程2初始值: " + threadLocalWithInit.get());
            threadLocalWithInit.set(threadLocalWithInit.get() + 20);
            System.out.println("线程2修改后: " + threadLocalWithInit.get());
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 每个线程都有独立的初始值");
    }

    /**
     * 演示ThreadLocal解决SimpleDateFormat线程安全问题
     */
    public static void demonstrateSimpleDateFormat() {
        System.out.println("\n========== ThreadLocal解决SimpleDateFormat线程安全问题 ==========");
        
        System.out.println("问题: SimpleDateFormat不是线程安全的");
        System.out.println("解决: 使用ThreadLocal为每个线程创建独立的SimpleDateFormat实例");
        System.out.println();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                Date date = new Date(System.currentTimeMillis() + taskId * 1000);
                String formatted = dateFormat.get().format(date);
                System.out.println("线程 " + Thread.currentThread().getName() + 
                    " 格式化日期: " + formatted);
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
        
        System.out.println("✓ 使用ThreadLocal，每个线程都有独立的SimpleDateFormat实例");
    }

    /**
     * 演示ThreadLocal的内存泄漏问题
     */
    public static void demonstrateMemoryLeak() {
        System.out.println("\n========== ThreadLocal内存泄漏问题 ==========");
        
        System.out.println("【内存泄漏原因】");
        System.out.println("ThreadLocalMap的Entry使用弱引用(WeakReference)存储key，");
        System.out.println("但value是强引用。如果ThreadLocal对象被回收，");
        System.out.println("但线程仍然存在，Entry中的value无法被回收，导致内存泄漏。");
        System.out.println();
        
        System.out.println("【解决方案】");
        System.out.println("1. 使用完ThreadLocal后，调用remove()方法清除");
        System.out.println("2. 使用try-finally确保remove()被调用");
        System.out.println();
        
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        
        Thread thread = new Thread(() -> {
            try {
                threadLocal.set("重要数据");
                System.out.println("设置值: " + threadLocal.get());
                // 使用数据...
            } finally {
                // 必须调用remove()，防止内存泄漏
                threadLocal.remove();
                System.out.println("已调用remove()，清除ThreadLocal");
            }
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示InheritableThreadLocal（子线程继承父线程的值）
     */
    public static void demonstrateInheritableThreadLocal() {
        System.out.println("\n========== InheritableThreadLocal演示 ==========");
        
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        
        threadLocal.set("父线程ThreadLocal值");
        inheritableThreadLocal.set("父线程InheritableThreadLocal值");
        
        Thread childThread = new Thread(() -> {
            System.out.println("子线程读取ThreadLocal: " + threadLocal.get());
            System.out.println("子线程读取InheritableThreadLocal: " + inheritableThreadLocal.get());
            System.out.println("✓ InheritableThreadLocal可以让子线程继承父线程的值");
        });
        
        childThread.start();
        
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示ThreadLocal
     */
    public static void demonstrateAll() {
        System.out.println("\n========== ThreadLocal综合演示 ==========");
        
        demonstrateNormalVariableProblem();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        demonstrateBasicUsage();
        demonstrateWithInitial();
        demonstrateSimpleDateFormat();
        demonstrateMemoryLeak();
        demonstrateInheritableThreadLocal();
    }
}






