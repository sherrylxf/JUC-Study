package com.sherrylxf.jucstudy.advanced;

import com.sherrylxf.jucstudy.atomic.AtomicClassDemo;
import com.sherrylxf.jucstudy.atomic.AtomicClassSummary;
import com.sherrylxf.jucstudy.singleton.SingletonDemo;
import com.sherrylxf.jucstudy.singleton.SingletonSummary;
import com.sherrylxf.jucstudy.threadLocal.ThreadLocalDemo;
import com.sherrylxf.jucstudy.threadLocal.ThreadLocalSummary;

/**
 * 高级特性综合演示类
 * 整合ThreadLocal、原子类、单例模式
 */
public class AdvancedDemo {

    /**
     * 演示ThreadLocal
     */
    public static void demonstrateThreadLocal() {
        System.out.println("\n========== 第一部分：ThreadLocal演示 ==========");
        ThreadLocalSummary.printFullSummary();
        ThreadLocalDemo.demonstrateAll();
    }

    /**
     * 演示原子类
     */
    public static void demonstrateAtomicClasses() {
        System.out.println("\n========== 第二部分：原子类演示 ==========");
        AtomicClassSummary.printFullSummary();
        AtomicClassDemo.demonstrateAll();
    }

    /**
     * 演示单例模式
     */
    public static void demonstrateSingleton() {
        System.out.println("\n========== 第三部分：单例模式演示 ==========");
        SingletonSummary.printFullSummary();
        SingletonDemo.demonstrateAll();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== ThreadLocal、原子类、单例模式完整学习演示 ==========");
        
        demonstrateThreadLocal();
        demonstrateAtomicClasses();
        demonstrateSingleton();
        
        System.out.println("\n========== 所有演示完成 ==========");
    }
}






