package com.sherrylxf.jucstudy.singleton;

/**
 * 单例模式的多种线程安全实现
 * 面试重点：各种实现方式的优缺点、线程安全性
 */
public class SingletonDemo {

    /**
     * 1. 饿汉式（线程安全）
     * 优点：简单，线程安全，类加载时创建
     * 缺点：如果不需要使用，也会创建实例，浪费内存
     */
    public static class HungrySingleton {
        private static final HungrySingleton INSTANCE = new HungrySingleton();
        
        private HungrySingleton() {
            System.out.println("饿汉式单例：实例已创建");
        }
        
        public static HungrySingleton getInstance() {
            return INSTANCE;
        }
    }

    /**
     * 2. 懒汉式（线程不安全）
     * 问题：多线程环境下可能创建多个实例
     */
    public static class LazySingletonUnsafe {
        private static LazySingletonUnsafe instance;
        
        private LazySingletonUnsafe() {
            System.out.println("懒汉式（不安全）：实例已创建");
        }
        
        public static LazySingletonUnsafe getInstance() {
            if (instance == null) {
                instance = new LazySingletonUnsafe();
            }
            return instance;
        }
    }

    /**
     * 3. 懒汉式 + synchronized（线程安全，但性能低）
     * 优点：线程安全
     * 缺点：每次获取实例都要加锁，性能低
     */
    public static class LazySingletonSynchronized {
        private static LazySingletonSynchronized instance;
        
        private LazySingletonSynchronized() {
            System.out.println("懒汉式+synchronized：实例已创建");
        }
        
        public static synchronized LazySingletonSynchronized getInstance() {
            if (instance == null) {
                instance = new LazySingletonSynchronized();
            }
            return instance;
        }
    }

    /**
     * 4. 双重检查锁定（DCL）- 错误实现
     * 问题：存在指令重排序问题，可能返回未初始化的对象
     */
    public static class DoubleCheckLockingWrong {
        private static DoubleCheckLockingWrong instance; // 没有volatile
        
        private DoubleCheckLockingWrong() {
            System.out.println("DCL（错误）：实例已创建");
        }
        
