package com.sherrylxf.jucstudy.jmm;

/**
 * JMM面试常见问题总结
 * 聚焦实习面试重点
 */
public class JMMInterviewSummary {

    /**
     * 打印JMM基础面试题
     */
    public static void printBasicQuestions() {
        System.out.println("\n========== JMM基础面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是JMM？】");
        System.out.println("A: Java内存模型(Java Memory Model)是一种规范，");
        System.out.println("   定义了Java程序中多线程访问共享内存时的行为规则。");
        System.out.println("   它屏蔽了不同硬件和操作系统的内存访问差异，");
        System.out.println("   让Java程序在各种平台上都能达到一致的内存访问效果。");
        System.out.println();
        
        System.out.println("【Q2: JMM的内存结构？】");
        System.out.println("A: JMM将内存分为主内存和工作内存：");
        System.out.println("   - 主内存: 所有线程共享，存储共享变量");
        System.out.println("   - 工作内存: 每个线程私有，存储该线程使用的变量的副本");
        System.out.println("   线程对变量的操作在工作内存中进行，然后同步到主内存。");
        System.out.println();
        
        System.out.println("【Q3: JMM的三大特性？】");
        System.out.println("A: 1. 可见性: 一个线程修改了共享变量，其他线程能立即看到");
        System.out.println("   2. 原子性: 一个或多个操作要么全部执行，要么全部不执行");
        System.out.println("   3. 有序性: 程序执行的顺序按照代码的先后顺序执行");
        System.out.println();
    }

    /**
     * 打印可见性相关面试题
     */
    public static void printVisibilityQuestions() {
        System.out.println("\n========== 可见性相关面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是可见性问题？】");
        System.out.println("A: 一个线程修改了共享变量的值，但其他线程看不到最新的值。");
        System.out.println("   原因: 每个线程都有自己的工作内存，线程对变量的修改");
        System.out.println("         可能只更新在工作内存中，没有及时刷新到主内存。");
        System.out.println();
        
        System.out.println("【Q2: 如何解决可见性问题？】");
        System.out.println("A: 1. volatile关键字: 保证变量的可见性");
        System.out.println("   2. synchronized: 保证可见性+原子性+有序性");
        System.out.println("   3. final关键字: 保证final变量的可见性");
        System.out.println("   4. Lock接口: 通过AQS实现可见性");
        System.out.println();
        
        System.out.println("【Q3: 为什么有时候不加volatile也能看到修改？】");
        System.out.println("A: 某些操作会触发内存同步（如System.out.println、");
        System.out.println("   Thread.sleep、synchronized等），但这依赖于JVM实现，");
        System.out.println("   不是保证！生产环境必须使用volatile或其他同步机制。");
        System.out.println();
    }

    /**
     * 打印原子性相关面试题
     */
    public static void printAtomicityQuestions() {
        System.out.println("\n========== 原子性相关面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: i++是原子操作吗？】");
        System.out.println("A: 不是！i++包含三个步骤：");
        System.out.println("   1. read: 读取i的值到工作内存");
        System.out.println("   2. modify: 在工作内存中执行+1操作");
        System.out.println("   3. write: 将结果写回主内存");
        System.out.println("   多线程环境下，这三个步骤可能被其他线程打断。");
        System.out.println();
        
        System.out.println("【Q2: volatile能保证原子性吗？】");
        System.out.println("A: 不能！volatile只能保证可见性和有序性，不能保证原子性。");
        System.out.println("   例如: volatile int count; count++; 仍然不是原子操作。");
        System.out.println("   如果需要原子性，应该使用synchronized或原子类。");
        System.out.println();
        
        System.out.println("【Q3: 如何保证原子性？】");
        System.out.println("A: 1. synchronized: 保证代码块的原子性");
        System.out.println("   2. Lock接口: 通过锁机制保证原子性");
        System.out.println("   3. 原子类: AtomicInteger、AtomicLong等（使用CAS实现）");
        System.out.println();
    }

    /**
     * 打印有序性相关面试题
     */
    public static void printOrderingQuestions() {
        System.out.println("\n========== 有序性相关面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是指令重排序？】");
        System.out.println("A: 为了提高性能，编译器和处理器可能会对指令进行重排序。");
        System.out.println("   重排序不会影响单线程程序的执行结果，");
        System.out.println("   但可能影响多线程程序的正确性。");
        System.out.println();
        
        System.out.println("【Q2: 为什么要进行指令重排序？】");
        System.out.println("A: 1. 提高CPU利用率: 避免CPU等待数据");
        System.out.println("   2. 提高指令级并行度: 充分利用CPU的多级流水线");
        System.out.println("   3. 优化编译器: 编译器可以优化代码执行顺序");
        System.out.println();
        
        System.out.println("【Q3: 如何防止指令重排序？】");
        System.out.println("A: 1. volatile: 禁止volatile变量前后的指令重排序");
        System.out.println("   2. synchronized: 保证代码块内的指令不会被重排序");
        System.out.println("   3. happens-before规则: 保证某些操作的顺序");
        System.out.println();
        
        System.out.println("【Q4: 单例模式中的双重检查锁定问题？】");
        System.out.println("A: instance = new Singleton() 包含三个步骤：");
        System.out.println("   1. 分配内存空间");
        System.out.println("   2. 初始化对象");
        System.out.println("   3. 将引用指向内存地址");
        System.out.println("   如果发生重排序，步骤2和3可能交换，导致返回未初始化的对象。");
        System.out.println("   解决: 使用volatile修饰instance变量。");
        System.out.println();
    }

