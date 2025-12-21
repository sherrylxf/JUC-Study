package com.sherrylxf.jucstudy.lock;

/**
 * 锁知识点总结
 * 聚焦面试重点
 */
public class LockSummary {

    /**
     * 打印synchronized知识点
     */
    public static void printSynchronizedSummary() {
        System.out.println("\n========== synchronized知识点 ==========");
        System.out.println();
        
        System.out.println("【使用方式】");
        System.out.println("1. 修饰实例方法: 锁的是当前对象(this)");
        System.out.println("2. 修饰静态方法: 锁的是类对象(Class对象)");
        System.out.println("3. 修饰代码块: 锁的是指定的对象");
        System.out.println();
        
        System.out.println("【特性】");
        System.out.println("1. 可重入: 同一个线程可以多次获取同一把锁");
        System.out.println("2. 不可中断: 获取锁的线程不能被中断");
        System.out.println("3. 非公平锁: 默认是非公平锁");
        System.out.println("4. 自动释放: 方法执行完毕或异常时自动释放");
        System.out.println();
        
        System.out.println("【锁升级过程】");
        System.out.println("无锁 -> 偏向锁 -> 轻量级锁 -> 重量级锁");
        System.out.println();
        System.out.println("1. 偏向锁: 只有一个线程访问时使用，几乎无开销");
        System.out.println("2. 轻量级锁: 多线程竞争但不激烈时使用，使用CAS自旋");
        System.out.println("3. 重量级锁: 竞争激烈时使用，线程阻塞");
        System.out.println();
        
        System.out.println("【实现原理】");
        System.out.println("1. 字节码层面: monitorenter和monitorexit指令");
        System.out.println("2. JVM层面: 对象头中的Mark Word");
        System.out.println("3. 操作系统层面: 重量级锁使用mutex互斥量");
        System.out.println();
    }

    /**
     * 打印ReentrantLock知识点
     */
    public static void printReentrantLockSummary() {
        System.out.println("\n========== ReentrantLock知识点 ==========");
        System.out.println();
        
        System.out.println("【特性】");
        System.out.println("1. 可重入: 同一个线程可以多次获取同一把锁");
        System.out.println("2. 可中断: 可以使用lockInterruptibly()响应中断");
        System.out.println("3. 可超时: 可以使用tryLock(timeout)超时获取");
        System.out.println("4. 公平/非公平: 可以指定公平锁或非公平锁");
        System.out.println("5. 手动释放: 必须在finally中释放锁");
        System.out.println();
        
        System.out.println("【常用方法】");
        System.out.println("1. lock(): 获取锁，阻塞直到获取成功");
        System.out.println("2. unlock(): 释放锁");
        System.out.println("3. tryLock(): 尝试获取锁，立即返回");
        System.out.println("4. tryLock(timeout): 超时获取锁");
        System.out.println("5. lockInterruptibly(): 可中断的获取锁");
        System.out.println("6. newCondition(): 创建条件变量");
        System.out.println();
        
        System.out.println("【实现原理】");
        System.out.println("基于AQS (AbstractQueuedSynchronizer)实现");
        System.out.println("使用CAS和CLH队列");
        System.out.println();
    }

    /**
     * 打印ReadWriteLock知识点
     */
    public static void printReadWriteLockSummary() {
        System.out.println("\n========== ReadWriteLock知识点 ==========");
        System.out.println();
        
        System.out.println("【规则】");
        System.out.println("1. 读锁之间不互斥: 多个线程可以同时持有读锁");
        System.out.println("2. 写锁互斥: 写锁与其他所有锁互斥");
        System.out.println("3. 读写互斥: 读锁和写锁互斥");
        System.out.println();
        
        System.out.println("【适用场景】");
        System.out.println("读多写少的场景，可以大幅提升性能");
        System.out.println();
        
        System.out.println("【锁降级】");
        System.out.println("在持有写锁的情况下，可以获取读锁，然后释放写锁");
        System.out.println("注意: 不支持锁升级（从读锁升级到写锁）");
        System.out.println();
    }

    /**
     * 打印StampedLock知识点
     */
    public static void printStampedLockSummary() {
        System.out.println("\n========== StampedLock知识点 ==========");
        System.out.println();
        
        System.out.println("【三种模式】");
        System.out.println("1. 写锁: 独占锁");
        System.out.println("2. 悲观读锁: 类似ReadWriteLock的读锁");
        System.out.println("3. 乐观读: 无锁的读操作，不阻塞写操作（重点）");
        System.out.println();
        
        System.out.println("【特点】");
        System.out.println("1. 所有获取锁的方法都返回stamp（邮戳）");
        System.out.println("2. 释放锁时需要传入对应的stamp");
        System.out.println("3. 不支持重入");
        System.out.println("4. 乐观读性能最好，但需要验证");
        System.out.println();
        
        System.out.println("【乐观读使用】");
        System.out.println("1. 使用tryOptimisticRead()获取乐观读");
        System.out.println("2. 读取数据");
        System.out.println("3. 使用validate(stamp)验证是否有写操作");
        System.out.println("4. 如果验证失败，升级为悲观读锁");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== 锁相关面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: synchronized和ReentrantLock的区别？】");
        System.out.println("A: 1. synchronized是关键字，ReentrantLock是类");
        System.out.println("   2. synchronized自动释放，ReentrantLock手动释放");
        System.out.println("   3. ReentrantLock支持可中断、超时、公平锁等高级功能");
        System.out.println("   4. synchronized是JVM层面实现，ReentrantLock是JDK层面实现");
        System.out.println();
        
        System.out.println("【Q2: synchronized的锁升级过程？】");
        System.out.println("A: 无锁 -> 偏向锁 -> 轻量级锁 -> 重量级锁");
        System.out.println("   偏向锁: 只有一个线程访问");
        System.out.println("   轻量级锁: 多线程竞争但不激烈（CAS自旋）");
        System.out.println("   重量级锁: 竞争激烈（线程阻塞）");
        System.out.println();
        
        System.out.println("【Q3: 什么是可重入锁？】");
        System.out.println("A: 同一个线程可以多次获取同一把锁，不会死锁。");
        System.out.println("   synchronized和ReentrantLock都是可重入锁。");
        System.out.println();
        
        System.out.println("【Q4: 什么是读写锁？适用场景？】");
        System.out.println("A: 读写锁分离读锁和写锁，读锁之间不互斥，");
        System.out.println("   写锁与其他所有锁互斥。适用于读多写少的场景。");
        System.out.println();
        
        System.out.println("【Q5: StampedLock的乐观读是什么？】");
        System.out.println("A: 乐观读是一种无锁的读操作，不阻塞写操作。");
        System.out.println("   读取数据后需要验证stamp，如果验证失败则升级为悲观读锁。");
        System.out.println("   性能比悲观读锁更好。");
        System.out.println();
        
        System.out.println("【Q6: 如何避免死锁？】");
        System.out.println("A: 1. 避免嵌套锁");
        System.out.println("   2. 按相同顺序获取锁");
        System.out.println("   3. 使用超时锁（tryLock）");
        System.out.println("   4. 使用可中断锁（lockInterruptibly）");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        System.out.println("\n========== 锁知识点完整总结 ==========");
        
        printSynchronizedSummary();
        printReentrantLockSummary();
        printReadWriteLockSummary();
        printStampedLockSummary();
        printInterviewQuestions();
    }
}




