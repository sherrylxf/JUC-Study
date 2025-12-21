package com.sherrylxf.jucstudy.lock;

/**
 * synchronized关键字演示
 * 面试重点：synchronized的使用方式、原理、锁升级
 */
public class SynchronizedDemo {

    private static int count = 0;
    private final Object lock = new Object();

    /**
     * 演示synchronized修饰实例方法
     */
    public static void demonstrateInstanceMethod() {
        System.out.println("\n========== synchronized修饰实例方法 ==========");
        
        SynchronizedDemo demo = new SynchronizedDemo();
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.increment();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.increment();
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
        
        System.out.println("最终count: " + demo.getCount());
        System.out.println("✓ synchronized实例方法锁的是当前对象(this)");
    }

    /**
     * 演示synchronized修饰静态方法
     */
    public static void demonstrateStaticMethod() {
        System.out.println("\n========== synchronized修饰静态方法 ==========");
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                SynchronizedDemo.incrementStatic();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                SynchronizedDemo.incrementStatic();
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
        
        System.out.println("最终staticCount: " + staticCount);
        System.out.println("✓ synchronized静态方法锁的是类对象(Class对象)");
    }

    /**
     * 演示synchronized代码块
     */
    public static void demonstrateSynchronizedBlock() {
        System.out.println("\n========== synchronized代码块 ==========");
        
        SynchronizedDemo demo = new SynchronizedDemo();
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.incrementWithBlock();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.incrementWithBlock();
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
        
        System.out.println("最终count: " + demo.getCount());
        System.out.println("✓ synchronized代码块锁的是指定的对象");
    }

    /**
     * 演示可重入性
     */
    public static void demonstrateReentrant() {
        System.out.println("\n========== synchronized的可重入性 ==========");
        
        SynchronizedDemo demo = new SynchronizedDemo();
        
        Thread thread = new Thread(() -> {
            demo.method1();
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ synchronized是可重入锁，同一个线程可以多次获取同一把锁");
    }

    /**
     * 演示锁的升级过程（概念说明）
     */
    public static void demonstrateLockUpgrade() {
        System.out.println("\n========== synchronized锁升级过程 ==========");
        
        System.out.println("【锁的四种状态】");
        System.out.println("1. 无锁状态");
        System.out.println("2. 偏向锁: 只有一个线程访问时，使用偏向锁");
        System.out.println("3. 轻量级锁: 多个线程竞争但不激烈时，使用轻量级锁（自旋锁）");
        System.out.println("4. 重量级锁: 竞争激烈时，升级为重量级锁（互斥锁）");
        System.out.println();
        
        System.out.println("【升级过程】");
        System.out.println("无锁 -> 偏向锁 -> 轻量级锁 -> 重量级锁");
        System.out.println();
        
        System.out.println("【偏向锁】");
        System.out.println("- 优点: 只有一个线程访问时，几乎无开销");
        System.out.println("- 原理: 在对象头中记录线程ID");
        System.out.println();
        
        System.out.println("【轻量级锁】");
        System.out.println("- 优点: 使用CAS自旋，避免线程阻塞");
        System.out.println("- 缺点: 自旋会消耗CPU");
        System.out.println();
        
        System.out.println("【重量级锁】");
        System.out.println("- 优点: 线程阻塞，不消耗CPU");
        System.out.println("- 缺点: 线程切换开销大");
        System.out.println();
    }

    /**
     * 演示死锁问题
     */
    public static void demonstrateDeadlock() {
        System.out.println("\n========== synchronized死锁演示 ==========");
        
        Object lock1 = new Object();
        Object lock2 = new Object();
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程1获取lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1获取lock2");
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("线程2获取lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2获取lock1");
                }
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (thread1.isAlive() || thread2.isAlive()) {
            System.out.println("⚠️ 检测到死锁！两个线程互相等待对方释放锁");
        }
    }

    // 实例方法
    public synchronized void increment() {
        count++;
    }

    // 静态方法
    private static int staticCount = 0;
    public static synchronized void incrementStatic() {
        staticCount++;
    }

    // 代码块
    public void incrementWithBlock() {
        synchronized (lock) {
            count++;
        }
    }

    // 可重入演示
    public synchronized void method1() {
        System.out.println("method1获取锁");
        method2(); // 调用另一个synchronized方法
    }

    public synchronized void method2() {
        System.out.println("method2获取锁（可重入）");
    }

    public int getCount() {
        return count;
    }

    /**
     * 综合演示synchronized
     */
    public static void demonstrateAll() {
        System.out.println("\n========== synchronized综合演示 ==========");
        
        demonstrateInstanceMethod();
        demonstrateStaticMethod();
        demonstrateSynchronizedBlock();
        demonstrateReentrant();
        demonstrateLockUpgrade();
        demonstrateDeadlock();
    }
}

