package com.sherrylxf.jucstudy.concurrentContainer;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发容器综合演示类
 * 包含各种并发容器的使用示例
 */
public class ConcurrentContainerDemo {

    /**
     * 演示ConcurrentHashMap基本使用
     */
    public static void demonstrateConcurrentHashMap() {
        System.out.println("\n========== ConcurrentHashMap演示 ==========");
        
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        
        // 多线程并发put
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    map.put("key" + threadId + "-" + j, threadId * 100 + j);
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终map大小: " + map.size());
        System.out.println("✓ ConcurrentHashMap保证线程安全");
    }

    /**
     * 演示ConcurrentHashMap的高级方法
     */
    public static void demonstrateConcurrentHashMapAdvanced() {
        System.out.println("\n========== ConcurrentHashMap高级方法演示 ==========");
        
        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        
        // computeIfAbsent: 如果不存在则计算
        map.computeIfAbsent("count", k -> new AtomicInteger(0)).incrementAndGet();
        map.computeIfAbsent("count", k -> new AtomicInteger(0)).incrementAndGet();
        System.out.println("computeIfAbsent结果: " + map.get("count"));
        
        // merge: 合并操作
        map.merge("count", new AtomicInteger(5), (oldVal, newVal) -> {
            oldVal.addAndGet(newVal.get());
            return oldVal;
        });
        System.out.println("merge后结果: " + map.get("count"));
        
        // forEach: 遍历
        map.put("a", new AtomicInteger(1));
        map.put("b", new AtomicInteger(2));
        map.put("c", new AtomicInteger(3));
        System.out.print("forEach遍历: ");
        map.forEach((k, v) -> System.out.print(k + "=" + v.get() + " "));
        System.out.println();
    }

