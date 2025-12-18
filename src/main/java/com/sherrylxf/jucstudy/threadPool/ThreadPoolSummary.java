package com.sherrylxf.jucstudy.threadPool;

/**
 * 线程池知识点总结
 */
public class ThreadPoolSummary {

    /**
     * 打印线程池基本概念总结
     */
    public static void printBasicConcepts() {
        System.out.println("\n========== 线程池基本概念 ==========");
        System.out.println();
        
        System.out.println("【什么是线程池】");
        System.out.println("线程池是一种线程使用模式，预先创建一定数量的线程，");
        System.out.println("当有任务需要执行时，从线程池中获取线程执行，");
        System.out.println("任务执行完毕后，线程不销毁，而是返回线程池等待下次使用。");
        System.out.println();
        
        System.out.println("【线程池的优势】");
        System.out.println("1. 降低资源消耗：减少线程创建和销毁的开销");
        System.out.println("2. 提高响应速度：任务到达时，线程已存在，无需等待创建");
        System.out.println("3. 提高线程的可管理性：统一管理、监控和调优");
        System.out.println("4. 控制并发数：避免创建过多线程导致系统资源耗尽");
        System.out.println();
        
        System.out.println("【线程池的核心组件】");
        System.out.println("1. 核心线程数 (corePoolSize)");
        System.out.println("2. 最大线程数 (maximumPoolSize)");
        System.out.println("3. 工作队列 (workQueue)");
        System.out.println("4. 线程工厂 (threadFactory)");
        System.out.println("5. 拒绝策略 (rejectedExecutionHandler)");
        System.out.println("6. 空闲线程存活时间 (keepAliveTime)");
        System.out.println();
    }

    /**
     * 打印ThreadPoolExecutor参数说明
     */
    public static void printThreadPoolExecutorParams() {
        System.out.println("\n========== ThreadPoolExecutor 核心参数详解 ==========");
        System.out.println();
        
        System.out.println("1. corePoolSize (核心线程数)");
        System.out.println("   - 线程池中保持存活的最小线程数");
        System.out.println("   - 即使线程空闲，也不会被回收（除非allowCoreThreadTimeOut=true）");
        System.out.println();
        
        System.out.println("2. maximumPoolSize (最大线程数)");
        System.out.println("   - 线程池允许创建的最大线程数");
        System.out.println("   - 当队列满了且当前线程数 < maximumPoolSize 时，创建新线程");
        System.out.println();
        
        System.out.println("3. keepAliveTime (空闲线程存活时间)");
        System.out.println("   - 非核心线程空闲时的存活时间");
        System.out.println("   - 超过这个时间，非核心线程会被回收");
        System.out.println();
        
        System.out.println("4. workQueue (工作队列)");
        System.out.println("   - 用于存放等待执行的任务");
        System.out.println("   - 常见类型：");
        System.out.println("     * LinkedBlockingQueue: 无界队列");
        System.out.println("     * ArrayBlockingQueue: 有界队列");
        System.out.println("     * SynchronousQueue: 同步队列，不存储元素");
        System.out.println("     * PriorityBlockingQueue: 优先级队列");
        System.out.println();
        
        System.out.println("5. threadFactory (线程工厂)");
        System.out.println("   - 用于创建线程");
        System.out.println("   - 可以自定义线程名称、优先级等");
        System.out.println();
        
        System.out.println("6. handler (拒绝策略)");
        System.out.println("   - 当线程池和队列都满了时的处理策略");
        System.out.println("   - 常见策略：");
        System.out.println("     * AbortPolicy: 直接抛出异常");
        System.out.println("     * CallerRunsPolicy: 调用者运行");
        System.out.println("     * DiscardPolicy: 直接丢弃");
        System.out.println("     * DiscardOldestPolicy: 丢弃最老的任务");
        System.out.println();
    }

