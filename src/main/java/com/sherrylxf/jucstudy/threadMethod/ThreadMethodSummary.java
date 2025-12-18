package com.sherrylxf.jucstudy.threadMethod;

/**
 * 多线程方法总结类
 * 提供所有常用方法的详细说明
 */
public class ThreadMethodSummary {

    /**
     * 打印Thread类方法总结
     */
    public static void printThreadMethodsSummary() {
        System.out.println("\n========== Thread类常用方法总结 ==========");
        System.out.println();
        
        System.out.println("【线程生命周期方法】");
        System.out.println("1. start()");
        System.out.println("   - 启动线程，使线程进入RUNNABLE状态");
        System.out.println("   - 只能调用一次，多次调用会抛出IllegalThreadStateException");
        System.out.println("   - 由JVM调用run()方法");
        System.out.println();
        
        System.out.println("2. run()");
        System.out.println("   - 线程的执行体，包含线程要执行的代码");
        System.out.println("   - 直接调用run()不会启动新线程，而是在当前线程执行");
        System.out.println();
        
        System.out.println("【线程控制方法】");
        System.out.println("3. sleep(long millis)");
        System.out.println("   - 让当前线程休眠指定毫秒数");
        System.out.println("   - 线程进入TIMED_WAITING状态");
        System.out.println("   - 不释放锁（如果持有锁）");
        System.out.println("   - 可能抛出InterruptedException");
        System.out.println();
        
        System.out.println("4. join()");
        System.out.println("   - 等待调用该方法的线程执行完成");
        System.out.println("   - 当前线程进入WAITING状态");
        System.out.println("   - 可能抛出InterruptedException");
        System.out.println();
        
        System.out.println("5. join(long millis)");
        System.out.println("   - 等待指定时间，超时后继续执行");
        System.out.println("   - 线程进入TIMED_WAITING状态");
        System.out.println();
        
        System.out.println("6. yield()");
        System.out.println("   - 提示调度器当前线程愿意让出CPU");
        System.out.println("   - 只是一个建议，不保证一定会让出");
        System.out.println("   - 线程从RUNNING转为READY状态");
        System.out.println();
        
        System.out.println("【线程中断方法】");
        System.out.println("7. interrupt()");
        System.out.println("   - 中断线程，设置中断标志位");
        System.out.println("   - 如果线程在sleep/wait/join，会抛出InterruptedException");
        System.out.println();
        
        System.out.println("8. isInterrupted()");
        System.out.println("   - 检查线程的中断标志位");
        System.out.println("   - 不会清除中断标志");
        System.out.println();
        
        System.out.println("9. interrupted()");
        System.out.println("   - 静态方法，检查当前线程的中断标志位");
        System.out.println("   - 会清除中断标志");
        System.out.println();
        
        System.out.println("【线程信息方法】");
        System.out.println("10. currentThread()");
        System.out.println("    - 静态方法，获取当前正在执行的线程对象");
        System.out.println();
        
        System.out.println("11. getName() / setName(String name)");
        System.out.println("    - 获取/设置线程名称");
        System.out.println("    - 默认名称格式：Thread-0, Thread-1...");
        System.out.println();
        
        System.out.println("12. threadId()");
        System.out.println("    - 获取线程的唯一标识符（Java 19+推荐使用）");
        System.out.println("    - getId() 在 Java 19+ 中已废弃");
        System.out.println();
        
        System.out.println("13. getPriority() / setPriority(int priority)");
        System.out.println("    - 获取/设置线程优先级");
        System.out.println("    - 范围：MIN_PRIORITY(1) 到 MAX_PRIORITY(10)");
        System.out.println("    - 默认：NORM_PRIORITY(5)");
        System.out.println("    - 优先级只是建议，不保证执行顺序");
        System.out.println();
        
        System.out.println("14. getState()");
        System.out.println("    - 获取线程状态");
        System.out.println("    - 返回Thread.State枚举：NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED");
        System.out.println();
        
        System.out.println("15. isAlive()");
        System.out.println("    - 判断线程是否还活着");
        System.out.println("    - 已启动且未终止返回true");
        System.out.println();
        
        System.out.println("16. isDaemon() / setDaemon(boolean on)");
        System.out.println("    - 判断/设置线程是否为守护线程");
        System.out.println("    - 守护线程在主线程结束时自动终止");
        System.out.println("    - 必须在start()之前设置");
        System.out.println();
    }

