package com.sherrylxf.jucstudy.threadMethod;

/**
 * 多线程方法综合演示类
 * 整合所有方法的演示
 */
public class ThreadMethodDemo {

    /**
     * 演示所有Thread类基本方法
     */
    public static void demonstrateBasicMethods() {
        System.out.println("\n========== 第一部分：Thread类基本方法 ==========");
        ThreadBasicMethods.demonstrateAllBasicMethods();
    }

    /**
     * 演示所有线程控制方法
     */
    public static void demonstrateControlMethods() {
        System.out.println("\n========== 第二部分：线程控制方法 ==========");
        ThreadControlMethods.demonstrateAllControlMethods();
    }

    /**
     * 演示Object类的wait/notify方法
     */
    public static void demonstrateWaitNotifyMethods() {
        System.out.println("\n========== 第三部分：Object类wait/notify方法 ==========");
        ObjectWaitNotifyMethods.demonstrateAllWaitNotifyMethods();
    }

    /**
     * 打印方法总结
     */
    public static void printSummary() {
        System.out.println("\n========== 第四部分：方法总结 ==========");
        ThreadMethodSummary.printFullSummary();
    }

    /**
     * 综合演示所有方法
     */
    public static void demonstrateAllMethods() {
        System.out.println("\n========== 多线程方法完整演示 ==========");
        
        // 打印总结（先看理论）
        printSummary();
        
        // 演示基本方法
        demonstrateBasicMethods();
        
        // 演示控制方法
        demonstrateControlMethods();
        
        // 演示wait/notify方法
        demonstrateWaitNotifyMethods();
        
        System.out.println("\n========== 演示完成 ==========");
    }
}