    /**
     * 打印线程池执行流程
     */
    public static void printExecutionFlow() {
        System.out.println("\n========== 线程池执行流程 ==========");
        System.out.println();
        System.out.println("1. 提交任务");
        System.out.println("   ↓");
        System.out.println("2. 判断当前线程数 < corePoolSize?");
        System.out.println("   ├─ 是 → 创建新线程执行任务");
        System.out.println("   └─ 否 → 进入步骤3");
        System.out.println("   ↓");
        System.out.println("3. 判断工作队列是否已满?");
        System.out.println("   ├─ 未满 → 将任务放入队列");
        System.out.println("   └─ 已满 → 进入步骤4");
        System.out.println("   ↓");
        System.out.println("4. 判断当前线程数 < maximumPoolSize?");
        System.out.println("   ├─ 是 → 创建新线程执行任务");
        System.out.println("   └─ 否 → 进入步骤5");
        System.out.println("   ↓");
        System.out.println("5. 执行拒绝策略");
        System.out.println();
    }

    /**
     * 打印各种线程池类型总结
     */
    public static void printThreadPoolTypes() {
        System.out.println("\n========== 各种线程池类型 ==========");
        System.out.println();
        
        System.out.println("1. FixedThreadPool (固定大小线程池)");
        System.out.println("   创建方式: Executors.newFixedThreadPool(n)");
        System.out.println("   特点: 核心线程数 = 最大线程数 = n");
        System.out.println("   队列: LinkedBlockingQueue (无界)");
        System.out.println("   适用: 执行长期任务，负载较重的服务器");
        System.out.println();
        
        System.out.println("2. CachedThreadPool (缓存线程池)");
        System.out.println("   创建方式: Executors.newCachedThreadPool()");
        System.out.println("   特点: 核心线程数 = 0, 最大线程数 = Integer.MAX_VALUE");
        System.out.println("   队列: SynchronousQueue (不存储元素)");
        System.out.println("   适用: 执行大量短期异步任务");
        System.out.println();
        
        System.out.println("3. SingleThreadExecutor (单线程线程池)");
        System.out.println("   创建方式: Executors.newSingleThreadExecutor()");
        System.out.println("   特点: 核心线程数 = 最大线程数 = 1");
        System.out.println("   队列: LinkedBlockingQueue (无界)");
        System.out.println("   适用: 需要顺序执行的任务");
        System.out.println();
        
        System.out.println("4. ScheduledThreadPool (定时任务线程池)");
        System.out.println("   创建方式: Executors.newScheduledThreadPool(n)");
        System.out.println("   特点: 可以执行定时任务和周期性任务");
        System.out.println("   队列: DelayedWorkQueue");
        System.out.println("   适用: 定时任务、周期性任务");
        System.out.println();
        
        System.out.println("5. WorkStealingPool (工作窃取线程池)");
        System.out.println("   创建方式: Executors.newWorkStealingPool()");
        System.out.println("   特点: 基于ForkJoinPool，使用工作窃取算法");
        System.out.println("   适用: 可以分解的任务");
        System.out.println();
    }

    /**
     * 打印任务提交方法总结
     */
    public static void printTaskSubmission() {
        System.out.println("\n========== 任务提交方法 ==========");
        System.out.println();
        
        System.out.println("1. execute(Runnable)");
        System.out.println("   - 提交Runnable任务");
        System.out.println("   - 无返回值");
        System.out.println("   - 无法获取任务执行结果");
        System.out.println();
        
        System.out.println("2. submit(Runnable)");
        System.out.println("   - 提交Runnable任务");
        System.out.println("   - 返回Future<?>");
        System.out.println("   - 可以判断任务是否完成");
        System.out.println();
        
        System.out.println("3. submit(Callable<T>)");
        System.out.println("   - 提交Callable任务");
        System.out.println("   - 返回Future<T>");
        System.out.println("   - 可以获取任务执行结果");
        System.out.println();
        
        System.out.println("4. invokeAll(Collection<Callable<T>>)");
        System.out.println("   - 批量提交任务");
        System.out.println("   - 等待所有任务完成");
        System.out.println("   - 返回List<Future<T>>");
        System.out.println();
        
        System.out.println("5. invokeAny(Collection<Callable<T>>)");
        System.out.println("   - 批量提交任务");
        System.out.println("   - 返回第一个完成的任务结果");
        System.out.println("   - 其他任务会被取消");
        System.out.println();
    }

