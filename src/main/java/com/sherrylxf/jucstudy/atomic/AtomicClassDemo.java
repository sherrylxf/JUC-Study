package com.sherrylxf.jucstudy.atomic;

import java.util.concurrent.atomic.*;

/**
 * 原子类演示
 * 面试重点：CAS原理、各种原子类的使用
 */
public class AtomicClassDemo {

    private static int normalInt = 0;
    private static final AtomicInteger atomicInt = new AtomicInteger(0);
    private static final AtomicLong atomicLong = new AtomicLong(0L);
    private static final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    private static final AtomicReference<String> atomicReference = new AtomicReference<>("初始值");
    private static final AtomicStampedReference<String> atomicStampedRef = 
        new AtomicStampedReference<>("初始值", 1);
    private static final AtomicIntegerArray atomicArray = new AtomicIntegerArray(10);

    /**
     * 演示AtomicInteger的基本使用
     */
    public static void demonstrateAtomicInteger() {
        System.out.println("\n========== AtomicInteger基本使用 ==========");
        
        System.out.println("【普通int vs AtomicInteger】");
        
        // 普通int
        normalInt = 0;
        Thread[] threads1 = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    normalInt++; // 非原子操作
                }
            });
        }
        
        for (Thread thread : threads1) {
            thread.start();
        }
        
        for (Thread thread : threads1) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("普通int结果: " + normalInt + " (预期: 10000)");
        
        // AtomicInteger
        atomicInt.set(0);
        Thread[] threads2 = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads2[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInt.incrementAndGet(); // 原子操作
                }
            });
        }
        
        for (Thread thread : threads2) {
            thread.start();
        }
        
        for (Thread thread : threads2) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("AtomicInteger结果: " + atomicInt.get() + " (预期: 10000)");
        System.out.println("✓ AtomicInteger保证了原子性");
    }

    /**
     * 演示AtomicInteger的常用方法
     */
    public static void demonstrateAtomicIntegerMethods() {
        System.out.println("\n========== AtomicInteger常用方法 ==========");
        
        atomicInt.set(10);
        System.out.println("初始值: " + atomicInt.get());
        
        System.out.println("getAndIncrement(): " + atomicInt.getAndIncrement() + 
            " (返回旧值，然后+1)");
        System.out.println("当前值: " + atomicInt.get());
        
        System.out.println("incrementAndGet(): " + atomicInt.incrementAndGet() + 
            " (先+1，返回新值)");
        System.out.println("当前值: " + atomicInt.get());
        
        System.out.println("getAndAdd(5): " + atomicInt.getAndAdd(5) + 
            " (返回旧值，然后+5)");
        System.out.println("当前值: " + atomicInt.get());
        
        System.out.println("addAndGet(5): " + atomicInt.addAndGet(5) + 
            " (先+5，返回新值)");
        System.out.println("当前值: " + atomicInt.get());
        
        System.out.println("compareAndSet(25, 100): " + 
            atomicInt.compareAndSet(25, 100) + " (CAS操作)");
        System.out.println("当前值: " + atomicInt.get());
        
        System.out.println("compareAndSet(25, 200): " + 
            atomicInt.compareAndSet(25, 200) + " (CAS操作失败，因为当前值不是25)");
        System.out.println("当前值: " + atomicInt.get());
    }

    /**
     * 演示CAS原理
     */
    public static void demonstrateCAS() {
        System.out.println("\n========== CAS (Compare-And-Swap) 原理 ==========");
        
        System.out.println("【CAS操作】");
        System.out.println("CAS是一种无锁算法，包含三个操作数：");
        System.out.println("1. 内存位置 (V)");
        System.out.println("2. 预期原值 (A)");
        System.out.println("3. 新值 (B)");
        System.out.println();
        System.out.println("只有当V的值等于A时，才会将V的值更新为B，否则不执行任何操作。");
        System.out.println();
        
        System.out.println("【CAS实现】");
        System.out.println("AtomicInteger的incrementAndGet()方法：");
        System.out.println("public final int incrementAndGet() {");
        System.out.println("    return unsafe.getAndAddInt(this, valueOffset, 1) + 1;");
        System.out.println("}");
        System.out.println();
        System.out.println("底层使用Unsafe类的CAS操作，由CPU的原子指令保证。");
        System.out.println();
        
        System.out.println("【CAS的优缺点】");
        System.out.println("优点:");
        System.out.println("  - 无锁，性能高");
        System.out.println("  - 避免了线程阻塞和上下文切换");
        System.out.println("缺点:");
        System.out.println("  - ABA问题");
        System.out.println("  - 循环时间长开销大（如果CAS失败，会一直重试）");
        System.out.println("  - 只能保证一个共享变量的原子操作");
        System.out.println();
    }

    /**
     * 演示AtomicReference
     */
    public static void demonstrateAtomicReference() {
        System.out.println("\n========== AtomicReference演示 ==========");
        
        System.out.println("初始值: " + atomicReference.get());
        
        Thread thread1 = new Thread(() -> {
            boolean success = atomicReference.compareAndSet("初始值", "线程1的值");
            System.out.println("线程1 CAS操作: " + success);
        });
        
        Thread thread2 = new Thread(() -> {
            boolean success = atomicReference.compareAndSet("初始值", "线程2的值");
            System.out.println("线程2 CAS操作: " + success);
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终值: " + atomicReference.get());
        System.out.println("✓ 只有一个线程的CAS操作会成功");
    }

    /**
     * 演示ABA问题及AtomicStampedReference解决方案
     */
    public static void demonstrateABAProblem() {
        System.out.println("\n========== ABA问题及解决方案 ==========");
        
        System.out.println("【ABA问题】");
        System.out.println("线程1读取值A，准备修改为B");
        System.out.println("线程2将A改为B，然后又改回A");
        System.out.println("线程1执行CAS，发现值还是A，认为没有被修改过，执行成功");
        System.out.println("但实际上值已经被修改过了！");
        System.out.println();
        
        System.out.println("【解决方案：AtomicStampedReference】");
        System.out.println("使用版本号（stamp）来标记每次修改，解决ABA问题");
        System.out.println();
        
        int[] stampHolder = new int[1];
        String current = atomicStampedRef.get(stampHolder);
        int currentStamp = stampHolder[0];
        
        System.out.println("初始值: " + current + ", 版本号: " + currentStamp);
        
        Thread thread1 = new Thread(() -> {
            int[] stamp = new int[1];
            String value = atomicStampedRef.get(stamp);
            System.out.println("线程1读取: " + value + ", 版本号: " + stamp[0]);
            
            try {
                Thread.sleep(1000); // 模拟处理时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            boolean success = atomicStampedRef.compareAndSet(value, "新值", stamp[0], stamp[0] + 1);
            System.out.println("线程1 CAS操作: " + success);
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            int[] stamp = new int[1];
            String value = atomicStampedRef.get(stamp);
            System.out.println("线程2读取: " + value + ", 版本号: " + stamp[0]);
            
            // 修改为其他值
            atomicStampedRef.compareAndSet(value, "中间值", stamp[0], stamp[0] + 1);
            System.out.println("线程2修改为: 中间值");
            
            // 再改回原值
            int[] newStamp = new int[1];
            String newValue = atomicStampedRef.get(newStamp);
            atomicStampedRef.compareAndSet(newValue, value, newStamp[0], newStamp[0] + 1);
            System.out.println("线程2改回: " + value);
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        int[] finalStamp = new int[1];
        String finalValue = atomicStampedRef.get(finalStamp);
        System.out.println("最终值: " + finalValue + ", 版本号: " + finalStamp[0]);
        System.out.println("✓ 使用版本号可以检测到ABA问题");
    }

    /**
     * 演示AtomicIntegerArray
     */
    public static void demonstrateAtomicArray() {
        System.out.println("\n========== AtomicIntegerArray演示 ==========");
        
        System.out.println("初始数组: ");
        for (int i = 0; i < atomicArray.length(); i++) {
            System.out.print(atomicArray.get(i) + " ");
        }
        System.out.println();
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    atomicArray.incrementAndGet(index);
                }
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("最终数组: ");
        for (int i = 0; i < atomicArray.length(); i++) {
            System.out.print(atomicArray.get(i) + " ");
        }
        System.out.println();
        System.out.println("✓ 每个元素都是原子操作");
    }

    /**
     * 演示其他原子类
     */
    public static void demonstrateOtherAtomicClasses() {
        System.out.println("\n========== 其他原子类演示 ==========");
        
        // AtomicLong
        atomicLong.set(100L);
        System.out.println("AtomicLong初始值: " + atomicLong.get());
        System.out.println("incrementAndGet(): " + atomicLong.incrementAndGet());
        
        // AtomicBoolean
        atomicBoolean.set(false);
        System.out.println("AtomicBoolean初始值: " + atomicBoolean.get());
        System.out.println("compareAndSet(false, true): " + 
            atomicBoolean.compareAndSet(false, true));
        System.out.println("当前值: " + atomicBoolean.get());
        
        System.out.println();
        System.out.println("【其他原子类】");
        System.out.println("- AtomicLong: 长整型原子类");
        System.out.println("- AtomicBoolean: 布尔型原子类");
        System.out.println("- AtomicReference: 引用类型原子类");
        System.out.println("- AtomicStampedReference: 带版本号的引用原子类（解决ABA问题）");
        System.out.println("- AtomicMarkableReference: 带标记位的引用原子类");
        System.out.println("- AtomicIntegerArray: 整型数组原子类");
        System.out.println("- AtomicLongArray: 长整型数组原子类");
        System.out.println("- AtomicReferenceArray: 引用类型数组原子类");
    }

    /**
     * 综合演示原子类
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 原子类综合演示 ==========");
        
        demonstrateAtomicInteger();
        demonstrateAtomicIntegerMethods();
        demonstrateCAS();
        demonstrateAtomicReference();
        demonstrateABAProblem();
        demonstrateAtomicArray();
        demonstrateOtherAtomicClasses();
    }
}


