package com.sherrylxf.jucstudy.threadPool;

/**
 * 线程池综合演示类
 * 整合所有线程池相关的演示
 */
public class ThreadPoolDemo {

    /**
     * 打印线程池知识点总结
     */
    public static void printSummary() {
        System.out.println("\n========== 第一部分：线程池知识点总结 ==========");
        ThreadPoolSummary.printFullSummary();
    }

    /**
     * 演示线程池基本概念
     */
    public static void demonstrateBasic() {
        System.out.println("\n========== 第二部分：线程池基本概念演示 ==========");
        ThreadPoolBasicDemo.demonstrateAllBasic();
    }

    /**
     * 演示ThreadPoolExecutor详细配置
     */
    public static void demonstrateThreadPoolExecutor() {
        System.out.println("\n========== 第三部分：ThreadPoolExecutor详细配置演示 ==========");
        ThreadPoolExecutorDemo.demonstrateAll();
    }

    /**
     * 演示各种线程池类型
     */
    public static void demonstrateTypes() {
        System.out.println("\n========== 第四部分：各种线程池类型演示 ==========");
        ThreadPoolTypesDemo.demonstrateAllTypes();
    }

    /**
     * 演示任务提交和执行
     */
    public static void demonstrateTasks() {
        System.out.println("\n========== 第五部分：任务提交和执行演示 ==========");
        ThreadPoolTaskDemo.demonstrateAllTasks();
    }

    /**
     * 演示线程池关闭
     */
    public static void demonstrateShutdown() {
        System.out.println("\n========== 第六部分：线程池关闭演示 ==========");
        ThreadPoolShutdownDemo.demonstrateAllShutdown();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 线程池完整学习演示 ==========");
        
        // 先看理论总结
        printSummary();
        
        // 然后看实际演示
        demonstrateBasic();
        demonstrateThreadPoolExecutor();
        demonstrateTypes();
        demonstrateTasks();
        demonstrateShutdown();
        
        System.out.println("\n========== 线程池学习演示完成 ==========");
    }
}

