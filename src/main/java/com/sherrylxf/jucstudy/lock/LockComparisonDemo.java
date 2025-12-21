package com.sherrylxf.jucstudy.lock;

/**
 * 各种锁的对比
 * 面试重点：synchronized vs ReentrantLock、各种锁的适用场景
 */
public class LockComparisonDemo {

    /**
     * 打印synchronized vs ReentrantLock对比
     */
    public static void printSynchronizedVsReentrantLock() {
        System.out.println("\n========== synchronized vs ReentrantLock ==========");
        System.out.println();
        
        System.out.println("┌─────────────────────┬──────────────┬──────────────────┐");
        System.out.println("│       特性           │ synchronized │  ReentrantLock   │");
        System.out.println("├─────────────────────┼──────────────┼──────────────────┤");
        System.out.println("│   锁的释放          │   自动释放    │   手动释放       │");
        System.out.println("│   可中断            │     否        │      是          │");
        System.out.println("│   公平锁            │     否        │      是          │");
        System.out.println("│   超时获取          │     否        │      是          │");
        System.out.println("│   条件变量          │   wait/notify │    Condition     │");
        System.out.println("│   锁绑定多个条件    │     否        │      是          │");
        System.out.println("│   性能              │    JVM优化    │    CAS实现       │");
        System.out.println("│   使用场景          │   简单场景    │   复杂场景       │");
        System.out.println("└─────────────────────┴──────────────┴──────────────────┘");
        System.out.println();
        
        System.out.println("【选择建议】");
        System.out.println("1. 简单场景: 使用synchronized，代码简洁，JVM自动优化");
        System.out.println("2. 需要高级功能: 使用ReentrantLock（可中断、超时、公平锁等）");
        System.out.println("3. 性能要求高: 根据实际情况测试选择");
        System.out.println();
    }

    /**
     * 打印ReadWriteLock vs StampedLock对比
     */
    public static void printReadWriteLockVsStampedLock() {
        System.out.println("\n========== ReadWriteLock vs StampedLock ==========");
        System.out.println();
        
        System.out.println("┌─────────────────────┬──────────────┬──────────────────┐");
        System.out.println("│       特性           │ ReadWriteLock │  StampedLock    │");
        System.out.println("├─────────────────────┼──────────────┼──────────────────┤");
        System.out.println("│   读锁互斥          │     否        │      否          │");
        System.out.println("│   写锁互斥          │     是        │      是          │");
        System.out.println("│   读写互斥          │     是        │      是          │");
        System.out.println("│   乐观读            │     否        │      是          │");
        System.out.println("│   锁降级            │     是        │      是          │");
        System.out.println("│   锁升级            │     否        │      否          │");
        System.out.println("│   重入              │     是        │      否          │");
        System.out.println("│   性能              │     较高      │      更高        │");
        System.out.println("└─────────────────────┴──────────────┴──────────────────┘");
        System.out.println();
        
        System.out.println("【选择建议】");
        System.out.println("1. 读多写少场景: 优先使用StampedLock（乐观读性能更好）");
        System.out.println("2. 需要重入: 使用ReadWriteLock");
        System.out.println("3. 简单场景: 使用ReadWriteLock，API更简单");
        System.out.println();
    }

    /**
     * 打印各种锁的适用场景
     */
    public static void printLockUseCases() {
        System.out.println("\n========== 各种锁的适用场景 ==========");
        System.out.println();
        
        System.out.println("【synchronized】");
        System.out.println("适用场景:");
        System.out.println("  - 简单的同步需求");
        System.out.println("  - 不需要高级功能（可中断、超时等）");
        System.out.println("  - 代码简洁性要求高");
        System.out.println();
        
        System.out.println("【ReentrantLock】");
        System.out.println("适用场景:");
        System.out.println("  - 需要可中断的锁");
        System.out.println("  - 需要超时获取锁");
        System.out.println("  - 需要公平锁");
        System.out.println("  - 需要多个条件变量");
        System.out.println();
        
        System.out.println("【ReadWriteLock】");
        System.out.println("适用场景:");
        System.out.println("  - 读多写少的场景");
        System.out.println("  - 需要锁降级");
        System.out.println("  - 需要重入");
        System.out.println();
        
        System.out.println("【StampedLock】");
        System.out.println("适用场景:");
        System.out.println("  - 读多写少的场景（性能要求高）");
        System.out.println("  - 可以使用乐观读");
        System.out.println("  - 不需要重入");
        System.out.println();
    }

    /**
     * 打印锁的性能对比
     */
    public static void printPerformanceComparison() {
        System.out.println("\n========== 锁的性能对比 ==========");
        System.out.println();
        
        System.out.println("【性能排序（读多写少场景）】");
        System.out.println("1. StampedLock（乐观读）: 性能最高");
        System.out.println("2. ReadWriteLock: 性能较高");
        System.out.println("3. ReentrantLock: 性能中等");
        System.out.println("4. synchronized: 性能中等（JVM优化后）");
        System.out.println();
        
        System.out.println("【性能排序（写多场景）】");
        System.out.println("1. synchronized: 性能最好（JVM优化）");
        System.out.println("2. ReentrantLock: 性能较好");
        System.out.println("3. ReadWriteLock/StampedLock: 性能较低（锁开销大）");
        System.out.println();
        
        System.out.println("【注意】");
        System.out.println("实际性能取决于具体场景，建议进行性能测试");
        System.out.println();
    }

    /**
     * 综合对比所有锁
     */
    public static void printAllComparison() {
        System.out.println("\n========== 锁的综合对比 ==========");
        
        printSynchronizedVsReentrantLock();
        printReadWriteLockVsStampedLock();
        printLockUseCases();
        printPerformanceComparison();
    }
}




