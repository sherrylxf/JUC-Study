package com.sherrylxf.jucstudy.jmm;

/**
 * JMM (Java Memory Model) 基本概念
 * 聚焦面试常见知识点
 */
public class JMMBasicConcepts {

    /**
     * 打印JMM基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== JMM (Java Memory Model) 基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是JMM】");
        System.out.println("JMM是Java内存模型的简称，是一种规范，定义了Java程序中");
        System.out.println("多线程访问共享内存时的行为规则。");
        System.out.println();
        
        System.out.println("【JMM的作用】");
        System.out.println("1. 屏蔽不同硬件和操作系统的内存访问差异");
        System.out.println("2. 让Java程序在各种平台上都能达到一致的内存访问效果");
        System.out.println("3. 保证多线程程序的正确性");
        System.out.println();
        
        System.out.println("【JMM的内存结构】");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         主内存 (Main Memory)         │");
        System.out.println("│  共享变量存储在主内存中              │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.println("              ↕ 交互");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│       工作内存 (Working Memory)     │");
        System.out.println("│  每个线程都有自己的工作内存          │");
        System.out.println("│  线程对变量的操作在工作内存中进行    │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.println();
        
        System.out.println("【内存交互的8种操作】");
        System.out.println("1. lock (锁定)    : 作用于主内存，标识变量为线程独占");
        System.out.println("2. unlock (解锁)  : 作用于主内存，释放锁定状态");
        System.out.println("3. read (读取)    : 作用于主内存，将变量值传输到工作内存");
        System.out.println("4. load (载入)    : 作用于工作内存，将read得到的值放入工作内存变量副本");
        System.out.println("5. use (使用)     : 作用于工作内存，将变量值传递给执行引擎");
        System.out.println("6. assign (赋值)  : 作用于工作内存，将执行引擎得到的值赋给变量");
        System.out.println("7. store (存储)   : 作用于工作内存，将变量值传输到主内存");
        System.out.println("8. write (写入)    : 作用于主内存，将store得到的值放入主内存变量");
        System.out.println();
        
        System.out.println("【操作规则】");
        System.out.println("- read和load、store和write必须成对出现");
        System.out.println("- 不允许read/load、store/write单独出现");
        System.out.println("- 不允许一个线程丢弃最近的assign操作");
        System.out.println("- 不允许一个线程无原因地（没有assign操作）把数据从工作内存同步回主内存");
        System.out.println();
    }

    /**
     * 打印JMM三大特性
     */
    public static void printThreeCharacteristics() {
        System.out.println("\n========== JMM三大特性 ==========");
        System.out.println();
        
        System.out.println("【1. 可见性 (Visibility)】");
        System.out.println("定义: 一个线程修改了共享变量的值，其他线程能够立即看到修改后的值");
        System.out.println("问题: 由于每个线程都有自己的工作内存，线程对变量的修改");
        System.out.println("      可能只更新在工作内存中，没有及时刷新到主内存，");
        System.out.println("      导致其他线程看不到最新的值");
        System.out.println("解决: volatile、synchronized、final");
        System.out.println();
        
        System.out.println("【2. 原子性 (Atomicity)】");
        System.out.println("定义: 一个或多个操作要么全部执行成功，要么全部不执行，");
        System.out.println("      不会被打断");
        System.out.println("问题: 多个线程同时执行非原子操作时，可能产生竞态条件");
        System.out.println("示例: i++ 不是原子操作，包含 read -> modify -> write 三步");
        System.out.println("解决: synchronized、Lock、原子类（AtomicInteger等）");
        System.out.println();
        
        System.out.println("【3. 有序性 (Ordering)】");
        System.out.println("定义: 程序执行的顺序按照代码的先后顺序执行");
        System.out.println("问题: 为了优化性能，编译器和处理器可能会对指令进行重排序");
        System.out.println("      （指令重排序不会影响单线程执行结果，但可能影响多线程）");
        System.out.println("解决: volatile、synchronized、happens-before规则");
        System.out.println();
        
        System.out.println("【面试重点】");
        System.out.println("这三个特性是多线程编程的核心问题，面试中经常问到：");
        System.out.println("- 什么是可见性？如何解决？");
        System.out.println("- 什么是原子性？i++是原子操作吗？");
        System.out.println("- 什么是指令重排序？为什么要重排序？");
        System.out.println();
    }

    /**
     * 打印完整基本概念
     */
    public static void printAllConcepts() {
        printBasicConcepts();
        printThreeCharacteristics();
    }
}






