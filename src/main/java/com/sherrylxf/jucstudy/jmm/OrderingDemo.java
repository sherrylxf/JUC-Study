package com.sherrylxf.jucstudy.jmm;

/**
 * 有序性问题演示
 * 面试重点：指令重排序、happens-before
 */
public class OrderingDemo {

    private static int x = 0;
    private static int y = 0;
    private static int a = 0;
    private static int b = 0;

    /**
     * 演示指令重排序问题
     * 在某些情况下，编译器和处理器会重排序指令以优化性能
     */
    public static void demonstrateInstructionReordering() {
        System.out.println("\n========== 指令重排序演示 ==========");
        
        System.out.println("【什么是指令重排序】");
        System.out.println("为了提高性能，编译器和处理器可能会对指令进行重排序。");
        System.out.println("重排序不会影响单线程程序的执行结果，");
        System.out.println("但可能影响多线程程序的正确性。");
        System.out.println();
        
        System.out.println("【重排序的类型】");
        System.out.println("1. 编译器重排序: 编译器在不改变单线程语义的前提下重排序");
        System.out.println("2. 处理器重排序: CPU在执行指令时重排序");
        System.out.println("3. 内存系统重排序: 由于缓存导致的内存操作重排序");
        System.out.println();
    }

    /**
     * 演示可能的重排序场景
     * 注意：这个演示可能不会每次都重现，因为重排序是概率性的
     */
    public static void demonstratePossibleReordering() {
        System.out.println("\n========== 可能的重排序场景 ==========");
        
        int successCount = 0;
        int totalTests = 100000;
        
        for (int i = 0; i < totalTests; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            
            Thread thread1 = new Thread(() -> {
                a = 1;  // 操作1
                x = b;  // 操作2
            });
            
            Thread thread2 = new Thread(() -> {
                b = 1;  // 操作3
                y = a;  // 操作4
            });
            
            thread1.start();
            thread2.start();
            
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // 如果没有重排序，结果应该是: (x=0,y=1) 或 (x=1,y=0) 或 (x=1,y=1)
            // 如果发生重排序，可能出现: (x=0,y=0)
            if (x == 0 && y == 0) {
                successCount++;
            }
        }
        
        System.out.println("测试次数: " + totalTests);
        System.out.println("检测到重排序的次数: " + successCount);
        if (successCount > 0) {
            System.out.println("⚠️ 检测到指令重排序！");
            System.out.println("   说明: 在某些情况下，指令被重排序了");
        } else {
            System.out.println("未检测到重排序（可能因为JVM优化或测试环境）");
            System.out.println("注意: 重排序是概率性的，不代表不存在");
        }
    }

    /**
     * 演示单例模式中的重排序问题（双重检查锁定）
     */
    public static void demonstrateSingletonReordering() {
        System.out.println("\n========== 单例模式中的重排序问题 ==========");
        
        System.out.println("【错误的双重检查锁定实现】");
        System.out.println("public class Singleton {");
        System.out.println("    private static Singleton instance;");
        System.out.println("    ");
        System.out.println("    public static Singleton getInstance() {");
        System.out.println("        if (instance == null) {              // 第一次检查");
        System.out.println("            synchronized (Singleton.class) {");
        System.out.println("                if (instance == null) {      // 第二次检查");
        System.out.println("                    instance = new Singleton(); // 问题在这里！");
        System.out.println("                }");
        System.out.println("            }");
        System.out.println("        }");
        System.out.println("        return instance;");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("【问题分析】");
        System.out.println("instance = new Singleton() 包含三个步骤：");
        System.out.println("1. 分配内存空间");
        System.out.println("2. 初始化对象");
        System.out.println("3. 将引用指向内存地址");
        System.out.println();
        System.out.println("如果发生重排序，步骤2和3可能交换，导致：");
        System.out.println("- 线程A执行到步骤3，instance已指向内存地址，但对象未初始化");
        System.out.println("- 线程B看到instance != null，直接返回未初始化的对象");
        System.out.println();
        System.out.println("【解决方案】");
        System.out.println("1. 使用volatile: private static volatile Singleton instance;");
        System.out.println("2. 使用静态内部类（推荐）");
        System.out.println("3. 使用枚举（推荐）");
    }

    /**
     * 综合演示有序性问题
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 有序性问题综合演示 ==========");
        
        demonstrateInstructionReordering();
        demonstratePossibleReordering();
        demonstrateSingletonReordering();
    }
}






