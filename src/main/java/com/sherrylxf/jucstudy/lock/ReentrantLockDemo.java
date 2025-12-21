package com.sherrylxf.jucstudy.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock演示
 * 面试重点：ReentrantLock的使用、与synchronized的区别
 */
public class ReentrantLockDemo {

    private static int count = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * 演示ReentrantLock基本使用
     */
    public static void demonstrateBasicUsage() {
        System.out.println("\n========== ReentrantLock基本使用 ==========");
        
        count = 0;
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock(); // 必须在finally中释放锁
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终count: " + count);
        System.out.println("✓ ReentrantLock保证了线程安全");
    }

    /**
     * 演示tryLock()方法
     */
    public static void demonstrateTryLock() {
        System.out.println("\n========== ReentrantLock tryLock() ==========");
        
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        
        Thread thread1 = new Thread(() -> {
            if (lock1.tryLock()) {
                try {
                    System.out.println("线程1获取lock1成功");
                    Thread.sleep(100);
                    if (lock2.tryLock()) {
                        try {
                            System.out.println("线程1获取lock2成功");
                        } finally {
                            lock2.unlock();
                        }
                    } else {
                        System.out.println("线程1获取lock2失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock1.unlock();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            if (lock2.tryLock()) {
                try {
                    System.out.println("线程2获取lock2成功");
                    Thread.sleep(100);
                    if (lock1.tryLock()) {
                        try {
                            System.out.println("线程2获取lock1成功");
                        } finally {
                            lock1.unlock();
                        }
                    } else {
                        System.out.println("线程2获取lock1失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ tryLock()可以避免死锁");
    }

    /**
     * 演示tryLock(timeout)方法
     */
    public static void demonstrateTryLockWithTimeout() {
        System.out.println("\n========== ReentrantLock tryLock(timeout) ==========");
        
        ReentrantLock lock = new ReentrantLock();
        
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程1持有锁，持续3秒");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("线程2尝试获取锁（最多等待2秒）");
                boolean acquired = lock.tryLock(2, java.util.concurrent.TimeUnit.SECONDS);
                if (acquired) {
                    try {
                        System.out.println("线程2成功获取锁");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("线程2等待超时，未获取到锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示公平锁和非公平锁
     */
    public static void demonstrateFairLock() {
        System.out.println("\n========== 公平锁 vs 非公平锁 ==========");
        
        System.out.println("【非公平锁（默认）】");
        @SuppressWarnings("unused")
        ReentrantLock unfairLock = new ReentrantLock(false);
        System.out.println("  优点: 性能高，吞吐量大");
        System.out.println("  缺点: 可能导致线程饥饿");
        System.out.println();
        
        System.out.println("【公平锁】");
        @SuppressWarnings("unused")
        ReentrantLock fairLock = new ReentrantLock(true);
        System.out.println("  优点: 线程按顺序获取锁，避免饥饿");
        System.out.println("  缺点: 性能较低，需要维护队列");
        System.out.println();
        
        System.out.println("【使用建议】");
        System.out.println("  默认使用非公平锁，除非有特殊需求");
        System.out.println();
    }

    /**
     * 演示可中断锁
     */
    public static void demonstrateInterruptibleLock() {
        System.out.println("\n========== 可中断锁 ==========");
        
        ReentrantLock lock = new ReentrantLock();
        
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程1持有锁，持续5秒");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("线程1被中断");
            } finally {
                lock.unlock();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("线程2尝试获取锁（可中断）");
                lock.lockInterruptibly(); // 可中断的获取锁
                try {
                    System.out.println("线程2成功获取锁");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("线程2在等待锁时被中断");
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("中断线程2");
        thread2.interrupt();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ lockInterruptibly()允许在等待锁时响应中断");
    }

    /**
     * 演示Condition条件变量
     */
    public static void demonstrateCondition() {
        System.out.println("\n========== ReentrantLock + Condition ==========");
        
        ReentrantLock lock = new ReentrantLock();
        java.util.concurrent.locks.Condition condition = lock.newCondition();
        final boolean[] flag = {false};
        
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("生产者等待条件满足...");
                while (!flag[0]) {
                    condition.await(); // 等待条件
                }
                System.out.println("生产者继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            lock.lock();
            try {
                flag[0] = true;
                System.out.println("消费者设置条件，唤醒生产者");
                condition.signal(); // 唤醒等待的线程
            } finally {
                lock.unlock();
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
        
        System.out.println("✓ Condition提供了类似wait/notify的功能，但更灵活");
    }

    /**
     * 综合演示ReentrantLock
     */
    public static void demonstrateAll() {
        System.out.println("\n========== ReentrantLock综合演示 ==========");
        
        demonstrateBasicUsage();
        demonstrateTryLock();
        demonstrateTryLockWithTimeout();
        demonstrateFairLock();
        demonstrateInterruptibleLock();
        demonstrateCondition();
    }
}