    /**
     * 演示CopyOnWriteArrayList
     */
    public static void demonstrateCopyOnWriteArrayList() {
        System.out.println("\n========== CopyOnWriteArrayList演示 ==========");
        
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        
        // 初始化数据
        list.add("元素1");
        list.add("元素2");
        list.add("元素3");
        
        // 读线程
        Thread readThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("读取线程遍历: " + list);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // 写线程
        Thread writeThread = new Thread(() -> {
            try {
                Thread.sleep(50);
                list.add("新元素1");
                System.out.println("写入新元素1");
                Thread.sleep(100);
                list.add("新元素2");
                System.out.println("写入新元素2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        readThread.start();
        writeThread.start();
        
        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("最终列表: " + list);
        System.out.println("✓ CopyOnWriteArrayList读写不互相阻塞");
    }

    /**
     * 演示CopyOnWriteArraySet
     */
    public static void demonstrateCopyOnWriteArraySet() {
        System.out.println("\n========== CopyOnWriteArraySet演示 ==========");
        
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        
        // 多线程并发添加
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int start = i * 10;
            executor.submit(() -> {
                for (int j = start; j < start + 10; j++) {
                    set.add(j);
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Set大小: " + set.size());
        System.out.println("Set内容（前10个）: " + set.stream().limit(10).toList());
        System.out.println("✓ CopyOnWriteArraySet保证线程安全且元素唯一");
    }

    /**
     * 演示ArrayBlockingQueue
     */
    public static void demonstrateArrayBlockingQueue() {
        System.out.println("\n========== ArrayBlockingQueue演示 ==========");
        
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        
        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put("产品" + i);
                    System.out.println("生产: 产品" + i + " (队列大小: " + queue.size() + ")");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(300); // 等待生产者先生产
                for (int i = 1; i <= 5; i++) {
                    String product = queue.take();
                    System.out.println("消费: " + product + " (队列大小: " + queue.size() + ")");
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ ArrayBlockingQueue实现生产者-消费者模式");
    }

    /**
     * 演示LinkedBlockingQueue
     */
    public static void demonstrateLinkedBlockingQueue() {
        System.out.println("\n========== LinkedBlockingQueue演示 ==========");
        
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        
        // 演示offer和poll（非阻塞）
        System.out.println("offer操作（非阻塞）:");
        System.out.println("offer(元素1): " + queue.offer("元素1"));
        System.out.println("offer(元素2): " + queue.offer("元素2"));
        System.out.println("offer(元素3): " + queue.offer("元素3"));
        System.out.println("offer(元素4): " + queue.offer("元素4") + " (队列满，返回false)");
        System.out.println("队列大小: " + queue.size());
        
        System.out.println("\npoll操作（非阻塞）:");
        System.out.println("poll(): " + queue.poll());
        System.out.println("poll(): " + queue.poll());
        System.out.println("poll(): " + queue.poll());
        System.out.println("poll(): " + queue.poll() + " (队列空，返回null)");
        
        System.out.println("✓ LinkedBlockingQueue支持阻塞和非阻塞操作");
    }

    /**
     * 演示PriorityBlockingQueue
     */
    public static void demonstratePriorityBlockingQueue() {
        System.out.println("\n========== PriorityBlockingQueue演示 ==========");
        
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        
        // 添加元素（无序）
        queue.offer(5);
        queue.offer(2);
        queue.offer(8);
        queue.offer(1);
        queue.offer(9);
        
        System.out.println("添加顺序: 5, 2, 8, 1, 9");
        System.out.print("取出顺序（按优先级）: ");
        
        // 取出元素（按优先级）
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();
        
        System.out.println("✓ PriorityBlockingQueue按优先级排序");
    }

    /**
     * 演示DelayQueue
     */
    public static void demonstrateDelayQueue() {
        System.out.println("\n========== DelayQueue演示 ==========");
        
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        
        // 添加延迟任务
        long now = System.currentTimeMillis();
        queue.offer(new DelayedTask("任务1", now + 2000));
        queue.offer(new DelayedTask("任务2", now + 1000));
        queue.offer(new DelayedTask("任务3", now + 3000));
        
        System.out.println("添加了3个延迟任务");
        
        // 取出任务（只有到期的任务才能被取出）
        try {
            System.out.println("等待取出任务...");
            DelayedTask task = queue.take();
            System.out.println("取出: " + task.getName() + " (延迟1秒)");
            
            task = queue.take();
            System.out.println("取出: " + task.getName() + " (延迟2秒)");
            
            task = queue.take();
            System.out.println("取出: " + task.getName() + " (延迟3秒)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ DelayQueue实现延迟队列功能");
    }

    /**
     * 延迟任务类
     */
    static class DelayedTask implements Delayed {
        private final String name;
        private final long executeTime;
        
        public DelayedTask(String name, long executeTime) {
            this.name = name;
            this.executeTime = executeTime;
        }
        
        public String getName() {
            return name;
        }
        
        @Override
        public long getDelay(TimeUnit unit) {
            long delay = executeTime - System.currentTimeMillis();
            return unit.convert(delay, TimeUnit.MILLISECONDS);
        }
        
        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.executeTime, ((DelayedTask) o).executeTime);
        }
    }

    /**
     * 演示SynchronousQueue
     */
    public static void demonstrateSynchronousQueue() {
        System.out.println("\n========== SynchronousQueue演示 ==========");
        
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        
        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                System.out.println("生产者: 准备放入元素");
                queue.put("数据");
                System.out.println("生产者: 元素已放入");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(500); // 确保生产者先执行
                System.out.println("消费者: 准备取出元素");
                String data = queue.take();
                System.out.println("消费者: 取出元素 " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ SynchronousQueue不存储元素，直接传递");
    }

    /**
     * 演示ConcurrentLinkedQueue
     */
    public static void demonstrateConcurrentLinkedQueue() {
        System.out.println("\n========== ConcurrentLinkedQueue演示 ==========");
        
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        
        // 多线程并发操作
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // 生产者
        for (int i = 0; i < 3; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    queue.offer("元素" + threadId + "-" + j);
                }
            });
        }
        
        // 消费者
        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                int count = 0;
                while (count < 15) {
                    String element = queue.poll();
                    if (element != null) {
                        count++;
                    }
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("剩余队列大小: " + queue.size());
        System.out.println("✓ ConcurrentLinkedQueue非阻塞、高并发性能好");
    }

    /**
     * 演示ConcurrentSkipListMap
     */
    public static void demonstrateConcurrentSkipListMap() {
        System.out.println("\n========== ConcurrentSkipListMap演示 ==========");
        
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        
        // 添加元素（无序）
        map.put(30, "value30");
        map.put(10, "value10");
        map.put(50, "value50");
        map.put(20, "value20");
        map.put(40, "value40");
        
        System.out.println("添加顺序: 30, 10, 50, 20, 40");
        System.out.print("遍历顺序（有序）: ");
        map.forEach((k, v) -> System.out.print(k + "=" + v + " "));
        System.out.println();
        
        System.out.println("第一个key: " + map.firstKey());
        System.out.println("最后一个key: " + map.lastKey());
        System.out.println("小于25的key: " + map.lowerKey(25));
        System.out.println("大于25的key: " + map.higherKey(25));
        
        System.out.println("✓ ConcurrentSkipListMap保持key有序");
    }

    /**
     * 演示ConcurrentSkipListSet
     */
    public static void demonstrateConcurrentSkipListSet() {
        System.out.println("\n========== ConcurrentSkipListSet演示 ==========");
        
        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
        
        // 添加元素（无序）
        set.add(5);
        set.add(2);
        set.add(8);
        set.add(1);
        set.add(9);
        
        System.out.println("添加顺序: 5, 2, 8, 1, 9");
        System.out.print("遍历顺序（有序）: ");
        set.forEach(e -> System.out.print(e + " "));
        System.out.println();
        
        System.out.println("第一个元素: " + set.first());
        System.out.println("最后一个元素: " + set.last());
        
        System.out.println("✓ ConcurrentSkipListSet保持元素有序");
    }

    /**
     * 演示生产者-消费者模式（使用BlockingQueue）
     */
    public static void demonstrateProducerConsumer() {
        System.out.println("\n========== 生产者-消费者模式演示 ==========");
        
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        
        // 生产者
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put("产品" + i);
                    System.out.println("生产: 产品" + i);
                    Thread.sleep(100);
                }
                queue.put("END"); // 结束标记
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // 消费者
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String product = queue.take();
                    if ("END".equals(product)) {
                        break;
                    }
                    System.out.println("消费: " + product);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ BlockingQueue完美实现生产者-消费者模式");
    }

    /**
     * 打印并发容器知识点总结
     */
    public static void printSummary() {
        System.out.println("\n========== 并发容器知识点总结 ==========");
        ConcurrentContainerSummary.printFullSummary();
    }

    /**
     * 综合演示所有内容
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 并发容器完整学习演示 ==========");
        
        // 先看理论总结
        printSummary();
        
        // Map系列
        System.out.println("\n========== Map系列 ==========");
        demonstrateConcurrentHashMap();
        demonstrateConcurrentHashMapAdvanced();
        
        // List/Set系列
        System.out.println("\n========== List/Set系列 ==========");
        demonstrateCopyOnWriteArrayList();
        demonstrateCopyOnWriteArraySet();
        
        // Queue系列 - BlockingQueue
        System.out.println("\n========== BlockingQueue系列 ==========");
        demonstrateArrayBlockingQueue();
        demonstrateLinkedBlockingQueue();
        demonstratePriorityBlockingQueue();
        demonstrateDelayQueue();
        demonstrateSynchronousQueue();
        
        // Queue系列 - 非阻塞
        System.out.println("\n========== 非阻塞Queue系列 ==========");
        demonstrateConcurrentLinkedQueue();
        
        // SkipList系列
        System.out.println("\n========== SkipList系列 ==========");
        demonstrateConcurrentSkipListMap();
        demonstrateConcurrentSkipListSet();
        
        // 综合应用
        System.out.println("\n========== 综合应用 ==========");
        demonstrateProducerConsumer();
        
        System.out.println("\n========== 并发容器学习演示完成 ==========");
    }
}

