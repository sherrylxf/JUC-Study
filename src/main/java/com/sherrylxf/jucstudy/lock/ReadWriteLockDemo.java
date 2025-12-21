package com.sherrylxf.jucstudy.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock读写锁演示
 * 面试重点：读写锁的原理、适用场景
 */
public class ReadWriteLockDemo {

    private static int data = 0;
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock readLock = 
        ((ReentrantReadWriteLock) readWriteLock).readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = 
        ((ReentrantReadWriteLock) readWriteLock).writeLock();

    /**
     * 演示读写锁的基本使用
     */
    public static void demonstrateBasicUsage() {
        System.out.println("\n========== ReadWriteLock基本使用 ==========");
        
        System.out.println("【读写锁规则】");
        System.out.println("1. 读锁之间不互斥: 多个线程可以同时持有读锁");
        System.out.println("2. 写锁互斥: 写锁与其他所有锁（读锁和写锁）互斥");
        System.out.println("3. 读写互斥: 读锁和写锁互斥");
        System.out.println();
        
        // 多个读线程
        for (int i = 0; i < 5; i++) {
            final int readerId = i;
            new Thread(() -> {
                readLock.lock();
                try {
                    System.out.println("读线程" + readerId + "读取数据: " + data);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }).start();
        }
        
        // 写线程
        new Thread(() -> {
            writeLock.lock();
            try {
                System.out.println("写线程修改数据");
                data = 100;
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }).start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示读写锁的性能优势
     */
    public static void demonstratePerformance() {
        System.out.println("\n========== ReadWriteLock性能优势 ==========");
        
        System.out.println("【适用场景】");
        System.out.println("读多写少的场景，使用读写锁可以大幅提升性能");
        System.out.println();
        
        System.out.println("【性能对比】");
        System.out.println("使用普通锁: 所有操作串行执行");
        System.out.println("使用读写锁: 读操作可以并发执行，只有写操作才互斥");
        System.out.println();
        
        data = 0;
        long startTime = System.currentTimeMillis();
        
        // 10个读线程
        Thread[] readers = new Thread[10];
        for (int i = 0; i < 10; i++) {
            readers[i] = new Thread(() -> {
                readLock.lock();
                try {
                    int value = data; // 读取操作
                    // 使用value避免警告
                    if (value < 0) {
                        // 不会执行，只是为了使用value
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            });
        }
        
        // 1个写线程
        Thread writer = new Thread(() -> {
            writeLock.lock();
            try {
                data++; // 写操作
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        });
        
        for (Thread reader : readers) {
            reader.start();
        }
        writer.start();
        
        for (Thread reader : readers) {
            try {
                reader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        try {
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("读写锁执行时间: " + (endTime - startTime) + "ms");
        System.out.println("✓ 读操作可以并发执行，性能更好");
    }

    /**
     * 演示读写锁的降级
     */
    public static void demonstrateLockDowngrade() {
        System.out.println("\n========== 读写锁降级 ==========");
        
        System.out.println("【锁降级】");
        System.out.println("在持有写锁的情况下，可以获取读锁，然后释放写锁，");
        System.out.println("这样就实现了从写锁降级到读锁。");
        System.out.println();
        System.out.println("【注意】");
        System.out.println("不支持锁升级（从读锁升级到写锁）");
        System.out.println();
        
        writeLock.lock();
        try {
            System.out.println("持有写锁，修改数据");
            data = 200;
            
            // 获取读锁（锁降级）
            readLock.lock();
            try {
                System.out.println("降级为读锁，读取数据: " + data);
            } finally {
                // 注意：这里不释放读锁，因为还要继续使用
            }
            
            // 释放写锁，但还持有读锁
            // 注意：实际代码中应该在finally中释放写锁
        } finally {
            writeLock.unlock();
            System.out.println("释放写锁，但仍持有读锁");
        }
        
        // 继续使用读锁
        try {
            System.out.println("使用读锁读取数据: " + data);
        } finally {
            readLock.unlock();
            System.out.println("释放读锁");
        }
    }

    /**
     * 综合演示ReadWriteLock
     */
    public static void demonstrateAll() {
        System.out.println("\n========== ReadWriteLock综合演示 ==========");
        
        demonstrateBasicUsage();
        demonstratePerformance();
        demonstrateLockDowngrade();
    }
}

