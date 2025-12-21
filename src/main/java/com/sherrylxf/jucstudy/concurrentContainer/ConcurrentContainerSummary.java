package com.sherrylxf.jucstudy.concurrentContainer;

/**
 * Java并发容器知识点总结
 * 聚焦面试重点
 */
public class ConcurrentContainerSummary {

    /**
     * 打印并发容器基本概念
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== 并发容器基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是并发容器】");
        System.out.println("并发容器是Java并发包(java.util.concurrent)中提供的线程安全的集合类，");
        System.out.println("用于在多线程环境下安全地操作集合，无需额外的同步机制。");
        System.out.println();
        
        System.out.println("【传统集合的问题】");
        System.out.println("1. HashMap/HashSet: 非线程安全，多线程环境下可能出现数据不一致");
        System.out.println("2. ArrayList: 非线程安全，可能出现数据丢失或异常");
        System.out.println("3. Vector/Hashtable: 线程安全但性能差，使用synchronized实现");
        System.out.println();
        
        System.out.println("【并发容器的优势】");
        System.out.println("1. 线程安全: 无需额外的同步机制");
        System.out.println("2. 性能优化: 使用更高效的并发算法（如分段锁、CAS等）");
        System.out.println("3. 功能丰富: 提供阻塞操作、原子操作等高级功能");
        System.out.println();
    }

    /**
     * 打印ConcurrentHashMap知识点
     */
    public static void printConcurrentHashMapSummary() {
        System.out.println("\n========== ConcurrentHashMap知识点 ==========");
        System.out.println();
        
        System.out.println("【特性】");
        System.out.println("1. 线程安全的HashMap实现");
        System.out.println("2. 支持高并发的读写操作");
        System.out.println("3. 不允许null键和null值");
        System.out.println();
        
        System.out.println("【实现原理 - JDK 1.7】");
        System.out.println("- 使用分段锁（Segment）");
        System.out.println("- 将数据分成多个段，每个段独立加锁");
        System.out.println("- 默认16个段，支持并发度为16");
        System.out.println();
        
        System.out.println("【实现原理 - JDK 1.8+】");
        System.out.println("- 使用CAS + synchronized");
        System.out.println("- 取消了分段锁，改用Node数组 + 链表/红黑树");
        System.out.println("- 只有在冲突时才加锁，粒度更细，性能更好");
        System.out.println("- 扩容时支持多线程并发扩容");
        System.out.println();
        
        System.out.println("【常用方法】");
        System.out.println("1. put(key, value): 插入键值对");
        System.out.println("2. get(key): 获取值");
        System.out.println("3. remove(key): 删除键值对");
        System.out.println("4. computeIfAbsent(key, function): 如果不存在则计算");
        System.out.println("5. merge(key, value, remappingFunction): 合并操作");
        System.out.println();
        
        System.out.println("【与HashMap的区别】");
        System.out.println("1. 线程安全性: ConcurrentHashMap线程安全，HashMap非线程安全");
        System.out.println("2. null值: ConcurrentHashMap不允许null，HashMap允许一个null键和多个null值");
        System.out.println("3. 性能: 高并发场景下ConcurrentHashMap性能更好");
        System.out.println();
    }

    /**
     * 打印CopyOnWrite容器知识点
     */
    public static void printCopyOnWriteSummary() {
        System.out.println("\n========== CopyOnWrite容器知识点 ==========");
        System.out.println();
        
        System.out.println("【包括的类】");
        System.out.println("1. CopyOnWriteArrayList");
        System.out.println("2. CopyOnWriteArraySet");
        System.out.println();
        
        System.out.println("【Copy-On-Write原理】");
        System.out.println("1. 写操作时，先复制整个数组，在新数组上进行修改");
        System.out.println("2. 修改完成后，将新数组的引用替换旧数组");
        System.out.println("3. 读操作直接读取数组，无需加锁");
        System.out.println();
        
        System.out.println("【适用场景】");
        System.out.println("1. 读多写少的场景");
        System.out.println("2. 读操作性能要求高");
        System.out.println("3. 能容忍短暂的数据不一致");
        System.out.println();
        
        System.out.println("【优缺点】");
        System.out.println("优点:");
        System.out.println("  - 读操作无需加锁，性能高");
        System.out.println("  - 读写不互相阻塞");
        System.out.println("缺点:");
        System.out.println("  - 写操作需要复制整个数组，内存开销大");
        System.out.println("  - 不适合写操作频繁的场景");
        System.out.println("  - 数据不是实时一致的（最终一致性）");
        System.out.println();
    }

