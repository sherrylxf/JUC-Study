package com.sherrylxf.jucstudy.aqs;

/**
 * AQS (AbstractQueuedSynchronizer) 知识点总结
 * 面试重点：AQS原理、自定义同步器、基于AQS的常见类
 */
public class AQSSummary {

    /**
     * 打印AQS基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== AQS基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是AQS】");
        System.out.println("AQS (AbstractQueuedSynchronizer) 是Java并发包中构建锁和其他同步器的基础框架");
        System.out.println("它是JUC包的核心，提供了实现阻塞锁和同步器的框架");
        System.out.println();
        
        System.out.println("【核心组件】");
        System.out.println("1. state变量: 同步状态，用volatile修饰，支持原子操作");
        System.out.println("2. CLH队列: 双向链表，用于存储等待获取锁的线程");
        System.out.println("3. CAS操作: 使用Unsafe类进行CAS操作，保证原子性");
        System.out.println();
        
        System.out.println("【基于AQS实现的类】");
        System.out.println("1. ReentrantLock: 可重入锁");
        System.out.println("2. ReentrantReadWriteLock: 读写锁");
        System.out.println("3. CountDownLatch: 倒计时器");
        System.out.println("4. Semaphore: 信号量");
        System.out.println("5. CyclicBarrier: 循环屏障");
        System.out.println();
    }

    /**
     * 打印AQS核心原理
     */
    public static void printCorePrinciples() {
        System.out.println("\n========== AQS核心原理 ==========");
        System.out.println();
        
        System.out.println("【1. state变量】");
        System.out.println("   - 用volatile int修饰，表示同步状态");
        System.out.println("   - 不同实现有不同的含义：");
        System.out.println("     * ReentrantLock: 表示锁被获取的次数");
        System.out.println("     * CountDownLatch: 表示倒计时剩余次数");
        System.out.println("     * Semaphore: 表示可用许可证数量");
        System.out.println();
        
        System.out.println("【2. CLH队列（双向链表）】");
        System.out.println("   - 队列中的节点代表等待获取锁的线程");
        System.out.println("   - 新节点采用CAS方式添加到队尾");
        System.out.println("   - 头节点表示当前持有锁的线程");
        System.out.println("   - 节点状态：CANCELLED、SIGNAL、CONDITION、PROPAGATE");
        System.out.println();
        
        System.out.println("【3. CAS操作】");
        System.out.println("   - compareAndSetState(): 原子地更新state");
        System.out.println("   - compareAndSetHead(): 原子地更新头节点");
        System.out.println("   - compareAndSetTail(): 原子地更新尾节点");
        System.out.println("   - compareAndSetWaitStatus(): 原子地更新节点状态");
        System.out.println();
        
        System.out.println("【4. 两种模式】");
        System.out.println("   - 独占模式（EXCLUSIVE）: 同一时刻只有一个线程能获取锁");
        System.out.println("     例如：ReentrantLock");
        System.out.println("   - 共享模式（SHARED）: 同一时刻可以有多个线程获取锁");
        System.out.println("     例如：CountDownLatch、Semaphore");
        System.out.println();
    }

    /**
     * 打印AQS关键方法
     */
    public static void printKeyMethods() {
        System.out.println("\n========== AQS关键方法 ==========");
        System.out.println();
        
        System.out.println("【需要子类实现的方法】");
        System.out.println("1. tryAcquire(int arg): 独占模式获取锁");
        System.out.println("2. tryRelease(int arg): 独占模式释放锁");
        System.out.println("3. tryAcquireShared(int arg): 共享模式获取锁");
        System.out.println("4. tryReleaseShared(int arg): 共享模式释放锁");
        System.out.println("5. isHeldExclusively(): 判断当前线程是否独占资源");
        System.out.println();
        
        System.out.println("【AQS提供的模板方法】");
        System.out.println("1. acquire(int arg): 独占模式获取锁（不响应中断）");
        System.out.println("2. acquireInterruptibly(int arg): 独占模式获取锁（响应中断）");
        System.out.println("3. tryAcquireNanos(int arg, long nanos): 独占模式超时获取锁");
        System.out.println("4. release(int arg): 独占模式释放锁");
        System.out.println("5. acquireShared(int arg): 共享模式获取锁");
        System.out.println("6. releaseShared(int arg): 共享模式释放锁");
        System.out.println();
        
        System.out.println("【acquire方法的执行流程】");
        System.out.println("1. 调用tryAcquire尝试获取锁");
        System.out.println("2. 如果失败，将当前线程包装成节点加入队列");
        System.out.println("3. 在队列中自旋，检查前驱节点是否为头节点");
        System.out.println("4. 如果是头节点，再次尝试获取锁");
        System.out.println("5. 获取成功则设置为头节点，失败则阻塞（LockSupport.park）");
        System.out.println();
    }

