package com.sherrylxf.jucstudy.threadState;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程状态转换演示
 * 展示各种状态之间的转换场景
 */
public class ThreadStateTransition {

    private static final Object lock = new Object();
    private static final Lock reentrantLock = new ReentrantLock();

    /**
     * 演示 NEW -> RUNNABLE -> TERMINATED 的转换
     */
    public static void demonstrateNewToRunnableToTerminated() {
        System.out.println("\n========== 状态转换1: NEW -> RUNNABLE -> TERMINATED ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("线程开始执行");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行完成");
        });
        
        System.out.println("创建线程后: " + thread.getState()); // NEW
        
        thread.start();
        System.out.println("调用start()后: " + thread.getState()); // RUNNABLE
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程结束后: " + thread.getState()); // TERMINATED
    }

    /**
     * 演示 RUNNABLE -> TIMED_WAITING -> RUNNABLE 的转换
     * 通过sleep()方法实现
     */
    public static void demonstrateTimedWaiting() {
        System.out.println("\n========== 状态转换2: RUNNABLE -> TIMED_WAITING -> RUNNABLE ==========");
        
        Thread thread = new Thread(() -> {
            try {
                System.out.println("线程进入TIMED_WAITING状态（sleep）");
                Thread.sleep(2000);
                System.out.println("线程从TIMED_WAITING状态恢复");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        thread.start();
        
        // 等待一小段时间，确保线程进入sleep状态
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程sleep时: " + thread.getState()); // TIMED_WAITING
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 RUNNABLE -> WAITING -> RUNNABLE 的转换
     * 通过wait()和notify()实现
     */
    public static void demonstrateWaiting() {
        System.out.println("\n========== 状态转换3: RUNNABLE -> WAITING -> RUNNABLE ==========");
        
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("线程进入WAITING状态（wait）");
                    lock.wait(); // 进入WAITING状态
                    System.out.println("线程被唤醒，从WAITING状态恢复");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(500); // 确保waitingThread先进入wait状态
                synchronized (lock) {
                    System.out.println("唤醒等待的线程");
                    lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        waitingThread.start();
        notifyingThread.start();
        
        // 等待一小段时间，确保waitingThread进入wait状态
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程wait时: " + waitingThread.getState()); // WAITING
        
        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 RUNNABLE -> BLOCKED -> RUNNABLE 的转换
     * 通过synchronized锁实现
     */
    public static void demonstrateBlocked() {
        System.out.println("\n========== 状态转换4: RUNNABLE -> BLOCKED -> RUNNABLE ==========");
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程1获取到锁，开始执行");
                try {
                    Thread.sleep(2000); // 持有锁2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1释放锁");
            }
        });
        
        Thread thread2 = new Thread(() -> {
            System.out.println("线程2尝试获取锁");
            synchronized (lock) {
                System.out.println("线程2获取到锁，开始执行");
            }
        });
        
        thread1.start();
        
        // 等待一小段时间，确保thread1先获取到锁
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        thread2.start();
        
        // 等待一小段时间，确保thread2尝试获取锁但被阻塞
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程2等待锁时: " + thread2.getState()); // BLOCKED
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示使用ReentrantLock时的TIMED_WAITING状态
     * 通过lock.tryLock(timeout)实现
     */
    public static void demonstrateReentrantLockTimedWaiting() {
        System.out.println("\n========== 状态转换5: RUNNABLE -> TIMED_WAITING (ReentrantLock) ==========");
        
        Thread thread1 = new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("线程1获取到ReentrantLock，开始执行");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("线程1释放ReentrantLock");
            }
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("线程2尝试获取ReentrantLock（带超时）");
                boolean acquired = reentrantLock.tryLock(3, TimeUnit.SECONDS);
                if (acquired) {
                    System.out.println("线程2成功获取到ReentrantLock");
                    reentrantLock.unlock();
                } else {
                    System.out.println("线程2超时未获取到锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        thread1.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        thread2.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程2在tryLock等待时: " + thread2.getState()); // TIMED_WAITING
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示所有状态转换
     */
    public static void demonstrateAllTransitions() {
        System.out.println("\n========== 综合演示：所有线程状态转换 ==========");
        
        demonstrateNewToRunnableToTerminated();
        demonstrateTimedWaiting();
        demonstrateWaiting();
        demonstrateBlocked();
        demonstrateReentrantLockTimedWaiting();
    }
}








