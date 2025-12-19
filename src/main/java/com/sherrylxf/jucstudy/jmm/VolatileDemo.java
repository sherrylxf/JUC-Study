package com.sherrylxf.jucstudy.jmm;

/**
 * volatile关键字深入讲解
 * 面试重点：volatile的作用、原理、使用场景
 */
public class VolatileDemo {

    /**
     * 打印volatile的基本概念
     */
    public static void printVolatileConcepts() {
        System.out.println("\n========== volatile关键字详解 ==========");
        System.out.println();
        
        System.out.println("【volatile的作用】");
        System.out.println("1. 保证可见性");
        System.out.println("   - 对volatile变量的写操作会立即刷新到主内存");
        System.out.println("   - 对volatile变量的读操作会从主内存读取最新值");
        System.out.println();
        System.out.println("2. 禁止指令重排序");
        System.out.println("   - volatile变量前后的代码不会被重排序");
        System.out.println("   - 通过内存屏障实现");
        System.out.println();
        System.out.println("3. 不保证原子性");
        System.out.println("   - volatile不能保证复合操作的原子性");
        System.out.println("   - 例如: count++ 仍然不是原子操作");
        System.out.println();
    }

    /**
     * 演示volatile的内存语义
     */
    public static void demonstrateVolatileMemorySemantics() {
        System.out.println("\n========== volatile的内存语义 ==========");
        System.out.println();
        
        System.out.println("【写操作的内存语义】");
        System.out.println("当写一个volatile变量时，JMM会把该线程对应的");
        System.out.println("工作内存中的共享变量值刷新到主内存。");
        System.out.println();
        
        System.out.println("【读操作的内存语义】");
        System.out.println("当读一个volatile变量时，JMM会把该线程对应的");
        System.out.println("工作内存置为无效，线程接下来将从主内存读取共享变量。");
        System.out.println();
        
        System.out.println("【内存屏障】");
        System.out.println("volatile通过内存屏障（Memory Barrier）实现：");
        System.out.println("1. LoadLoad屏障: 确保volatile读之前的所有普通读完成");
        System.out.println("2. StoreStore屏障: 确保volatile写之前的所有普通写完成");
        System.out.println("3. LoadStore屏障: 确保volatile读之后的所有普通写完成");
        System.out.println("4. StoreLoad屏障: 确保volatile写之后的所有普通读完成");
        System.out.println();
    }

    /**
     * 演示volatile的使用场景
     */
    public static void demonstrateVolatileUseCases() {
        System.out.println("\n========== volatile的使用场景 ==========");
        System.out.println();
        
        System.out.println("【适用场景】");
        System.out.println("1. 状态标志");
        System.out.println("   - 用于标记线程是否应该停止");
        System.out.println("   - 例如: volatile boolean stop = false;");
        System.out.println();
        System.out.println("2. 双重检查锁定（DCL）");
        System.out.println("   - 单例模式中使用volatile防止重排序");
        System.out.println();
        System.out.println("3. 独立观察");
        System.out.println("   - 定期发布观察结果供程序其他部分使用");
        System.out.println();
        System.out.println("4. volatile bean模式");
        System.out.println("   - 对象的所有字段都是volatile");
        System.out.println();
        
        System.out.println("【不适用场景】");
        System.out.println("1. 需要保证原子性的操作");
        System.out.println("   - count++ 等复合操作");
        System.out.println("   - 应该使用synchronized或原子类");
        System.out.println();
        System.out.println("2. 需要互斥访问的场景");
        System.out.println("   - volatile不能提供互斥性");
        System.out.println("   - 应该使用synchronized或Lock");
        System.out.println();
    }

    /**
     * 演示volatile vs synchronized
     */
    public static void demonstrateVolatileVsSynchronized() {
        System.out.println("\n========== volatile vs synchronized ==========");
        System.out.println();
        
        System.out.println("【对比表格】");
        System.out.println("┌─────────────────┬──────────────┬──────────────┐");
        System.out.println("│      特性        │   volatile   │ synchronized │");
        System.out.println("├─────────────────┼──────────────┼──────────────┤");
        System.out.println("│   可见性        │      ✓       │      ✓       │");
        System.out.println("│   原子性        │      ✗       │      ✓       │");
        System.out.println("│   有序性        │      ✓       │      ✓       │");
        System.out.println("│   互斥性        │      ✗       │      ✓       │");
        System.out.println("│   性能          │     高        │     低        │");
        System.out.println("│   阻塞          │     否        │     是        │");
        System.out.println("└─────────────────┴──────────────┴──────────────┘");
        System.out.println();
        
        System.out.println("【选择建议】");
        System.out.println("- 如果只需要保证可见性，使用volatile");
        System.out.println("- 如果需要保证原子性或互斥性，使用synchronized");
        System.out.println("- 如果既要性能又要原子性，考虑使用原子类");
        System.out.println();
    }

    /**
     * 演示volatile的happens-before关系
     */
    public static void demonstrateVolatileHappensBefore() {
        System.out.println("\n========== volatile的happens-before关系 ==========");
        System.out.println();
        
        System.out.println("【volatile规则】");
        System.out.println("对一个volatile变量的写操作，happens-before于");
        System.out.println("任意后续对这个volatile变量的读操作。");
        System.out.println();
        
        System.out.println("【示例代码】");
        System.out.println("// 线程A");
        System.out.println("x = 1;           // 操作1");
        System.out.println("volatileFlag = true;  // 操作2");
        System.out.println();
        System.out.println("// 线程B");
        System.out.println("if (volatileFlag) {   // 操作3");
        System.out.println("    int y = x;        // 操作4");
        System.out.println("}");
        System.out.println();
        System.out.println("【happens-before关系】");
        System.out.println("操作1 happens-before 操作2 (程序顺序规则)");
        System.out.println("操作2 happens-before 操作3 (volatile规则)");
        System.out.println("操作3 happens-before 操作4 (程序顺序规则)");
        System.out.println("因此: 操作1 happens-before 操作4");
        System.out.println("线程B能看到x=1");
        System.out.println();
    }

    /**
     * 综合演示volatile
     */
    public static void demonstrateAll() {
        System.out.println("\n========== volatile关键字综合演示 ==========");
        
        printVolatileConcepts();
        demonstrateVolatileMemorySemantics();
        demonstrateVolatileUseCases();
        demonstrateVolatileVsSynchronized();
        demonstrateVolatileHappensBefore();
    }
}