    /**
     * 打印Object类wait/notify方法总结
     */
    public static void printObjectMethodsSummary() {
        System.out.println("\n========== Object类wait/notify方法总结 ==========");
        System.out.println();
        
        System.out.println("【重要：必须在synchronized代码块中调用】");
        System.out.println();
        
        System.out.println("1. wait()");
        System.out.println("   - 让当前线程等待，释放锁");
        System.out.println("   - 线程进入WAITING状态");
        System.out.println("   - 必须由其他线程调用notify()或notifyAll()唤醒");
        System.out.println("   - 可能抛出InterruptedException");
        System.out.println();
        
        System.out.println("2. wait(long timeout)");
        System.out.println("   - 等待指定时间，超时后自动唤醒");
        System.out.println("   - 线程进入TIMED_WAITING状态");
        System.out.println();
        
        System.out.println("3. wait(long timeout, int nanos)");
        System.out.println("   - 等待指定时间（毫秒+纳秒）");
        System.out.println();
        
        System.out.println("4. notify()");
        System.out.println("   - 唤醒一个等待的线程");
        System.out.println("   - 随机选择一个等待的线程唤醒");
        System.out.println("   - 被唤醒的线程需要重新获取锁才能继续执行");
        System.out.println();
        
        System.out.println("5. notifyAll()");
        System.out.println("   - 唤醒所有等待的线程");
        System.out.println("   - 所有被唤醒的线程竞争获取锁");
        System.out.println();
        
        System.out.println("【使用注意事项】");
        System.out.println("- wait/notify必须在synchronized代码块中调用");
        System.out.println("- 调用wait()的线程会释放锁");
        System.out.println("- notify()/notifyAll()不会释放锁，需要退出synchronized块");
        System.out.println("- 通常使用while循环检查条件，避免虚假唤醒");
        System.out.println();
    }

    /**
     * 打印方法使用场景总结
     */
    public static void printUsageScenarios() {
        System.out.println("\n========== 方法使用场景总结 ==========");
        System.out.println();
        
        System.out.println("【线程同步场景】");
        System.out.println("- wait()/notify(): 实现线程间的等待和通知机制");
        System.out.println("- synchronized: 实现互斥访问共享资源");
        System.out.println();
        
        System.out.println("【线程控制场景】");
        System.out.println("- sleep(): 让线程暂停执行一段时间");
        System.out.println("- join(): 等待其他线程完成后再继续");
        System.out.println("- yield(): 让出CPU给其他线程（很少使用）");
        System.out.println();
        
        System.out.println("【线程中断场景】");
        System.out.println("- interrupt(): 优雅地停止线程");
        System.out.println("- isInterrupted(): 检查中断标志，决定是否退出循环");
        System.out.println();
        
        System.out.println("【线程信息获取场景】");
        System.out.println("- currentThread(): 获取当前线程对象");
        System.out.println("- getName(): 用于日志记录和调试");
        System.out.println("- getState(): 监控线程状态");
        System.out.println();
        
        System.out.println("【经典设计模式】");
        System.out.println("- 生产者消费者模式: wait()/notify()");
        System.out.println("- 线程池模式: 管理多个线程的生命周期");
        System.out.println("- 主从模式: join()等待子线程完成");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printThreadMethodsSummary();
        printObjectMethodsSummary();
        printUsageScenarios();
    }
}