    /**
     * 打印volatile相关面试题
     */
    public static void printVolatileQuestions() {
        System.out.println("\n========== volatile相关面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: volatile的作用？】");
        System.out.println("A: 1. 保证可见性: 对volatile变量的写会立即刷新到主内存，");
        System.out.println("                  读会从主内存读取最新值");
        System.out.println("   2. 禁止指令重排序: 通过内存屏障实现");
        System.out.println("   3. 不保证原子性: volatile不能保证复合操作的原子性");
        System.out.println();
        
        System.out.println("【Q2: volatile的实现原理？】");
        System.out.println("A: volatile通过内存屏障（Memory Barrier）实现：");
        System.out.println("   - LoadLoad屏障: 确保volatile读之前的所有普通读完成");
        System.out.println("   - StoreStore屏障: 确保volatile写之前的所有普通写完成");
        System.out.println("   - LoadStore屏障: 确保volatile读之后的所有普通写完成");
        System.out.println("   - StoreLoad屏障: 确保volatile写之后的所有普通读完成");
        System.out.println();
        
        System.out.println("【Q3: volatile的使用场景？】");
        System.out.println("A: 1. 状态标志: 用于标记线程是否应该停止");
        System.out.println("   2. 双重检查锁定: 单例模式中使用volatile防止重排序");
        System.out.println("   3. 独立观察: 定期发布观察结果");
        System.out.println("   不适用: 需要保证原子性的操作（如count++）");
        System.out.println();
        
        System.out.println("【Q4: volatile vs synchronized？】");
        System.out.println("A: volatile: 保证可见性和有序性，性能高，不阻塞");
        System.out.println("   synchronized: 保证可见性+原子性+有序性，性能较低，会阻塞");
        System.out.println("   选择: 如果只需要可见性用volatile，需要原子性用synchronized");
        System.out.println();
    }

    /**
     * 打印happens-before相关面试题
     */
    public static void printHappensBeforeQuestions() {
        System.out.println("\n========== happens-before相关面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 什么是happens-before？】");
        System.out.println("A: happens-before是JMM的核心概念，用于描述两个操作之间的可见性。");
        System.out.println("   如果操作A happens-before 操作B，那么A的结果对B可见。");
        System.out.println("   注意: happens-before不是时间顺序，而是内存可见性。");
        System.out.println();
        
        System.out.println("【Q2: happens-before的8大规则？】");
        System.out.println("A: 1. 程序顺序规则: 同一线程中前面的操作happens-before后面的操作");
        System.out.println("   2. 监视器锁规则: 解锁happens-before加锁");
        System.out.println("   3. volatile规则: volatile写happens-before volatile读");
        System.out.println("   4. 线程启动规则: start() happens-before run()");
        System.out.println("   5. 线程终止规则: 线程操作happens-before join()返回");
        System.out.println("   6. 线程中断规则: interrupt() happens-before isInterrupted()");
        System.out.println("   7. 对象终结规则: 构造函数happens-before finalize()");
        System.out.println("   8. 传递性: A happens-before B, B happens-before C => A happens-before C");
        System.out.println();
        
        System.out.println("【Q3: happens-before的作用？】");
        System.out.println("A: happens-before规则是JMM的核心，它定义了哪些操作");
        System.out.println("   之间的内存可见性是有保证的。如果两个操作之间没有");
        System.out.println("   happens-before关系，JVM可以任意重排序，可能导致可见性问题。");
        System.out.println();
    }

    /**
     * 打印综合面试题
     */
    public static void printComprehensiveQuestions() {
        System.out.println("\n========== 综合面试题 ==========");
        System.out.println();
        
        System.out.println("【Q1: 如何理解JMM？】");
        System.out.println("A: JMM是Java内存模型，定义了多线程访问共享内存的规则。");
        System.out.println("   它解决了可见性、原子性、有序性三大问题，");
        System.out.println("   通过happens-before规则保证内存可见性。");
        System.out.println();
        
        System.out.println("【Q2: 多线程编程的核心问题？】");
        System.out.println("A: 1. 可见性: 使用volatile、synchronized解决");
        System.out.println("   2. 原子性: 使用synchronized、Lock、原子类解决");
        System.out.println("   3. 有序性: 使用volatile、synchronized、happens-before解决");
        System.out.println();
        
        System.out.println("【Q3: 如何保证线程安全？】");
        System.out.println("A: 1. 不可变对象: final修饰");
        System.out.println("   2. 线程封闭: ThreadLocal");
        System.out.println("   3. 同步机制: synchronized、Lock");
        System.out.println("   4. 原子类: AtomicInteger等");
        System.out.println("   5. volatile: 保证可见性和有序性");
        System.out.println();
        
        System.out.println("【Q4: 单例模式的线程安全实现？】");
        System.out.println("A: 1. 饿汉式: 类加载时创建，天然线程安全");
        System.out.println("   2. 懒汉式+synchronized: 性能较低");
        System.out.println("   3. 双重检查锁定+volatile: 性能好，线程安全");
        System.out.println("   4. 静态内部类: 推荐，线程安全且性能好");
        System.out.println("   5. 枚举: 推荐，最简单且线程安全");
        System.out.println();
    }

    /**
     * 打印完整面试总结
     */
    public static void printFullSummary() {
        System.out.println("\n========== JMM面试问题完整总结 ==========");
        
        printBasicQuestions();
        printVisibilityQuestions();
        printAtomicityQuestions();
        printOrderingQuestions();
        printVolatileQuestions();
        printHappensBeforeQuestions();
        printComprehensiveQuestions();
    }
}


