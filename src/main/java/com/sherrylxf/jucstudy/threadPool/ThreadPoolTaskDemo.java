package com.sherrylxf.jucstudy.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池任务提交和执行演示
 * 包括：submit(), execute(), Future, CompletableFuture 等
 */
public class ThreadPoolTaskDemo {

    /**
     * 演示 submit() 和 execute() 的区别
     */
    public static void demonstrateSubmitVsExecute() {
        System.out.println("\n========== submit() vs execute() 的区别 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("1. execute() - 提交Runnable任务，无返回值:");
        executor.execute(() -> {
            System.out.println("   execute() 执行的任务");
        });
        
        System.out.println("2. submit(Runnable) - 提交Runnable任务，返回Future:");
        Future<?> future1 = executor.submit(() -> {
            System.out.println("   submit(Runnable) 执行的任务");
        });
        System.out.println("   Future对象: " + future1);
        
        System.out.println("3. submit(Callable) - 提交Callable任务，返回Future:");
        Future<String> future2 = executor.submit(() -> {
            System.out.println("   submit(Callable) 执行的任务");
            return "任务结果";
        });
        
        try {
            System.out.println("   获取结果: " + future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 Future 的使用
     */
    public static void demonstrateFuture() {
        System.out.println("\n========== Future 使用演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Future<Integer>> futures = new ArrayList<>();
        
        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            Future<Integer> future = executor.submit(() -> {
                System.out.println("任务 " + taskId + " 开始执行");
                Thread.sleep(1000);
                System.out.println("任务 " + taskId + " 执行完成");
                return taskId * 10;
            });
            futures.add(future);
        }
        
        System.out.println("所有任务已提交，开始获取结果:");
        
        // 获取结果
        for (int i = 0; i < futures.size(); i++) {
            try {
                Future<Integer> future = futures.get(i);
                System.out.println("任务 " + i + " 结果: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        executor.shutdown();
    }

    /**
     * 演示 Future 的取消
     */
    public static void demonstrateFutureCancel() {
        System.out.println("\n========== Future 取消演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        Future<?> future = executor.submit(() -> {
            System.out.println("长时间运行的任务开始");
            try {
                Thread.sleep(5000);
                System.out.println("任务完成");
            } catch (InterruptedException e) {
                System.out.println("任务被中断");
            }
        });
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("尝试取消任务:");
        boolean cancelled = future.cancel(true);
        System.out.println("取消结果: " + cancelled);
        System.out.println("是否已取消: " + future.isCancelled());
        System.out.println("是否已完成: " + future.isDone());
        
        executor.shutdown();
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 invokeAll() - 批量提交任务
     */
    public static void demonstrateInvokeAll() {
        System.out.println("\n========== invokeAll() 批量提交任务 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                System.out.println("任务 " + taskId + " 执行中");
                Thread.sleep(1000);
                return "任务 " + taskId + " 的结果";
            });
        }
        
        try {
            System.out.println("批量提交任务，等待所有任务完成...");
            List<Future<String>> futures = executor.invokeAll(tasks);
            
            System.out.println("所有任务完成，获取结果:");
            for (int i = 0; i < futures.size(); i++) {
                System.out.println("  " + futures.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }

    /**
     * 演示 invokeAny() - 获取第一个完成的任务结果
     */
    public static void demonstrateInvokeAny() {
        System.out.println("\n========== invokeAny() 获取第一个完成的任务 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                int sleepTime = (taskId + 1) * 500; // 不同的执行时间
                System.out.println("任务 " + taskId + " 开始执行，预计耗时 " + sleepTime + "ms");
                Thread.sleep(sleepTime);
                System.out.println("任务 " + taskId + " 完成");
                return "任务 " + taskId + " 的结果";
            });
        }
        
        try {
            System.out.println("提交任务，获取第一个完成的结果...");
            String result = executor.invokeAny(tasks);
            System.out.println("第一个完成的任务结果: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 CompletableFuture (Java 8+)
     */
    public static void demonstrateCompletableFuture() {
        System.out.println("\n========== CompletableFuture 演示 ==========");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("1. 异步执行任务:");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("   异步任务执行中...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "异步任务结果";
        }, executor);
        
        try {
            System.out.println("   结果: " + future1.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n2. 任务链式调用:");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("   第一步: 获取数据");
            return "数据";
        }, executor)
        .thenApply(data -> {
            System.out.println("   第二步: 处理 " + data);
            return "处理后的" + data;
        })
        .thenAccept(result -> {
            System.out.println("   第三步: 消费 " + result);
        });
        
        System.out.println("\n3. 组合多个任务:");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("   任务1执行");
            return "结果1";
        }, executor);
        
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("   任务2执行");
            return "结果2";
        }, executor);
        
        CompletableFuture<String> combined = future2.thenCombine(future3, (r1, r2) -> {
            System.out.println("   组合结果: " + r1 + " + " + r2);
            return r1 + " + " + r2;
        });
        
        try {
            System.out.println("   最终结果: " + combined.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
        
        try {
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示所有任务相关方法
     */
    public static void demonstrateAllTasks() {
        System.out.println("\n========== 线程池任务提交和执行综合演示 ==========");
        
        demonstrateSubmitVsExecute();
        demonstrateFuture();
        demonstrateFutureCancel();
        demonstrateInvokeAll();
        demonstrateInvokeAny();
        demonstrateCompletableFuture();
    }
}








