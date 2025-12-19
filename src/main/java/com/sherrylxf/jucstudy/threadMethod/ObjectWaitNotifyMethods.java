package com.sherrylxf.jucstudy.threadMethod;

/**
 * Object类的wait/notify方法演示
 * 包括：wait(), notify(), notifyAll()
 * 注意：这些方法必须在synchronized代码块中调用
 */
public class ObjectWaitNotifyMethods {

    private static final Object lock = new Object();
    private static boolean flag = false;

    /**
     * 演示 wait() 和 notify() 方法
     * wait(): 让当前线程等待，释放锁
     * notify(): 唤醒一个等待的线程
     */
    public static void demonstrateWaitAndNotify() {
        System.out.println("\n========== wait() 和 notify() 方法演示 ==========");
        
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程获取到锁，开始等待");
                try {
                    while (!flag) {
                        System.out.println("等待线程调用wait()，释放锁");
                        lock.wait(); // 释放锁，进入WAITING状态
                        System.out.println("等待线程被唤醒");
                    }
                    System.out.println("等待线程继续执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // 确保waitingThread先进入wait状态
                synchronized (lock) {
                    System.out.println("通知线程获取到锁");
                    flag = true;
                    System.out.println("通知线程调用notify()");
                    lock.notify(); // 唤醒一个等待的线程
                    System.out.println("通知线程释放锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        waitingThread.start();
        notifyingThread.start();
        
        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        flag = false; // 重置标志
    }

    /**
     * 演示 wait(timeout) 方法
     * 等待指定时间，超时后自动唤醒
     */
    public static void demonstrateWaitWithTimeout() {
        System.out.println("\n========== wait(timeout) 方法演示 ==========");
        
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程获取到锁，开始等待（最多2秒）");
                try {
                    long startTime = System.currentTimeMillis();
                    lock.wait(2000); // 等待最多2秒
                    long endTime = System.currentTimeMillis();
                    System.out.println("等待线程被唤醒，等待时间: " + (endTime - startTime) + "ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        waitingThread.start();
        
        try {
            waitingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 notifyAll() 方法
     * 唤醒所有等待的线程
     */
    public static void demonstrateNotifyAll() {
        System.out.println("\n========== notifyAll() 方法演示 ==========");
        
        Thread waitingThread1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程1获取到锁，开始等待");
                try {
                    lock.wait();
                    System.out.println("等待线程1被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread waitingThread2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程2获取到锁，开始等待");
                try {
                    lock.wait();
                    System.out.println("等待线程2被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread waitingThread3 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程3获取到锁，开始等待");
                try {
                    lock.wait();
                    System.out.println("等待线程3被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // 确保所有等待线程都进入wait状态
                synchronized (lock) {
                    System.out.println("通知线程获取到锁，调用notifyAll()");
                    lock.notifyAll(); // 唤醒所有等待的线程
                    System.out.println("通知线程释放锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        waitingThread1.start();
        waitingThread2.start();
        waitingThread3.start();
        notifyingThread.start();
        
        try {
            waitingThread1.join();
            waitingThread2.join();
            waitingThread3.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 wait/notify 的经典生产者消费者模式
     */
    public static void demonstrateProducerConsumer() {
        System.out.println("\n========== wait/notify 生产者消费者模式演示 ==========");
        
        final Object buffer = new Object();
        final int[] data = new int[1]; // 共享数据
        final boolean[] hasData = {false};
        
        // 生产者线程
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (buffer) {
                    while (hasData[0]) {
                        try {
                            System.out.println("生产者：缓冲区已满，等待...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    data[0] = i;
                    hasData[0] = true;
                    System.out.println("生产者：生产数据 " + i);
                    buffer.notify(); // 通知消费者
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // 消费者线程
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (buffer) {
                    while (!hasData[0]) {
                        try {
                            System.out.println("消费者：缓冲区为空，等待...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int value = data[0];
                    hasData[0] = false;
                    System.out.println("消费者：消费数据 " + value);
                    buffer.notify(); // 通知生产者
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示所有wait/notify方法
     */
    public static void demonstrateAllWaitNotifyMethods() {
        System.out.println("\n========== Object wait/notify 方法综合演示 ==========");
        
        demonstrateWaitAndNotify();
        demonstrateWaitWithTimeout();
        demonstrateNotifyAll();
        demonstrateProducerConsumer();
    }
}




