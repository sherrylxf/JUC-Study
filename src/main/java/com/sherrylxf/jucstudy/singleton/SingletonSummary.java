package com.sherrylxf.jucstudy.singleton;

/**
 * 单例模式知识点总结
 * 聚焦面试重点
 */
public class SingletonSummary {

    /**
     * 打印单例模式基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== 单例模式基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是单例模式】");
        System.out.println("单例模式确保一个类只有一个实例，");
        System.out.println("并提供一个全局访问点。");
        System.out.println();
        
        System.out.println("【单例模式的特点】");
        System.out.println("1. 只有一个实例");
        System.out.println("2. 必须自己创建自己的实例");
        System.out.println("3. 必须给其他对象提供这一实例");
        System.out.println();
        
        System.out.println("【使用场景】");
        System.out.println("1. 需要频繁创建和销毁的对象");
        System.out.println("2. 创建对象耗时过多或耗资源过多");
        System.out.println("3. 工具类对象");
        System.out.println("4. 频繁访问数据库或文件的对象");
        System.out.println();
    }

    /**
     * 打印各种实现方式
     */
    public static void printImplementations() {
        System.out.println("\n========== 单例模式实现方式 ==========");
        System.out.println();
        
        System.out.println("【1. 饿汉式】");
        System.out.println("优点: 简单，线程安全，类加载时创建");
        System.out.println("缺点: 如果不需要使用，也会创建实例，浪费内存");
        System.out.println();
        
        System.out.println("【2. 懒汉式（不安全）】");
        System.out.println("优点: 延迟加载");
        System.out.println("缺点: 线程不安全，可能创建多个实例");
        System.out.println();
        
        System.out.println("【3. 懒汉式 + synchronized】");
        System.out.println("优点: 线程安全，延迟加载");
        System.out.println("缺点: 每次获取实例都要加锁，性能低");
        System.out.println();
        
        System.out.println("【4. 双重检查锁定（DCL）】");
        System.out.println("优点: 线程安全，延迟加载，性能好（只有第一次需要加锁）");
        System.out.println("缺点: 代码较复杂，必须使用volatile防止指令重排序");
        System.out.println("注意: 不使用volatile可能导致返回未初始化的对象");
        System.out.println();
        
        System.out.println("【5. 静态内部类（推荐）】");
        System.out.println("优点: 线程安全，延迟加载，性能好，代码简洁");
        System.out.println("原理: 静态内部类只有在被调用时才会加载，类加载是线程安全的");
        System.out.println();
        
        System.out.println("【6. 枚举（最推荐）】");
        System.out.println("优点: 线程安全，防止反射攻击，防止反序列化创建新实例，代码最简洁");
        System.out.println("缺点: 不是懒加载");
        System.out.println();
    }

    /**
     * 打印DCL问题详解
     */
    public static void printDCLProblem() {
        System.out.println("\n========== 双重检查锁定（DCL）问题详解 ==========");
        System.out.println();
        
        System.out.println("【问题代码】");
        System.out.println("private static Singleton instance; // 没有volatile");
        System.out.println("public static Singleton getInstance() {");
        System.out.println("    if (instance == null) {");
        System.out.println("        synchronized (Singleton.class) {");
        System.out.println("            if (instance == null) {");
        System.out.println("                instance = new Singleton(); // 问题在这里");
        System.out.println("            }");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println("    return instance;");
        System.out.println("}");
        System.out.println();
        
        System.out.println("【问题分析】");
        System.out.println("instance = new Singleton() 包含三个步骤：");
        System.out.println("1. 分配内存空间");
        System.out.println("2. 初始化对象");
        System.out.println("3. 将引用指向内存地址");
        System.out.println();
        System.out.println("如果发生指令重排序，步骤2和3可能交换：");
        System.out.println("- 线程A执行到步骤3，instance已指向内存地址，但对象未初始化");
        System.out.println("- 线程B看到instance != null，直接返回未初始化的对象");
        System.out.println();
        
        System.out.println("【解决方案】");
        System.out.println("使用volatile修饰instance变量：");
        System.out.println("private static volatile Singleton instance;");
        System.out.println("volatile可以禁止指令重排序，保证对象完全初始化后才返回引用");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== 单例模式面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 单例模式有哪些实现方式？】");
        System.out.println("A: 1. 饿汉式 2. 懒汉式 3. 懒汉式+synchronized");
        System.out.println("   4. 双重检查锁定 5. 静态内部类 6. 枚举");
        System.out.println();
        
        System.out.println("【Q2: 双重检查锁定为什么要使用volatile？】");
        System.out.println("A: 防止指令重排序。instance = new Singleton()包含三个步骤，");
        System.out.println("   如果发生重排序，可能返回未初始化的对象。");
        System.out.println("   volatile可以禁止重排序，保证对象完全初始化。");
        System.out.println();
        
        System.out.println("【Q3: 哪种实现方式最好？】");
        System.out.println("A: 如果需要延迟加载: 静态内部类（最推荐）");
        System.out.println("   如果不需要延迟加载: 枚举（最推荐）");
        System.out.println();
        
        System.out.println("【Q4: 如何防止反射攻击和反序列化创建新实例？】");
        System.out.println("A: 使用枚举实现单例，枚举天然防止反射和反序列化攻击。");
        System.out.println();
        
        System.out.println("【Q5: 单例模式的优缺点？】");
        System.out.println("A: 优点: 节省内存，提高性能，避免重复创建");
        System.out.println("   缺点: 难以扩展，违背单一职责原则，线程安全问题需要处理");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printBasicConcepts();
        printImplementations();
        printDCLProblem();
        printInterviewQuestions();
    }
}






