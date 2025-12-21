package com.sherrylxf.jucstudy.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS综合演示类
 * 包含自定义同步器示例和基于AQS的常见类演示
 */
public class AQSDemo {

    /**
     * 自定义互斥锁（基于AQS实现）
     * 演示如何基于AQS实现一个简单的互斥锁
     */
    static class CustomMutex {
        private final Sync sync = new Sync();

        /**
         * 自定义同步器（独占模式）
         */
        static class Sync extends AbstractQueuedSynchronizer {
            /**
             * 尝试获取锁（state=0表示未锁定，state=1表示已锁定）
             */
            @Override
            protected boolean tryAcquire(int arg) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            /**
             * 尝试释放锁
             */
            @Override
            protected boolean tryRelease(int arg) {
                if (getState() == 0) {
                    throw new IllegalMonitorStateException();
                }
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }

            /**
             * 判断是否被当前线程独占
             */
            @Override
            protected boolean isHeldExclusively() {
                return getExclusiveOwnerThread() == Thread.currentThread();
            }

            /**
             * 创建条件变量
             */
            java.util.concurrent.locks.Condition newCondition() {
                return new ConditionObject();
            }
        }

        public void lock() {
            sync.acquire(1);
        }

        public void unlock() {
            sync.release(1);
        }

        public boolean tryLock() {
            return sync.tryAcquire(1);
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(timeout));
        }
    }

    /**
     * 自定义可重入锁（基于AQS实现）
     * 演示如何实现可重入锁
     */
    static class CustomReentrantLock {
        private final Sync sync = new Sync();

        static class Sync extends AbstractQueuedSynchronizer {
            /**
             * 尝试获取锁（支持可重入）
             */
            @Override
            protected boolean tryAcquire(int acquires) {
                final Thread current = Thread.currentThread();
                int c = getState();
                
                // 如果state=0，说明锁未被持有
                if (c == 0) {
                    if (compareAndSetState(0, acquires)) {
                        setExclusiveOwnerThread(current);
                        return true;
                    }
                }
                // 如果当前线程已经持有锁，则state加1（可重入）
                else if (current == getExclusiveOwnerThread()) {
                    int nextc = c + acquires;
                    if (nextc < 0) {
                        throw new Error("Maximum lock count exceeded");
                    }
                    setState(nextc);
                    return true;
                }
                return false;
            }

            /**
             * 尝试释放锁（支持可重入）
             */
            @Override
            protected boolean tryRelease(int releases) {
                int c = getState() - releases;
                if (Thread.currentThread() != getExclusiveOwnerThread()) {
                    throw new IllegalMonitorStateException();
                }
                boolean free = false;
                if (c == 0) {
                    free = true;
                    setExclusiveOwnerThread(null);
                }
                setState(c);
                return free;
            }

            @Override
            protected boolean isHeldExclusively() {
                return getExclusiveOwnerThread() == Thread.currentThread();
            }

            final int getHoldCount() {
                return isHeldExclusively() ? getState() : 0;
            }
        }

        public void lock() {
            sync.acquire(1);
        }

        public void unlock() {
            sync.release(1);
        }

        public int getHoldCount() {
            return sync.getHoldCount();
        }
    }

    /**
     * 自定义信号量（基于AQS实现，共享模式）
     */
    static class CustomSemaphore {
        private final Sync sync;

        CustomSemaphore(int permits) {
            sync = new Sync(permits);
        }

        static class Sync extends AbstractQueuedSynchronizer {
            Sync(int permits) {
                setState(permits);
            }

            /**
             * 尝试获取许可证（共享模式）
             */
            @Override
            protected int tryAcquireShared(int acquires) {
                for (;;) {
                    int available = getState();
                    int remaining = available - acquires;
                    if (remaining < 0 || compareAndSetState(available, remaining)) {
                        return remaining;
                    }
                }
            }

            /**
             * 尝试释放许可证（共享模式）
             */
            @Override
            protected boolean tryReleaseShared(int releases) {
                for (;;) {
                    int current = getState();
                    int next = current + releases;
                    if (next < current) {
                        throw new Error("Maximum permit count exceeded");
                    }
                    if (compareAndSetState(current, next)) {
                        return true;
                    }
                }
            }

            /**
             * 获取当前可用许可证数量
             */
            int getPermits() {
                return getState();
            }
        }

        public void acquire() throws InterruptedException {
            sync.acquireSharedInterruptibly(1);
        }

        public void release() {
            sync.releaseShared(1);
        }

        public int availablePermits() {
            return sync.getPermits();
        }
    }

    /**
     * 演示自定义互斥锁
     */
    public static void demonstrateCustomMutex() {
        System.out.println("\n========== 自定义互斥锁演示 ==========");
        
        CustomMutex mutex = new CustomMutex();
        int[] count = {0};
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                mutex.lock();
                try {
                    count[0]++;
                } finally {
                    mutex.unlock();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                mutex.lock();
                try {
                    count[0]++;
                } finally {
                    mutex.unlock();
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
        
        System.out.println("最终count: " + count[0]);
        System.out.println("✓ 自定义互斥锁保证了线程安全");
    }

    /**
     * 演示自定义可重入锁
     */
    public static void demonstrateCustomReentrantLock() {
        System.out.println("\n========== 自定义可重入锁演示 ==========");
        
        CustomReentrantLock lock = new CustomReentrantLock();
        
        // 测试可重入
        lock.lock();
        System.out.println("第一次获取锁，holdCount: " + lock.getHoldCount());
        
        lock.lock();
        System.out.println("第二次获取锁（可重入），holdCount: " + lock.getHoldCount());
        
        lock.unlock();
        System.out.println("释放一次，holdCount: " + lock.getHoldCount());
        
        lock.unlock();
        System.out.println("释放第二次，holdCount: " + lock.getHoldCount());
        System.out.println("✓ 自定义可重入锁支持同一线程多次获取");
    }

    /**
     * 演示自定义信号量
     */
    public static void demonstrateCustomSemaphore() {
        System.out.println("\n========== 自定义信号量演示 ==========");
        
        CustomSemaphore semaphore = new CustomSemaphore(3); // 允许3个线程同时访问
        System.out.println("初始可用许可证: " + semaphore.availablePermits());
        
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("线程" + threadId + "尝试获取许可证...");
                    semaphore.acquire();
                    System.out.println("线程" + threadId + "获取许可证成功，可用: " + semaphore.availablePermits());
                    
                    Thread.sleep(2000);
                    
                    semaphore.release();
                    System.out.println("线程" + threadId + "释放许可证，可用: " + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 自定义信号量控制并发访问数量");
    }

    /**
     * 演示CountDownLatch（基于AQS的共享模式）
     */
    public static void demonstrateCountDownLatch() {
        System.out.println("\n========== CountDownLatch演示 ==========");
        
        CountDownLatch latch = new CountDownLatch(3);
        
        // 工作线程
        for (int i = 1; i <= 3; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    System.out.println("工作线程" + workerId + "开始工作");
                    Thread.sleep(1000);
                    System.out.println("工作线程" + workerId + "完成工作");
                    latch.countDown(); // 计数减1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        // 主线程等待
        try {
            System.out.println("主线程等待所有工作线程完成...");
            latch.await(); // 等待计数为0
            System.out.println("✓ 所有工作线程已完成，主线程继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示Semaphore（基于AQS的共享模式）
     */
    public static void demonstrateSemaphore() {
        System.out.println("\n========== Semaphore演示 ==========");
        
        Semaphore semaphore = new Semaphore(2); // 允许2个线程同时访问
        
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("线程" + threadId + "尝试获取许可证...");
                    semaphore.acquire();
                    System.out.println("线程" + threadId + "获取许可证成功，开始执行");
                    
                    Thread.sleep(2000);
                    
                    semaphore.release();
                    System.out.println("线程" + threadId + "释放许可证");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ Semaphore控制同时访问资源的线程数量");
    }

    /**
     * 演示Semaphore的公平和非公平模式
     */
    public static void demonstrateSemaphoreFair() {
        System.out.println("\n========== Semaphore公平/非公平模式演示 ==========");
        
        System.out.println("【非公平Semaphore（默认）】");
        @SuppressWarnings("unused")
        Semaphore unfairSemaphore = new Semaphore(1, false);
        System.out.println("  多个线程竞争时，新来的线程可能插队");
        
        System.out.println("\n【公平Semaphore】");
        Semaphore fairSemaphore = new Semaphore(1, true);
        System.out.println("  多个线程竞争时，按照等待顺序获取许可证");
        
        System.out.println("\n演示公平Semaphore的使用：");
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("线程" + threadId + "等待获取许可证...");
                    fairSemaphore.acquire();
                    System.out.println("线程" + threadId + "获取许可证成功");
                    Thread.sleep(500);
                    fairSemaphore.release();
                    System.out.println("线程" + threadId + "释放许可证");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(50); // 间隔启动，便于观察顺序
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示CyclicBarrier（基于AQS和ReentrantLock实现）
     */
    public static void demonstrateCyclicBarrier() {
        System.out.println("\n========== CyclicBarrier演示 ==========");
        
        // 创建屏障，需要3个线程到达
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("所有线程已到达屏障，执行屏障任务");
        });
        
        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("线程" + threadId + "开始执行第一阶段任务");
                    Thread.sleep(1000 * threadId); // 模拟不同执行时间
                    System.out.println("线程" + threadId + "第一阶段任务完成，等待其他线程...");
                    
                    barrier.await(); // 等待所有线程到达
                    
                    System.out.println("线程" + threadId + "所有线程已就绪，开始执行第二阶段任务");
                    Thread.sleep(500);
                    System.out.println("线程" + threadId + "第二阶段任务完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ CyclicBarrier让多个线程在某个点同步");
    }

    /**
     * 演示CyclicBarrier的可重用特性
     */
    public static void demonstrateCyclicBarrierReuse() {
        System.out.println("\n========== CyclicBarrier可重用演示 ==========");
        
        // 创建屏障，需要3个线程到达
        CyclicBarrier barrier = new CyclicBarrier(3);
        
        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    // 第一轮
                    System.out.println("线程" + threadId + "第一轮任务执行");
                    Thread.sleep(500);
                    barrier.await();
                    System.out.println("线程" + threadId + "第一轮完成，继续执行");
                    
                    Thread.sleep(200);
                    
                    // 第二轮（屏障可以重用）
                    System.out.println("线程" + threadId + "第二轮任务执行");
                    Thread.sleep(500);
                    barrier.await();
                    System.out.println("线程" + threadId + "第二轮完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ CyclicBarrier可以重复使用，CountDownLatch不能重用");
    }

    /**
     * 演示CountDownLatch的多个阶段场景
     */
    public static void demonstrateCountDownLatchMultiStage() {
        System.out.println("\n========== CountDownLatch多阶段演示 ==========");
        
        CountDownLatch stage1Latch = new CountDownLatch(3);
        CountDownLatch stage2Latch = new CountDownLatch(3);
        
        // 工作线程
        for (int i = 1; i <= 3; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    // 第一阶段
                    System.out.println("工作线程" + workerId + "执行第一阶段任务");
                    Thread.sleep(1000);
                    System.out.println("工作线程" + workerId + "第一阶段完成");
                    stage1Latch.countDown();
                    
                    // 等待所有线程完成第一阶段
                    stage1Latch.await();
                    
                    // 第二阶段
                    System.out.println("工作线程" + workerId + "执行第二阶段任务");
                    Thread.sleep(1000);
                    System.out.println("工作线程" + workerId + "第二阶段完成");
                    stage2Latch.countDown();
                    
                    // 等待所有线程完成第二阶段
                    stage2Latch.await();
                    System.out.println("工作线程" + workerId + "所有阶段完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ 使用多个CountDownLatch可以实现多阶段同步");
    }

    /**
     * 演示ReentrantLock（基于AQS的独占模式）
     */
    public static void demonstrateReentrantLock() {
        System.out.println("\n========== ReentrantLock演示 ==========");
        
        ReentrantLock lock = new ReentrantLock();
        int[] count = {0};
        
        // 演示基本使用
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.lock();
                try {
                    count[0]++;
                } finally {
                    lock.unlock();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.lock();
                try {
                    count[0]++;
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
        
        System.out.println("最终count: " + count[0]);
        System.out.println("锁被持有的次数: " + lock.getHoldCount());
        System.out.println("是否有线程在等待: " + lock.hasQueuedThreads());
        System.out.println("✓ ReentrantLock保证了线程安全");
    }

    /**
     * 演示ReentrantLock的可重入特性
     */
    public static void demonstrateReentrantLockReentrant() {
        System.out.println("\n========== ReentrantLock可重入演示 ==========");
        
        ReentrantLock lock = new ReentrantLock();
        
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("第一次获取锁，holdCount: " + lock.getHoldCount());
                
                lock.lock();
                try {
                    System.out.println("第二次获取锁（可重入），holdCount: " + lock.getHoldCount());
                    
                    lock.lock();
                    try {
                        System.out.println("第三次获取锁（可重入），holdCount: " + lock.getHoldCount());
                    } finally {
                        lock.unlock();
                        System.out.println("释放第三次，holdCount: " + lock.getHoldCount());
                    }
                } finally {
                    lock.unlock();
                    System.out.println("释放第二次，holdCount: " + lock.getHoldCount());
                }
            } finally {
                lock.unlock();
                System.out.println("释放第一次，holdCount: " + lock.getHoldCount());
            }
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ ReentrantLock支持可重入，同一线程可以多次获取锁");
    }

    /**
     * 演示ReentrantLock的公平锁和非公平锁
     */
    public static void demonstrateReentrantLockFair() {
        System.out.println("\n========== ReentrantLock公平/非公平锁演示 ==========");
        
        System.out.println("【非公平锁（默认）】");
        @SuppressWarnings("unused")
        ReentrantLock unfairLock = new ReentrantLock(false);
        System.out.println("  新来的线程可能直接获取锁，性能高但可能导致饥饿");
        
        System.out.println("\n【公平锁】");
        ReentrantLock fairLock = new ReentrantLock(true);
        System.out.println("  线程按照等待顺序获取锁，避免饥饿但性能较低");
        
        System.out.println("\n演示公平锁的使用：");
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(50 * threadId); // 间隔启动
                    System.out.println("线程" + threadId + "尝试获取公平锁...");
                    fairLock.lock();
                    try {
                        System.out.println("线程" + threadId + "获取公平锁成功");
                        Thread.sleep(200);
                    } finally {
                        fairLock.unlock();
                        System.out.println("线程" + threadId + "释放公平锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示AQS的acquire流程
     */
    public static void demonstrateAcquireFlow() {
        System.out.println("\n========== AQS的acquire流程演示 ==========");
        
        CustomMutex mutex = new CustomMutex();
        
        // 线程1先获取锁
        Thread thread1 = new Thread(() -> {
            mutex.lock();
            try {
                System.out.println("线程1获取锁成功，持有3秒");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
                System.out.println("线程1释放锁");
            }
        });
        
        // 线程2后获取锁（会被阻塞）
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(100); // 确保thread1先获取锁
                System.out.println("线程2尝试获取锁（会被阻塞）");
                mutex.lock();
                System.out.println("线程2获取锁成功");
                mutex.unlock();
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
        
        System.out.println("✓ 演示了AQS的acquire流程：tryAcquire -> 入队 -> 自旋 -> 阻塞");
    }

    /**
     * 打印AQS知识点总结
     */
    public static void printSummary() {
        System.out.println("\n========== AQS知识点总结 ==========");
        AQSSummary.printFullSummary();
    }

    /**
     * 演示基于AQS实现的常用类
     */
    public static void demonstrateAQSBasedClasses() {
        System.out.println("\n========== 基于AQS实现的常用类演示 ==========");
        
        demonstrateCountDownLatch();
        demonstrateCountDownLatchMultiStage();
        demonstrateSemaphore();
        demonstrateSemaphoreFair();
        demonstrateCyclicBarrier();
        demonstrateCyclicBarrierReuse();
        demonstrateReentrantLock();
        demonstrateReentrantLockReentrant();
        demonstrateReentrantLockFair();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== AQS完整学习演示 ==========");
        
        // 先看理论总结
        printSummary();
        
        // 自定义同步器演示
        System.out.println("\n========== 自定义同步器演示 ==========");
        demonstrateCustomMutex();
        demonstrateCustomReentrantLock();
        demonstrateCustomSemaphore();
        demonstrateAcquireFlow();
        
        // 基于AQS实现的常用类演示
        demonstrateAQSBasedClasses();
        
        System.out.println("\n========== AQS学习演示完成 ==========");
    }
}

