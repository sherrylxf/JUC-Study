package com.sherrylxf.jucstudy.threadLocal;

/**
 * ThreadLocal知识点总结
 * 聚焦面试重点
 */
public class ThreadLocalSummary {

    /**
     * 打印ThreadLocal基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== ThreadLocal基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是ThreadLocal】");
        System.out.println("ThreadLocal是Java提供的线程本地存储机制，");
        System.out.println("可以为每个线程提供独立的变量副本，");
        System.out.println("实现线程间的数据隔离。");
        System.out.println();
        
        System.out.println("【ThreadLocal的作用】");
        System.out.println("1. 线程隔离: 每个线程都有自己独立的变量副本");
        System.out.println("2. 避免线程安全问题: 不需要同步，天然线程安全");
        System.out.println("3. 传递上下文信息: 如用户信息、事务信息等");
        System.out.println();
        
        System.out.println("【使用场景】");
        System.out.println("1. SimpleDateFormat等非线程安全类的线程安全使用");
        System.out.println("2. 数据库连接、Session管理等");
        System.out.println("3. 传递上下文信息（如用户ID、事务ID等）");
        System.out.println("4. 避免参数传递的复杂性");
        System.out.println();
    }

    /**
     * 打印ThreadLocal原理
     */
    public static void printPrinciple() {
        System.out.println("\n========== ThreadLocal原理 ==========");
        System.out.println();
        
        System.out.println("【存储结构】");
        System.out.println("ThreadLocal内部使用ThreadLocalMap存储数据，");
        System.out.println("ThreadLocalMap是Thread的一个成员变量。");
        System.out.println();
        System.out.println("Thread -> ThreadLocalMap -> Entry[]");
        System.out.println("                      -> Entry(ThreadLocal, Value)");
        System.out.println();
        
        System.out.println("【get()方法流程】");
        System.out.println("1. 获取当前线程");
        System.out.println("2. 获取当前线程的ThreadLocalMap");
        System.out.println("3. 以ThreadLocal为key，从Map中获取值");
        System.out.println();
        
        System.out.println("【set()方法流程】");
        System.out.println("1. 获取当前线程");
        System.out.println("2. 获取当前线程的ThreadLocalMap");
        System.out.println("3. 如果Map不存在，创建Map");
        System.out.println("4. 以ThreadLocal为key，存储值");
        System.out.println();
        
        System.out.println("【内存泄漏问题】");
        System.out.println("ThreadLocalMap的Entry使用弱引用(WeakReference)存储key，");
        System.out.println("但value是强引用。如果ThreadLocal对象被回收，");
        System.out.println("但线程仍然存在，Entry中的value无法被回收。");
        System.out.println();
        System.out.println("【解决方案】");
        System.out.println("使用完ThreadLocal后，必须调用remove()方法清除。");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== ThreadLocal面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: ThreadLocal的作用？】");
        System.out.println("A: 为每个线程提供独立的变量副本，实现线程间的数据隔离，");
        System.out.println("   避免线程安全问题。");
        System.out.println();
        
        System.out.println("【Q2: ThreadLocal的实现原理？】");
        System.out.println("A: ThreadLocal内部使用ThreadLocalMap存储数据，");
        System.out.println("   ThreadLocalMap是Thread的成员变量。");
        System.out.println("   每个线程都有自己的ThreadLocalMap，");
        System.out.println("   以ThreadLocal为key存储值。");
        System.out.println();
        
        System.out.println("【Q3: ThreadLocal的内存泄漏问题？】");
        System.out.println("A: ThreadLocalMap的Entry使用弱引用存储key，");
        System.out.println("   但value是强引用。如果ThreadLocal被回收，");
        System.out.println("   但线程仍然存在，value无法被回收，导致内存泄漏。");
        System.out.println("   解决: 使用完必须调用remove()方法。");
        System.out.println();
        
        System.out.println("【Q4: InheritableThreadLocal的作用？】");
        System.out.println("A: 可以让子线程继承父线程的ThreadLocal值。");
        System.out.println("   普通ThreadLocal子线程无法访问父线程的值。");
        System.out.println();
        
        System.out.println("【Q5: ThreadLocal的使用场景？】");
        System.out.println("A: 1. SimpleDateFormat等非线程安全类的线程安全使用");
        System.out.println("   2. 数据库连接、Session管理");
        System.out.println("   3. 传递上下文信息");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printBasicConcepts();
        printPrinciple();
        printInterviewQuestions();
    }
}






