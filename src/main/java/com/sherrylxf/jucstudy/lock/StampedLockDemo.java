package com.sherrylxf.jucstudy.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock邮戳锁演示
 * 面试重点：StampedLock的特点、三种模式
 */
public class StampedLockDemo {

    private static int data = 0;
    private static final StampedLock stampedLock = new StampedLock();

    /**
     * 演示StampedLock的三种模式
     */
    public static void demonstrateThreeModes() {
        System.out.println("\n========== StampedLock三种模式 ==========");
        
        System.out.println("【三种模式】");
        System.out.println("1. 写锁（Write Lock）: 独占锁，类似ReentrantReadWriteLock的写锁");
        System.out.println("2. 悲观读锁（Pessimistic Read Lock）: 类似ReentrantReadWriteLock的读锁");
        System.out.println("3. 乐观读（Optimistic Read）: 无锁的读操作，不阻塞写操作");
        System.out.println();
        
        System.out.println("【特点】");
        System.out.println("- 所有获取锁的方法都返回一个stamp（邮戳）");
        System.out.println("- 释放锁时需要传入对应的stamp");
        System.out.println("- 不支持重入");
        System.out.println("- 乐观读不阻塞写操作，性能更好");
        System.out.println();
    }

    /**
     * 演示写锁
     */
    public static void demonstrateWriteLock() {
        System.out.println("\n========== StampedLock写锁 ==========");
        
        Thread writer1 = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("写线程1获取写锁");
                data = 100;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockWrite(stamp);
                System.out.println("写线程1释放写锁");
            }
        });
        
        Thread writer2 = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("写线程2获取写锁");
                data = 200;
            } finally {
                stampedLock.unlockWrite(stamp);
                System.out.println("写线程2释放写锁");
            }
        });
        
        writer1.start();
        writer2.start();
        
        try {
            writer1.join();
            writer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终数据: " + data);
        System.out.println("✓ 写锁是独占的，互斥");
    }

    /**
     * 演示悲观读锁
     */
    public static void demonstratePessimisticRead() {
        System.out.println("\n========== StampedLock悲观读锁 ==========");
        
        data = 0;
        
        // 多个读线程
        for (int i = 0; i < 5; i++) {
            final int readerId = i;
            new Thread(() -> {
                long stamp = stampedLock.readLock();
                try {
                    System.out.println("读线程" + readerId + "读取数据: " + data);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }).start();
        }
        
        // 写线程
        new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("写线程修改数据");
                data = 300;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }).start();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 悲观读锁类似ReadWriteLock的读锁");
    }

    /**
     * 演示乐观读（重点）
     */
    public static void demonstrateOptimisticRead() {
        System.out.println("\n========== StampedLock乐观读（重点） ==========");
        
        System.out.println("【乐观读的优势】");
        System.out.println("乐观读不阻塞写操作，性能更好");
        System.out.println("适用于读多写少的场景");
        System.out.println();
        
        data = 0;
        
        Thread reader = new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead(); // 尝试乐观读
            int value = data; // 读取数据
            
            // 验证stamp是否有效（期间是否有写操作）
            if (!stampedLock.validate(stamp)) {
                // 如果无效，升级为悲观读锁
                System.out.println("乐观读失败，升级为悲观读锁");
                stamp = stampedLock.readLock();
                try {
                    value = data; // 重新读取
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            
            System.out.println("读取到的数据: " + value);
        });
        
        Thread writer = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("写线程修改数据");
                data = 400;
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        });
        
        reader.start();
        writer.start();
        
        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 乐观读不阻塞写操作，但需要验证");
    }

    /**
     * 演示tryConvertToWriteLock（锁转换）
     */
    public static void demonstrateLockConversion() {
        System.out.println("\n========== StampedLock锁转换 ==========");
        
        System.out.println("【锁转换】");
        System.out.println("可以将读锁转换为写锁，但需要满足条件");
        System.out.println();
        
        long stamp = stampedLock.readLock();
        try {
            System.out.println("持有读锁，尝试转换为写锁");
            
            // 尝试转换为写锁
            long writeStamp = stampedLock.tryConvertToWriteLock(stamp);
            if (writeStamp != 0) {
                System.out.println("转换成功，持有写锁");
                data = 500;
                stamp = writeStamp; // 更新stamp
            } else {
                System.out.println("转换失败，需要先释放读锁再获取写锁");
                stampedLock.unlockRead(stamp);
                stamp = stampedLock.writeLock();
                data = 500;
            }
        } finally {
            stampedLock.unlock(stamp);
            System.out.println("释放锁");
        }
    }

    /**
     * 综合演示StampedLock
     */
    public static void demonstrateAll() {
        System.out.println("\n========== StampedLock综合演示 ==========");
        
        demonstrateThreeModes();
        demonstrateWriteLock();
        demonstratePessimisticRead();
        demonstrateOptimisticRead();
        demonstrateLockConversion();
    }
}




