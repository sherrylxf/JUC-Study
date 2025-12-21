package com.sherrylxf.jucstudy.jmm;

/**
 * happens-before规则演示
 * 面试重点：happens-before规则、内存可见性保证
 */
public class HappensBeforeDemo {

    private static int x = 0;
    private static int y = 0;
    private static volatile boolean flag = false;

    /**
     * 打印happens-before基本概念
     */
    public static void printHappensBeforeConcepts() {
        System.out.println("\n========== happens-before规则 ==========");
        System.out.println();
        
        System.out.println("【什么是happens-before】");
        System.out.println("happens-before是JMM的核心概念，用于描述两个操作之间的可见性。");
        System.out.println("如果操作A happens-before 操作B，那么A的结果对B可见。");
        System.out.println();
        
        System.out.println("【注意】");
        System.out.println("happens-before并不意味着时间上的先后顺序，");
        System.out.println("而是指内存可见性。即使A在时间上先于B执行，");
        System.out.println("如果没有happens-before关系，B也可能看不到A的结果。");
        System.out.println();
    }

    /**
     * 打印happens-before的8大规则
     */
    public static void printHappensBeforeRules() {
        System.out.println("\n========== happens-before的8大规则 ==========");
        System.out.println();
        
        System.out.println("1. 【程序顺序规则】");
        System.out.println("   同一线程中，前面的操作happens-before后面的操作");
        System.out.println("   示例: x = 1; y = 2; // x=1 happens-before y=2");
        System.out.println();
        
        System.out.println("2. 【监视器锁规则】");
        System.out.println("   对一个锁的解锁happens-before于随后对这个锁的加锁");
        System.out.println("   示例: synchronized块中的操作对后续获取同一锁的线程可见");
        System.out.println();
        
        System.out.println("3. 【volatile变量规则】");
        System.out.println("   对一个volatile变量的写happens-before于任意后续对这个变量的读");
        System.out.println("   示例: volatileFlag = true; if(volatileFlag) {...}");
        System.out.println();
        
        System.out.println("4. 【线程启动规则】");
        System.out.println("   Thread.start()的调用happens-before于被启动线程中的任意操作");
        System.out.println("   示例: thread.start(); // start() happens-before run()中的操作");
        System.out.println();
        
        System.out.println("5. 【线程终止规则】");
        System.out.println("   线程中的任意操作happens-before于该线程的终止检测");
        System.out.println("   示例: thread.join(); // 线程中的操作happens-before join()返回");
        System.out.println();
        
        System.out.println("6. 【线程中断规则】");
        System.out.println("   对线程interrupt()的调用happens-before于被中断线程检测到中断");
        System.out.println("   示例: thread.interrupt(); // interrupt() happens-before isInterrupted()");
        System.out.println();
        
        System.out.println("7. 【对象终结规则】");
        System.out.println("   对象的构造函数执行结束happens-before于finalize()方法");
        System.out.println();
        
        System.out.println("8. 【传递性】");
        System.out.println("   如果A happens-before B，B happens-before C，");
        System.out.println("   那么A happens-before C");
        System.out.println();
    }

    /**
     * 演示程序顺序规则
     */
    public static void demonstrateProgramOrderRule() {
        System.out.println("\n========== 程序顺序规则演示 ==========");
        
        System.out.println("同一线程中，前面的操作happens-before后面的操作");
        System.out.println();
        
        Thread thread = new Thread(() -> {
            x = 1;  // 操作1
            y = 2;  // 操作2
            System.out.println("线程中: x = " + x + ", y = " + y);
            System.out.println("操作1 happens-before 操作2");
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示volatile规则
     */
    public static void demonstrateVolatileRule() {
        System.out.println("\n========== volatile规则演示 ==========");
        
        System.out.println("volatile变量的写happens-before于任意后续对这个变量的读");
        System.out.println();
        
        Thread writer = new Thread(() -> {
            x = 100;           // 操作1
            flag = true;       // 操作2 (volatile写)
            System.out.println("Writer: x = " + x + ", flag = true");
        });
        
        Thread reader = new Thread(() -> {
            while (!flag) {
                // 等待flag变为true
            }
            // 由于volatile规则，这里能看到x=100
            System.out.println("Reader: 看到 x = " + x);
            System.out.println("volatile写happens-before volatile读");
        });
        
        reader.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        writer.start();
        
        try {
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 重置
        flag = false;
        x = 0;
    }

    /**
     * 演示线程启动规则
     */
    public static void demonstrateThreadStartRule() {
        System.out.println("\n========== 线程启动规则演示 ==========");
        
        System.out.println("Thread.start()的调用happens-before于被启动线程中的任意操作");
        System.out.println();
        
        x = 0;
        
        Thread thread = new Thread(() -> {
            // 由于线程启动规则，这里能看到x=10
            System.out.println("子线程中: x = " + x);
            System.out.println("start() happens-before run()中的操作");
        });
        
        x = 10;  // 在start()之前设置
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示传递性
     */
    public static void demonstrateTransitivity() {
        System.out.println("\n========== happens-before传递性演示 ==========");
        
        System.out.println("如果A happens-before B，B happens-before C，那么A happens-before C");
        System.out.println();
        
        x = 0;
        flag = false;
        
        Thread thread1 = new Thread(() -> {
            x = 1;           // 操作A
            flag = true;     // 操作B (volatile写)
        });
        
        Thread thread2 = new Thread(() -> {
            while (!flag) {
                // 等待flag变为true
            }
            // 操作C: 由于传递性，这里能看到x=1
            System.out.println("线程2看到: x = " + x);
            System.out.println("A happens-before B (程序顺序规则)");
            System.out.println("B happens-before C (volatile规则)");
            System.out.println("因此: A happens-before C (传递性)");
        });
        
        thread2.start();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        thread1.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 重置
        flag = false;
        x = 0;
    }

    /**
     * 综合演示happens-before
     */
    public static void demonstrateAll() {
        System.out.println("\n========== happens-before规则综合演示 ==========");
        
        printHappensBeforeConcepts();
        printHappensBeforeRules();
        demonstrateProgramOrderRule();
        demonstrateVolatileRule();
        demonstrateThreadStartRule();
        demonstrateTransitivity();
    }
}






