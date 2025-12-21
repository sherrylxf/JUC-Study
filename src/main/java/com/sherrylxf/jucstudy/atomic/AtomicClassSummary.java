package com.sherrylxf.jucstudy.atomic;

/**
 * 原子类知识点总结
 * 聚焦面试重点
 */
public class AtomicClassSummary {

    /**
     * 打印原子类基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== 原子类基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是原子类】");
        System.out.println("原子类是java.util.concurrent.atomic包下的类，");
        System.out.println("提供原子操作，保证线程安全。");
        System.out.println();
        
        System.out.println("【原子类的优势】");
        System.out.println("1. 无锁编程: 使用CAS实现，性能高");
        System.out.println("2. 线程安全: 保证原子性");
        System.out.println("3. 避免阻塞: 不会造成线程阻塞");
        System.out.println();
        
        System.out.println("【常用原子类】");
        System.out.println("1. AtomicInteger: 整型原子类");
        System.out.println("2. AtomicLong: 长整型原子类");
        System.out.println("3. AtomicBoolean: 布尔型原子类");
        System.out.println("4. AtomicReference: 引用类型原子类");
        System.out.println("5. AtomicStampedReference: 带版本号的引用原子类");
        System.out.println("6. AtomicIntegerArray: 整型数组原子类");
        System.out.println();
    }

    /**
     * 打印CAS原理
     */
    public static void printCASPrinciple() {
        System.out.println("\n========== CAS原理 ==========");
        System.out.println();
        
        System.out.println("【CAS (Compare-And-Swap)】");
        System.out.println("CAS是一种无锁算法，包含三个操作数：");
        System.out.println("1. 内存位置 (V)");
        System.out.println("2. 预期原值 (A)");
        System.out.println("3. 新值 (B)");
        System.out.println();
        System.out.println("只有当V的值等于A时，才会将V的值更新为B，否则不执行任何操作。");
        System.out.println();
        
        System.out.println("【CAS实现】");
        System.out.println("底层使用Unsafe类的CAS操作，由CPU的原子指令保证。");
        System.out.println("在x86架构上，使用cmpxchg指令实现。");
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
     * 打印ABA问题
     */
    public static void printABAProblem() {
        System.out.println("\n========== ABA问题 ==========");
        System.out.println();
        
        System.out.println("【ABA问题描述】");
        System.out.println("线程1读取值A，准备修改为B");
        System.out.println("线程2将A改为B，然后又改回A");
        System.out.println("线程1执行CAS，发现值还是A，认为没有被修改过，执行成功");
        System.out.println("但实际上值已经被修改过了！");
        System.out.println();
        
        System.out.println("【解决方案】");
        System.out.println("1. AtomicStampedReference: 使用版本号标记每次修改");
        System.out.println("2. AtomicMarkableReference: 使用标记位标记是否被修改");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== 原子类面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是CAS？】");
        System.out.println("A: CAS (Compare-And-Swap) 是一种无锁算法，");
        System.out.println("   比较内存位置的值和预期值，如果相等则更新为新值。");
        System.out.println();
        
        System.out.println("【Q2: CAS的优缺点？】");
        System.out.println("A: 优点: 无锁，性能高，避免线程阻塞");
        System.out.println("   缺点: ABA问题，循环时间长开销大，只能保证一个变量的原子性");
        System.out.println();
        
        System.out.println("【Q3: 什么是ABA问题？如何解决？】");
        System.out.println("A: ABA问题是指值从A变为B再变回A，CAS认为没有被修改。");
        System.out.println("   解决: 使用AtomicStampedReference添加版本号。");
        System.out.println();
        
        System.out.println("【Q4: 原子类和synchronized的区别？】");
        System.out.println("A: 原子类: 使用CAS实现，无锁，性能高，但只能保证单个操作原子性");
        System.out.println("   synchronized: 使用锁实现，会阻塞，性能较低，但功能强大");
        System.out.println();
        
        System.out.println("【Q5: AtomicInteger的incrementAndGet()实现原理？】");
        System.out.println("A: 使用CAS循环，不断尝试将值+1，直到成功为止。");
        System.out.println("   底层调用Unsafe类的getAndAddInt方法。");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printBasicConcepts();
        printCASPrinciple();
        printABAProblem();
        printInterviewQuestions();
    }
}






