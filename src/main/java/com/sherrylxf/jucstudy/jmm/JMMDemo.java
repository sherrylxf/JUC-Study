package com.sherrylxf.jucstudy.jmm;

/**
 * JMM综合演示类
 * 整合所有JMM相关的演示和面试总结
 */
public class JMMDemo {

    /**
     * 打印JMM基本概念
     */
    public static void printConcepts() {
        System.out.println("\n========== 第一部分：JMM基本概念 ==========");
        JMMBasicConcepts.printAllConcepts();
    }

    /**
     * 演示可见性问题
     */
    public static void demonstrateVisibility() {
        System.out.println("\n========== 第二部分：可见性问题演示 ==========");
        VisibilityDemo.demonstrateAll();
    }

    /**
     * 演示原子性问题
     */
    public static void demonstrateAtomicity() {
        System.out.println("\n========== 第三部分：原子性问题演示 ==========");
        AtomicityDemo.demonstrateAll();
    }

    /**
     * 演示有序性问题
     */
    public static void demonstrateOrdering() {
        System.out.println("\n========== 第四部分：有序性问题演示 ==========");
        OrderingDemo.demonstrateAll();
    }

    /**
     * 演示volatile关键字
     */
    public static void demonstrateVolatile() {
        System.out.println("\n========== 第五部分：volatile关键字详解 ==========");
        VolatileDemo.demonstrateAll();
    }

    /**
     * 演示happens-before规则
     */
    public static void demonstrateHappensBefore() {
        System.out.println("\n========== 第六部分：happens-before规则演示 ==========");
        HappensBeforeDemo.demonstrateAll();
    }

    /**
     * 打印面试问题总结
     */
    public static void printInterviewSummary() {
        System.out.println("\n========== 第七部分：JMM面试问题总结 ==========");
        JMMInterviewSummary.printFullSummary();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== JMM (Java Memory Model) 完整学习演示 ==========");
        
        // 先看理论
        printConcepts();
        
        // 再看实际演示
        demonstrateVisibility();
        demonstrateAtomicity();
        demonstrateOrdering();
        demonstrateVolatile();
        demonstrateHappensBefore();
        
        // 最后看面试总结
        printInterviewSummary();
        
        System.out.println("\n========== JMM学习演示完成 ==========");
    }
}