    /**
     * 打印BlockingQueue知识点
     */
    public static void printBlockingQueueSummary() {
        System.out.println("\n========== BlockingQueue知识点 ==========");
        System.out.println();
        
        System.out.println("【什么是BlockingQueue】");
        System.out.println("BlockingQueue是一个支持阻塞操作的队列接口，");
        System.out.println("当队列满时，插入操作会阻塞；当队列空时，取出操作会阻塞。");
        System.out.println();
        
        System.out.println("【常用实现类】");
        System.out.println("1. ArrayBlockingQueue: 有界数组队列，需要指定容量");
        System.out.println("2. LinkedBlockingQueue: 链表队列，可以是有界或无界");
        System.out.println("3. PriorityBlockingQueue: 优先级队列，无界");
        System.out.println("4. DelayQueue: 延迟队列，元素有延迟时间");
        System.out.println("5. SynchronousQueue: 同步队列，不存储元素");
        System.out.println("6. LinkedTransferQueue: 链表传输队列");
        System.out.println();
        
        System.out.println("【常用方法】");
        System.out.println("1. put(e): 插入元素，队列满时阻塞");
        System.out.println("2. take(): 取出元素，队列空时阻塞");
        System.out.println("3. offer(e): 插入元素，队列满时返回false");
        System.out.println("4. poll(): 取出元素，队列空时返回null");
        System.out.println("5. offer(e, timeout): 超时插入");
        System.out.println("6. poll(timeout): 超时取出");
        System.out.println();
        
        System.out.println("【使用场景】");
        System.out.println("1. 生产者-消费者模式");
        System.out.println("2. 线程池的工作队列");
        System.out.println("3. 任务调度和缓冲");
        System.out.println();
    }

    /**
     * 打印ConcurrentLinkedQueue知识点
     */
    public static void printConcurrentLinkedQueueSummary() {
        System.out.println("\n========== ConcurrentLinkedQueue知识点 ==========");
        System.out.println();
        
        System.out.println("【特性】");
        System.out.println("1. 基于链表的无界线程安全队列");
        System.out.println("2. 使用CAS实现，无锁算法");
        System.out.println("3. 遵循FIFO（先进先出）原则");
        System.out.println();
        
        System.out.println("【与BlockingQueue的区别】");
        System.out.println("1. 非阻塞: 操作不会阻塞，立即返回");
        System.out.println("2. 无界: 理论上容量无限制");
        System.out.println("3. 性能: 高并发场景下性能更好");
        System.out.println();
        
        System.out.println("【常用方法】");
        System.out.println("1. offer(e): 添加元素到队尾");
        System.out.println("2. poll(): 移除并返回队头元素");
        System.out.println("3. peek(): 返回队头元素但不移除");
        System.out.println("4. size(): 返回队列大小（不准确，因为无锁）");
        System.out.println();
    }

    /**
     * 打印ConcurrentSkipListMap知识点
     */
    public static void printConcurrentSkipListSummary() {
        System.out.println("\n========== ConcurrentSkipList知识点 ==========");
        System.out.println();
        
        System.out.println("【包括的类】");
        System.out.println("1. ConcurrentSkipListMap: 线程安全的跳表Map");
        System.out.println("2. ConcurrentSkipListSet: 线程安全的跳表Set");
        System.out.println();
        
        System.out.println("【跳表(Skip List)原理】");
        System.out.println("1. 基于链表的概率性数据结构");
        System.out.println("2. 通过多级索引提高查询效率");
        System.out.println("3. 时间复杂度: O(log n)");
        System.out.println();
        
        System.out.println("【特性】");
        System.out.println("1. 线程安全: 使用CAS实现");
        System.out.println("2. 有序: 保持元素有序（Map按key排序，Set按元素排序）");
        System.out.println("3. 无锁: 读操作完全无锁，写操作使用CAS");
        System.out.println();
        
        System.out.println("【适用场景】");
        System.out.println("1. 需要有序的并发集合");
        System.out.println("2. 读多写少的场景");
        System.out.println("3. 替代ConcurrentHashMap（需要有序时）");
        System.out.println();
    }

