package com.sherrylxf.jucstudy.threadMethod;

/**
 * 线程控制方法演示
 * 包括：sleep(), join(), yield(), interrupt(), isInterrupted() 等
 */
public class ThreadControlMethods {

    /**
     * 演示 sleep() 方法
     * 让当前线程休眠指定毫秒数，线程进入TIMED_WAITING状态
     */
    public static void demonstrateSleep() {
        System.out.println("\n========== sleep() 方法演示 ==========");
        
        System.out.println("开始时间: " + System.currentTimeMillis());
        
        Thread thread = new Thread(() -> {
            try {
                System.out.println("线程开始休眠2秒");
                Thread.sleep(2000); // 休眠2000毫秒
                System.out.println("线程休眠结束");
            } catch (InterruptedException e) {
                System.out.println("线程被中断: " + e.getMessage());
            }
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("结束时间: " + System.currentTimeMillis());
    }

    /**
     * 演示 join() 方法
     * 等待调用join()的线程执行完成
     */
    public static void demonstrateJoin() {
        System.out.println("\n========== join() 方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程执行完成");
        });
        
        thread.start();
        
        System.out.println("主线程等待子线程完成...");
        try {
            thread.join(); // 主线程等待thread执行完成
            System.out.println("主线程继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 join(timeout) 方法
     * 等待指定时间，如果线程未完成则继续执行
     */
    public static void demonstrateJoinWithTimeout() {
        System.out.println("\n========== join(timeout) 方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始执行（需要3秒）");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程执行完成");
        });
        
        thread.start();
        
        System.out.println("主线程等待子线程最多1秒...");
        try {
            thread.join(1000); // 最多等待1秒
            if (thread.isAlive()) {
                System.out.println("等待超时，主线程继续执行（子线程仍在运行）");
            } else {
                System.out.println("子线程已完成，主线程继续执行");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 等待子线程真正完成
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 yield() 方法
     * 提示调度器当前线程愿意让出CPU，让其他线程运行
     * 注意：这是一个建议，不保证一定会让出
     */
    public static void demonstrateYield() {
        System.out.println("\n========== yield() 方法演示 ==========");
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程1: " + i);
                Thread.yield(); // 让出CPU
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程2: " + i);
                Thread.yield(); // 让出CPU
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("注意：yield()只是建议，实际效果取决于JVM调度");
    }

    /**
     * 演示 interrupt() 方法
     * 中断线程，设置中断标志位
     */
    public static void demonstrateInterrupt() {
        System.out.println("\n========== interrupt() 方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("线程运行中...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("线程在sleep时被中断");
                    // 中断标志被清除，需要重新设置或退出循环
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("线程退出");
        });
        
        thread.start();
        
        try {
            Thread.sleep(2500); // 让线程运行一段时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("主线程中断子线程");
        thread.interrupt(); // 中断线程
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 isInterrupted() 方法
     * 检查线程的中断标志位（不清除标志）
     */
    public static void demonstrateIsInterrupted() {
        System.out.println("\n========== isInterrupted() 方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("线程开始执行");
            while (!Thread.currentThread().isInterrupted()) {
                // 检查中断标志
                System.out.println("线程运行中，中断标志: " + Thread.currentThread().isInterrupted());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("捕获到中断异常");
                    System.out.println("中断标志: " + Thread.currentThread().isInterrupted());
                    break;
                }
            }
            System.out.println("线程结束");
        });
        
        thread.start();
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("中断线程");
        thread.interrupt();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示 interrupted() 静态方法
     * 检查当前线程的中断标志位，并清除标志
     */
    public static void demonstrateInterrupted() {
        System.out.println("\n========== interrupted() 静态方法演示 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("线程开始执行");
            while (true) {
                if (Thread.interrupted()) { // 检查并清除中断标志
                    System.out.println("检测到中断，清除标志后: " + Thread.interrupted());
                    break;
                }
                System.out.println("线程运行中...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("捕获到中断异常");
                    break;
                }
            }
            System.out.println("线程结束");
        });
        
        thread.start();
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("中断线程");
        thread.interrupt();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示所有控制方法
     */
    public static void demonstrateAllControlMethods() {
        System.out.println("\n========== 线程控制方法综合演示 ==========");
        
        demonstrateSleep();
        demonstrateJoin();
        demonstrateJoinWithTimeout();
        demonstrateYield();
        demonstrateInterrupt();
        demonstrateIsInterrupted();
        demonstrateInterrupted();
    }
}