    /**
     * 打印线程池关闭方法总结
     */
    public static void printShutdownMethods() {
        System.out.println("\n========== 线程池关闭方法 ==========");
        System.out.println();
        
        System.out.println("1. shutdown()");
        System.out.println("   - 优雅关闭");
        System.out.println("   - 不再接受新任务");
        System.out.println("   - 等待已提交的任务完成");
        System.out.println();
        
        System.out.println("2. shutdownNow()");
        System.out.println("   - 立即关闭");
        System.out.println("   - 尝试停止所有正在执行的任务");
        System.out.println("   - 返回等待执行的任务列表");
        System.out.println();
        
        System.out.println("3. awaitTermination(long timeout, TimeUnit unit)");
        System.out.println("   - 等待线程池终止");
        System.out.println("   - 必须在shutdown()或shutdownNow()之后调用");
        System.out.println("   - 返回true表示已终止，false表示超时");
        System.out.println();
        
        System.out.println("【正确的关闭流程】");
        System.out.println("1. 调用 shutdown() 停止接受新任务");
        System.out.println("2. 调用 awaitTermination() 等待任务完成");
        System.out.println("3. 如果超时，调用 shutdownNow() 强制关闭");
        System.out.println();
    }

    /**
     * 打印线程池最佳实践
     */
    public static void printBestPractices() {
        System.out.println("\n========== 线程池最佳实践 ==========");
        System.out.println();
        
        System.out.println("1. 【线程池大小设置】");
        System.out.println("   CPU密集型任务: 线程数 = CPU核心数");
        System.out.println("     说明: 不再推荐 N+1，因为CPU始终是瓶颈，");
        System.out.println("           预留线程并不能增加CPU处理能力，反而可能加剧竞争。");
        System.out.println("           处理缺页中断等情况仍然需要占用CPU核心。");
        System.out.println("   IO密集型任务: 线程数 = CPU核心数 * 2 (或更多，根据IO等待时间调整)");
        System.out.println("     说明: IO操作会阻塞线程，可以设置更多线程充分利用CPU");
        System.out.println("   混合型任务: 根据实际情况调整");
        System.out.println("     公式: 线程数 = CPU核心数 * (1 + IO等待时间 / CPU计算时间)");
        System.out.println();
        
        System.out.println("2. 【避免使用Executors创建线程池】");
        System.out.println("   - FixedThreadPool 和 SingleThreadExecutor 使用无界队列，可能导致OOM");
        System.out.println("   - CachedThreadPool 最大线程数为Integer.MAX_VALUE，可能导致OOM");
        System.out.println("   - 推荐使用 ThreadPoolExecutor 手动创建");
        System.out.println();
        
        System.out.println("3. 【合理设置队列大小】");
        System.out.println("   - 有界队列可以防止内存溢出");
        System.out.println("   - 队列大小需要根据实际情况调整");
        System.out.println();
        
        System.out.println("4. 【自定义拒绝策略】");
        System.out.println("   - 根据业务需求选择合适的拒绝策略");
        System.out.println("   - 可以记录日志、保存任务到数据库等");
        System.out.println();
        
        System.out.println("5. 【正确关闭线程池】");
        System.out.println("   - 使用 shutdown() + awaitTermination()");
        System.out.println("   - 超时后使用 shutdownNow()");
        System.out.println("   - 在finally块中确保关闭");
        System.out.println();
        
        System.out.println("6. 【监控线程池状态】");
        System.out.println("   - 监控线程池大小、活跃线程数、队列大小等");
        System.out.println("   - 及时发现问题并调整参数");
        System.out.println();
    }

    /**
     * 打印完整总结
     */
    public static void printFullSummary() {
        printBasicConcepts();
        printThreadPoolExecutorParams();
        printExecutionFlow();
        printThreadPoolTypes();
        printTaskSubmission();
        printShutdownMethods();
        printBestPractices();
    }
}