        public static DoubleCheckLockingWrong getInstance() {
            if (instance == null) { // 第一次检查：避免不必要的同步
                synchronized (DoubleCheckLockingWrong.class) {
                    if (instance == null) { // 第二次检查：确保只有一个线程创造实例
                        instance = new DoubleCheckLockingWrong();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 5. 双重检查锁定（DCL）- 正确实现
     * 使用volatile防止指令重排序
     * 优点：线程安全，性能好（只有第一次需要加锁）
     * 缺点：代码较复杂
     */
    public static class DoubleCheckLockingCorrect {
        private static volatile DoubleCheckLockingCorrect instance; // 使用volatile，避免指令重排序
        
        private DoubleCheckLockingCorrect() {
            System.out.println("DCL（正确）：实例已创建");
        }
        
        public static DoubleCheckLockingCorrect getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckLockingCorrect.class) {
                    if (instance == null) {
                        instance = new DoubleCheckLockingCorrect();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 6. 静态内部类（推荐）
     * 优点：线程安全，延迟加载，性能好，代码简洁
     * 原理：静态内部类只有在被调用时才会加载，类加载是线程安全的
     */
    public static class StaticInnerClassSingleton {
        private StaticInnerClassSingleton() {
            System.out.println("静态内部类：实例已创建");
        }
        
        private static class SingletonHolder {
            private static final StaticInnerClassSingleton INSTANCE = 
                new StaticInnerClassSingleton();
        }
        
        public static StaticInnerClassSingleton getInstance() {
            return SingletonHolder.INSTANCE;
        }
    }

    /**
     * 7. 枚举（最推荐）
     * 优点：线程安全，防止反射攻击，防止反序列化创建新实例，代码最简洁
     * 缺点：不是懒加载
     */
    public enum EnumSingleton {
        INSTANCE;
        
        public void doSomething() {

        }
    }

    /**
     * 演示各种单例模式的线程安全性
     */
    public static void demonstrateThreadSafety() {
        System.out.println("\n========== 单例模式线程安全性测试 ==========");
        
        System.out.println("【1. 饿汉式】");
        testSingleton(() -> HungrySingleton.getInstance(), "HungrySingleton");
        
        System.out.println("\n【2. 懒汉式（不安全）】");
        testSingleton(() -> LazySingletonUnsafe.getInstance(), "LazySingletonUnsafe");
        
        System.out.println("\n【3. 懒汉式+synchronized】");
        testSingleton(() -> LazySingletonSynchronized.getInstance(), "LazySingletonSynchronized");
        
        System.out.println("\n【4. DCL（正确实现）】");
        testSingleton(() -> DoubleCheckLockingCorrect.getInstance(), "DoubleCheckLockingCorrect");
        
        System.out.println("\n【5. 静态内部类】");
        testSingleton(() -> StaticInnerClassSingleton.getInstance(), "StaticInnerClassSingleton");
        
        System.out.println("\n【6. 枚举】");
        testSingleton(() -> EnumSingleton.INSTANCE, "EnumSingleton");
    }

    private static void testSingleton(java.util.function.Supplier<Object> supplier, String name) {
        java.util.Set<Object> instances = new java.util.HashSet<>();
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Object instance = supplier.get();
                instances.add(instance);
                latch.countDown();
            }).start();
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("  创建了 " + instances.size() + " 个实例");
        if (instances.size() == 1) {
            System.out.println("  ✓ 线程安全");
        } else {
            System.out.println("  ✗ 线程不安全！");
        }
    }

    /**
     * 演示各种实现方式的性能对比
     */
    public static void demonstratePerformance() {
        System.out.println("\n========== 单例模式性能对比 ==========");
        
        int iterations = 10000000;
        
        // 饿汉式
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            HungrySingleton.getInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("饿汉式: " + (end - start) + "ms");
        
        // 懒汉式+synchronized
        start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            LazySingletonSynchronized.getInstance();
        }
        end = System.currentTimeMillis();
        System.out.println("懒汉式+synchronized: " + (end - start) + "ms");
        
        // DCL
        start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            DoubleCheckLockingCorrect.getInstance();
        }
        end = System.currentTimeMillis();
        System.out.println("DCL: " + (end - start) + "ms");
        
        // 静态内部类
        start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            StaticInnerClassSingleton.getInstance();
        }
        end = System.currentTimeMillis();
        System.out.println("静态内部类: " + (end - start) + "ms");
        
        // 枚举
        start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            EnumSingleton.INSTANCE.doSomething();
        }
        end = System.currentTimeMillis();
        System.out.println("枚举: " + (end - start) + "ms");
    }

    /**
     * 打印各种实现方式的对比
     */
    public static void printComparison() {
        System.out.println("\n========== 单例模式实现方式对比 ==========");
        System.out.println();
        System.out.println("┌──────────────┬──────────┬──────────┬──────────┬──────────┐");
        System.out.println("│   实现方式   │ 线程安全 │ 延迟加载 │   性能   │ 代码简洁 │");
        System.out.println("├──────────────┼──────────┼──────────┼──────────┼──────────┤");
        System.out.println("│   饿汉式     │    ✓     │    ✗     │    高     │    高     │");
        System.out.println("│ 懒汉式(不安全)│    ✗     │    ✓     │    高     │    高     │");
        System.out.println("│懒汉式+sync   │    ✓     │    ✓     │    低     │    中     │");
        System.out.println("│   DCL        │    ✓     │    ✓     │    高     │    低     │");
        System.out.println("│ 静态内部类   │    ✓     │    ✓     │    高     │    高     │");
        System.out.println("│    枚举      │    ✓     │    ✗     │    高     │    高     │");
        System.out.println("└──────────────┴──────────┴──────────┴──────────┴──────────┘");
        System.out.println();
        System.out.println("【推荐】");
        System.out.println("1. 如果需要延迟加载: 使用静态内部类（最推荐）");
        System.out.println("2. 如果不需要延迟加载: 使用枚举（最推荐）");
        System.out.println("3. 如果对性能要求极高: 使用饿汉式");
        System.out.println();
    }

    /**
     * 综合演示单例模式
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 单例模式综合演示 ==========");
        
        demonstrateThreadSafety();
        demonstratePerformance();
        printComparison();
    }
}

