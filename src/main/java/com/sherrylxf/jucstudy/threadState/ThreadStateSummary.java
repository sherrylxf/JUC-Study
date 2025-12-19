package com.sherrylxf.jucstudy.threadState;

/**
 * 线程状态总结类
 * 提供线程状态的详细说明和状态转换图
 */
public class ThreadStateSummary {

    /**
     * 打印线程状态说明
     */
    public static void printStateSummary() {
        System.out.println("\n========== 线程状态详细说明 ==========");
        System.out.println();
        
        System.out.println("1. NEW (新建状态)");
        System.out.println("   - 线程对象被创建，但尚未调用start()方法");
        System.out.println("   - 此时线程还没有被启动");
        System.out.println("   - 示例: Thread t = new Thread(() -> {});");
        System.out.println();
        
        System.out.println("2. RUNNABLE (可运行状态)");
        System.out.println("   - 线程调用了start()方法后进入此状态");
        System.out.println("   - 包括两个子状态：");
        System.out.println("     * READY: 等待CPU时间片");
        System.out.println("     * RUNNING: 正在执行");
        System.out.println("   - 注意：Java中不区分READY和RUNNING，都显示为RUNNABLE");
        System.out.println();
        
        System.out.println("3. BLOCKED (阻塞状态)");
        System.out.println("   - 线程等待获取监视器锁（synchronized）");
        System.out.println("   - 当另一个线程持有锁时，当前线程进入BLOCKED状态");
        System.out.println("   - 一旦获取到锁，线程会从BLOCKED转为RUNNABLE");
        System.out.println("   - 示例: 线程A持有synchronized锁，线程B尝试获取同一把锁");
        System.out.println();
        
        System.out.println("4. WAITING (无限等待状态)");
        System.out.println("   - 线程无限期等待，需要其他线程唤醒");
        System.out.println("   - 进入方式：");
        System.out.println("     * Object.wait() - 需要notify()或notifyAll()唤醒");
        System.out.println("     * Thread.join() - 等待另一个线程完成");
        System.out.println("     * LockSupport.park() - 需要unpark()唤醒");
        System.out.println();
        
        System.out.println("5. TIMED_WAITING (限时等待状态)");
        System.out.println("   - 线程在指定时间内等待");
        System.out.println("   - 进入方式：");
        System.out.println("     * Thread.sleep(time) - 睡眠指定时间");
        System.out.println("     * Object.wait(timeout) - 带超时的等待");
        System.out.println("     * Thread.join(timeout) - 带超时的join");
        System.out.println("     * LockSupport.parkNanos() - 带超时的park");
        System.out.println("     * LockSupport.parkUntil() - 直到指定时间");
        System.out.println();
        
        System.out.println("6. TERMINATED (终止状态)");
        System.out.println("   - 线程执行完毕，已经终止");
        System.out.println("   - 线程的run()方法执行完成或抛出异常");
        System.out.println("   - 终止后的线程不能再次启动");
        System.out.println();
    }

    /**
     * 打印状态转换图
     */
    public static void printStateTransitionDiagram() {
        System.out.println("\n========== 线程状态转换图 ==========");
        System.out.println();
        System.out.println("                    start()");
        System.out.println("    NEW ──────────────────────> RUNNABLE");
        System.out.println("                                    │");
        System.out.println("                                    │");
        System.out.println("                    ┌──────────────┼──────────────┐");
        System.out.println("                    │              │              │");
        System.out.println("                    │              │              │");
        System.out.println("                    ▼              ▼              ▼");
        System.out.println("              BLOCKED        WAITING    TIMED_WAITING");
        System.out.println("                    │              │              │");
        System.out.println("                    │              │              │");
        System.out.println("                    └──────────────┼──────────────┘");
        System.out.println("                                    │");
        System.out.println("                                    │ run()完成");
        System.out.println("                                    ▼");
        System.out.println("                               TERMINATED");
        System.out.println();
        System.out.println("状态转换说明：");
        System.out.println("  NEW -> RUNNABLE: 调用start()方法");
        System.out.println("  RUNNABLE -> BLOCKED: 等待获取synchronized锁");
        System.out.println("  RUNNABLE -> WAITING: 调用wait()、join()或park()");
        System.out.println("  RUNNABLE -> TIMED_WAITING: 调用sleep()、wait(timeout)等");
        System.out.println("  BLOCKED -> RUNNABLE: 获取到锁");
        System.out.println("  WAITING -> RUNNABLE: 被notify()、unpark()或目标线程结束");
        System.out.println("  TIMED_WAITING -> RUNNABLE: 超时或被唤醒");
        System.out.println("  RUNNABLE -> TERMINATED: run()方法执行完成");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printStateSummary();
        printStateTransitionDiagram();
    }
}




