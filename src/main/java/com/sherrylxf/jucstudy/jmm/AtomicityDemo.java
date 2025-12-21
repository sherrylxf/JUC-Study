package com.sherrylxf.jucstudy.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性问题演示
 * 面试重点：i++不是原子操作
 */
public class AtomicityDemo {

    private static int count = 0;
    private static volatile int volatileCount = 0; // volatile不能保证原子性
    private static final AtomicInteger atomicCount = new AtomicInteger(0);
    private static int synchronizedCount = 0;
    private static final Object lock = new Object();

    /**
     * 演示i++不是原子操作
     * i++包含三个步骤：read -> modify -> write
     */
    public static void demonstrateNonAtomicOperation() {
        System.out.println("\n========== i++不是原子操作演示 ==========");
        
        System.out.println("i++实际上包含三个步骤：");
        System.out.println("1. read:   读取i的值到工作内存");
        System.out.println("2. modify: 在工作内存中执行+1操作");
        System.out.println("3. write:  将结果写回主内存");
        System.out.println();
        System.out.println("多线程环境下，这三个步骤可能被其他线程打断！");
        System.out.println();
        
        // 创建10个线程，每个线程执行1000次count++
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    count++; // 非原子操作
                }
            });
        }
        
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        
        System.out.println("预期结果: 10000 (10个线程 × 1000次)");
        System.out.println("实际结果: " + count);
        System.out.println("丢失了 " + (10000 - count) + " 次更新");
        System.out.println("原因: i++不是原子操作，多线程竞争导致数据丢失");
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 演示volatile不能保证原子性
     * 重要：volatile只能保证可见性，不能保证原子性
     */
    public static void demonstrateVolatileNotAtomic() {
        System.out.println("\n========== volatile不能保证原子性 ==========");
        
        System.out.println("⚠️ 重要：volatile只能保证可见性，不能保证原子性！");
        System.out.println();
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    volatileCount++; // 即使使用volatile，仍然不是原子操作
                }
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("使用volatile的count: " + volatileCount);
        System.out.println("预期结果: 10000");
        System.out.println("实际结果: " + volatileCount);
        System.out.println("结论: volatile不能解决原子性问题！");
    }

    /**
     * 演示使用synchronized保证原子性
     */
    public static void demonstrateSynchronizedSolution() {
        System.out.println("\n========== synchronized保证原子性 ==========");
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (lock) {
                        synchronizedCount++; // 使用synchronized保证原子性
                    }
                }
            });
        }
        
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        
        System.out.println("使用synchronized的count: " + synchronizedCount);
        System.out.println("预期结果: 10000");
        System.out.println("实际结果: " + synchronizedCount);
        System.out.println("✓ synchronized保证了原子性");
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }

    /**
     * 演示使用原子类保证原子性
     */
    public static void demonstrateAtomicSolution() {
        System.out.println("\n========== 原子类保证原子性 ==========");
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCount.incrementAndGet(); // 原子操作
                }
            });
        }
        
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        
        System.out.println("使用AtomicInteger的count: " + atomicCount.get());
        System.out.println("预期结果: 10000");
        System.out.println("实际结果: " + atomicCount.get());
        System.out.println("✓ 原子类保证了原子性");
        System.out.println("耗时: " + (endTime - startTime) + "ms");
        System.out.println("优势: 性能比synchronized更好（使用CAS实现）");
    }

    /**
     * 演示原子操作的对比
     */
    public static void demonstrateComparison() {
        System.out.println("\n========== 原子性解决方案对比 ==========");
        System.out.println();
        System.out.println("【解决方案对比】");
        System.out.println("1. synchronized");
        System.out.println("   - 优点: 简单易用，功能强大（保证可见性+原子性+有序性）");
        System.out.println("   - 缺点: 性能开销较大，可能造成线程阻塞");
        System.out.println();
        System.out.println("2. 原子类 (AtomicInteger等)");
        System.out.println("   - 优点: 性能好，使用CAS实现，无锁编程");
        System.out.println("   - 缺点: 只能保证单个操作的原子性，复杂操作仍需synchronized");
        System.out.println();
        System.out.println("3. volatile");
        System.out.println("   - 优点: 性能好，保证可见性和有序性");
        System.out.println("   - 缺点: 不能保证原子性！");
        System.out.println();
    }

    /**
     * 综合演示原子性问题
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 原子性问题综合演示 ==========");
        
        demonstrateNonAtomicOperation();
        
        // 重置
        volatileCount = 0;
        synchronizedCount = 0;
        atomicCount.set(0);
        
        demonstrateVolatileNotAtomic();
        demonstrateSynchronizedSolution();
        demonstrateAtomicSolution();
        demonstrateComparison();
    }
}






