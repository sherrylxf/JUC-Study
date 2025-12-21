package com.sherrylxf.jucstudy.lock;

/**
 * 锁综合演示类
 * 整合所有锁相关的演示和总结
 */
public class LockDemo {

    /**
     * 打印锁知识点总结
     */
    public static void printSummary() {
        System.out.println("\n========== 第一部分：锁知识点总结 ==========");
        LockSummary.printFullSummary();
    }

    /**
     * 演示synchronized
     */
    public static void demonstrateSynchronized() {
        System.out.println("\n========== 第二部分：synchronized演示 ==========");
        SynchronizedDemo.demonstrateAll();
    }

    /**
     * 演示ReentrantLock
     */
    public static void demonstrateReentrantLock() {
        System.out.println("\n========== 第三部分：ReentrantLock演示 ==========");
        ReentrantLockDemo.demonstrateAll();
    }

    /**
     * 演示ReadWriteLock
     */
    public static void demonstrateReadWriteLock() {
        System.out.println("\n========== 第四部分：ReadWriteLock演示 ==========");
        ReadWriteLockDemo.demonstrateAll();
    }

    /**
     * 演示StampedLock
     */
    public static void demonstrateStampedLock() {
        System.out.println("\n========== 第五部分：StampedLock演示 ==========");
        StampedLockDemo.demonstrateAll();
    }

    /**
     * 演示锁的对比
     */
    public static void demonstrateComparison() {
        System.out.println("\n========== 第六部分：各种锁的对比 ==========");
        LockComparisonDemo.printAllComparison();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 锁完整学习演示 ==========");
        
        // 先看理论总结
        printSummary();
        
        // 再看实际演示
        demonstrateSynchronized();
        demonstrateReentrantLock();
        demonstrateReadWriteLock();
        demonstrateStampedLock();
        
        // 最后看对比
        demonstrateComparison();
        
        System.out.println("\n========== 锁学习演示完成 ==========");
    }
}