    /**
     * 打印各种容器的对比
     */
    public static void printContainerComparison() {
        System.out.println("\n========== 并发容器对比 ==========");
        System.out.println();
        
        System.out.println("【Map系列】");
        System.out.println("HashMap vs ConcurrentHashMap vs Hashtable:");
        System.out.println("  HashMap: 非线程安全，性能高");
        System.out.println("  ConcurrentHashMap: 线程安全，高并发性能好");
        System.out.println("  Hashtable: 线程安全，但性能差（使用synchronized）");
        System.out.println();
        
        System.out.println("【List系列】");
        System.out.println("ArrayList vs Vector vs CopyOnWriteArrayList:");
        System.out.println("  ArrayList: 非线程安全，性能高");
        System.out.println("  Vector: 线程安全，但性能差（使用synchronized）");
        System.out.println("  CopyOnWriteArrayList: 线程安全，读多写少场景性能好");
        System.out.println();
        
        System.out.println("【Queue系列】");
        System.out.println("阻塞队列 vs 非阻塞队列:");
        System.out.println("  BlockingQueue: 支持阻塞操作，适合生产者-消费者模式");
        System.out.println("  ConcurrentLinkedQueue: 非阻塞，性能高");
        System.out.println();
    }

    /**
     * 打印面试常见问题
     */
    public static void printInterviewQuestions() {
        System.out.println("\n========== 并发容器相关面试常见问题 ==========");
        System.out.println();
        
        System.out.println("【Q1: ConcurrentHashMap的实现原理？】");
        System.out.println("A: JDK 1.8之前使用分段锁，JDK 1.8之后使用CAS + synchronized。");
        System.out.println("   JDK 1.8中，只有在哈希冲突时才加锁，粒度更细，性能更好。");
        System.out.println();
        
        System.out.println("【Q2: ConcurrentHashMap为什么不允许null值？】");
        System.out.println("A: 因为null值在并发环境下无法区分：");
        System.out.println("   1. key不存在返回null");
        System.out.println("   2. key存在但value为null也返回null");
        System.out.println("   无法区分这两种情况，所以不允许null值。");
        System.out.println();
        
        System.out.println("【Q3: CopyOnWriteArrayList的适用场景？】");
        System.out.println("A: 适用于读多写少的场景，比如：");
        System.out.println("   1. 白名单/黑名单（读多写少）");
        System.out.println("   2. 事件监听器列表");
        System.out.println("   3. 配置信息的缓存");
        System.out.println();
        
        System.out.println("【Q4: BlockingQueue的实现原理？】");
        System.out.println("A: BlockingQueue使用AQS或Condition实现阻塞机制。");
        System.out.println("   当队列满时，put操作会阻塞并等待；");
        System.out.println("   当队列空时，take操作会阻塞并等待。");
        System.out.println();
        
        System.out.println("【Q5: ConcurrentHashMap和Hashtable的区别？】");
        System.out.println("A: 1. 实现方式: ConcurrentHashMap使用分段锁/CAS，Hashtable使用synchronized");
        System.out.println("   2. 性能: ConcurrentHashMap性能更好，支持更高的并发度");
        System.out.println("   3. null值: ConcurrentHashMap不允许null，Hashtable也不允许null");
        System.out.println("   4. 锁粒度: ConcurrentHashMap锁粒度更细");
        System.out.println();
        
        System.out.println("【Q6: ArrayBlockingQueue和LinkedBlockingQueue的区别？】");
        System.out.println("A: 1. 数据结构: ArrayBlockingQueue使用数组，LinkedBlockingQueue使用链表");
        System.out.println("   2. 锁数量: ArrayBlockingQueue使用一个锁，LinkedBlockingQueue使用两个锁");
        System.out.println("   3. 容量: ArrayBlockingQueue必须指定容量，LinkedBlockingQueue可选");
        System.out.println("   4. 性能: LinkedBlockingQueue的吞吐量通常更高");
        System.out.println();
        
        System.out.println("【Q7: 如何选择合适的并发容器？】");
        System.out.println("A: 1. 需要Map -> ConcurrentHashMap");
        System.out.println("   2. 需要List，读多写少 -> CopyOnWriteArrayList");
        System.out.println("   3. 需要Queue，生产者-消费者 -> BlockingQueue系列");
        System.out.println("   4. 需要Queue，高性能非阻塞 -> ConcurrentLinkedQueue");
        System.out.println("   5. 需要有序 -> ConcurrentSkipListMap/Set");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        System.out.println("\n========== 并发容器知识点完整总结 ==========");
        
        printBasicConcepts();
        printConcurrentHashMapSummary();
        printCopyOnWriteSummary();
        printBlockingQueueSummary();
        printConcurrentLinkedQueueSummary();
        printConcurrentSkipListSummary();
        printContainerComparison();
        printInterviewQuestions();
    }
}