    /**
     * 打印自定义同步器要点
     */
    public static void printCustomSyncPoints() {
        System.out.println("\n========== 自定义同步器要点 ==========");
        System.out.println();
        
        System.out.println("【实现步骤】");
        System.out.println("1. 继承AbstractQueuedSynchronizer类");
        System.out.println("2. 根据需求选择独占模式或共享模式（或两者都支持）");
        System.out.println("3. 实现tryAcquire/tryRelease或tryAcquireShared/tryReleaseShared");
        System.out.println("4. 使用getState()、setState()、compareAndSetState()操作state");
        System.out.println("5. 包装成Lock或同步器类对外提供API");
        System.out.println();
        
        System.out.println("【state变量的设计】");
        System.out.println("- 需要定义state的含义（如：锁的持有次数、剩余资源数等）");
        System.out.println("- 考虑可重入性（同一线程多次获取）");
        System.out.println("- 考虑公平性（是否需要按顺序获取）");
        System.out.println();
        
        System.out.println("【常见实现模式】");
        System.out.println("1. 互斥锁: state=0表示未锁定，state>0表示被锁定");
        System.out.println("2. 信号量: state表示可用许可证数量");
        System.out.println("3. 倒计时器: state表示剩余计数");
        System.out.println();
    }

    /**
     * 打印基于AQS的常见类
     */
    public static void printAQSBasedClasses() {
        System.out.println("\n========== 基于AQS的常见类 ==========");
        System.out.println();
        
        System.out.println("【1. ReentrantLock】");
        System.out.println("   - 模式: 独占模式");
        System.out.println("   - state含义: 锁被获取的次数（支持可重入）");
        System.out.println("   - 支持公平锁和非公平锁");
        System.out.println("   - 内部类Sync继承AQS，FairSync和NonfairSync继承Sync");
        System.out.println();
        
        System.out.println("【2. CountDownLatch】");
        System.out.println("   - 模式: 共享模式");
        System.out.println("   - state含义: 倒计时剩余次数");
        System.out.println("   - await()方法：当state>0时阻塞，state=0时唤醒所有等待线程");
        System.out.println("   - countDown()方法：state减1，如果减到0则唤醒等待线程");
        System.out.println();
        
        System.out.println("【3. Semaphore】");
        System.out.println("   - 模式: 共享模式");
        System.out.println("   - state含义: 可用许可证数量");
        System.out.println("   - acquire()方法：获取许可证，state减1");
        System.out.println("   - release()方法：释放许可证，state加1");
        System.out.println("   - 支持公平和非公平两种模式");
        System.out.println();
        
        System.out.println("【4. ReentrantReadWriteLock】");
        System.out.println("   - 模式: 同时使用独占模式和共享模式");
        System.out.println("   - state含义: 高16位表示读锁持有数，低16位表示写锁持有数");
        System.out.println("   - 读锁使用共享模式，写锁使用独占模式");
        System.out.println();
        
        System.out.println("【5. CyclicBarrier】");
        System.out.println("   - 模式: 基于ReentrantLock和Condition实现");
        System.out.println("   - 功能: 让一组线程到达一个屏障（同步点）时被阻塞，直到最后一个线程到达屏障时");
        System.out.println("     屏障才会开门，所有被屏障拦截的线程才会继续运行");
        System.out.println("   - 特点: 可以重用（cyclic），而CountDownLatch不能重用");
        System.out.println("   - 构造函数可以传入一个Runnable，作为屏障打开时执行的任务");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== AQS相关面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是AQS？】");
        System.out.println("A: AQS (AbstractQueuedSynchronizer) 是Java并发包中构建锁和其他同步器的");
        System.out.println("   基础框架。它使用state变量、CLH队列和CAS操作实现了同步机制。");
        System.out.println("   许多JUC类都是基于AQS实现的，如ReentrantLock、CountDownLatch等。");
        System.out.println();
        
        System.out.println("【Q2: AQS的核心组件有哪些？】");
        System.out.println("A: 1. state变量：用volatile int修饰，表示同步状态");
        System.out.println("   2. CLH队列：双向链表，存储等待获取锁的线程");
        System.out.println("   3. CAS操作：用于原子地更新state和队列节点");
        System.out.println();
        
        System.out.println("【Q3: AQS的两种模式是什么？】");
        System.out.println("A: 1. 独占模式（EXCLUSIVE）：同一时刻只有一个线程能获取锁");
        System.out.println("      例如：ReentrantLock");
        System.out.println("   2. 共享模式（SHARED）：同一时刻可以有多个线程获取锁");
        System.out.println("      例如：CountDownLatch、Semaphore");
        System.out.println();
        
        System.out.println("【Q4: AQS的acquire方法执行流程？】");
        System.out.println("A: 1. 调用tryAcquire尝试获取锁");
        System.out.println("   2. 如果失败，将当前线程包装成节点加入CLH队列");
        System.out.println("   3. 在队列中自旋，检查前驱节点是否为头节点");
        System.out.println("   4. 如果是头节点，再次尝试获取锁");
        System.out.println("   5. 获取成功则设置为头节点，失败则阻塞（LockSupport.park）");
        System.out.println();
        
        System.out.println("【Q5: 如何基于AQS实现自定义同步器？】");
        System.out.println("A: 1. 继承AbstractQueuedSynchronizer类");
        System.out.println("   2. 根据需求选择独占模式或共享模式");
        System.out.println("   3. 实现tryAcquire/tryRelease或tryAcquireShared/tryReleaseShared");
        System.out.println("   4. 使用getState()、setState()、compareAndSetState()操作state");
        System.out.println("   5. 包装成Lock或同步器类对外提供API");
        System.out.println();
        
        System.out.println("【Q6: ReentrantLock是如何基于AQS实现的？】");
        System.out.println("A: ReentrantLock内部有一个Sync类继承自AQS，FairSync和NonfairSync");
        System.out.println("   继承Sync实现公平锁和非公平锁。state表示锁被获取的次数，");
        System.out.println("   支持可重入。当state=0时表示锁未被持有，state>0表示被持有");
        System.out.println("   且可重入次数为state的值。");
        System.out.println();
        
        System.out.println("【Q7: CountDownLatch是如何基于AQS实现的？】");
        System.out.println("A: CountDownLatch使用共享模式。state表示倒计时剩余次数。");
        System.out.println("   await()方法：当state>0时，线程会被阻塞并加入队列；");
        System.out.println("   countDown()方法：state减1，如果减到0则唤醒所有等待线程。");
        System.out.println();
        
        System.out.println("【Q8: CyclicBarrier和CountDownLatch的区别？】");
        System.out.println("A: 1. CountDownLatch是一次性的，计数减到0后不能再使用；");
        System.out.println("      CyclicBarrier可以重用，可以多次等待");
        System.out.println("   2. CountDownLatch由外部线程控制计数，一个线程可以多次countDown；");
        System.out.println("      CyclicBarrier由参与的线程控制，每个线程只能await一次");
        System.out.println("   3. CountDownLatch适用于一个或多个线程等待一组线程完成；");
        System.out.println("      CyclicBarrier适用于一组线程互相等待，达到同步点");
        System.out.println("   4. CyclicBarrier可以设置屏障打开时执行的任务，CountDownLatch不可以");
        System.out.println();
        
        System.out.println("【Q9: 公平锁和非公平锁的区别？】");
        System.out.println("A: 1. 公平锁：按照线程等待的顺序获取锁，新来的线程会加入队列");
        System.out.println("   2. 非公平锁：新来的线程可能直接尝试获取锁，可能插队");
        System.out.println("   3. 非公平锁性能更好，但可能导致线程饥饿");
        System.out.println("   4. ReentrantLock和Semaphore都支持公平/非公平模式");
        System.out.println("   5. ReentrantLock默认是非公平锁，可以通过构造函数指定");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        System.out.println("\n========== AQS知识点完整总结 ==========");
        
        printBasicConcepts();
        printCorePrinciples();
        printKeyMethods();
        printCustomSyncPoints();
        printAQSBasedClasses();
        printInterviewQuestions();
    }
}

