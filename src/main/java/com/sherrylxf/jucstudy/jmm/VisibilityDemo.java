package com.sherrylxf.jucstudy.jmm;

/**
 * 可见性问题演示
 * 面试重点：volatile解决可见性问题
 */
public class VisibilityDemo {

    // 不使用volatile，会出现可见性问题
    private static boolean flag = false;
    
    // 使用volatile，保证可见性
    private static volatile boolean volatileFlag = false;
    
    private static int count = 0;

    /**
     * 演示可见性问题
     * 问题：主线程修改flag后，子线程可能看不到最新的值
     */
    public static void demonstrateVisibilityProblem() {
        System.out.println("\n========== 可见性问题演示 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始执行，等待flag变为true...");
            // 可能陷入死循环，因为看不到主线程对flag的修改
            while (!flag) {
                // 空循环，等待flag变为true
            }
            System.out.println("子线程检测到flag变为true，退出循环");
        });
        
        thread.start();
        
        try {
            Thread.sleep(1000); // 让子线程先运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("主线程修改flag为true");
        flag = true;
        
        // 等待一段时间，观察子线程是否能退出
        try {
            Thread.sleep(2000);
            if (thread.isAlive()) {
                System.out.println("⚠️ 子线程仍在运行，说明存在可见性问题！");
                System.out.println("   子线程看不到主线程对flag的修改");
            } else {
                System.out.println("✓ 子线程已退出");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示volatile解决可见性问题
     */
    public static void demonstrateVolatileSolution() {
        System.out.println("\n========== volatile解决可见性问题 ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始执行，等待volatileFlag变为true...");
            while (!volatileFlag) {
                // 空循环
            }
            System.out.println("✓ 子线程检测到volatileFlag变为true，立即退出循环");
        });
        
        thread.start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("主线程修改volatileFlag为true");
        volatileFlag = true;
        
        try {
            Thread.sleep(100);
            if (thread.isAlive()) {
                System.out.println("子线程仍在运行");
            } else {
                System.out.println("✓ volatile保证了可见性，子线程立即看到了修改");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示为什么有时候不加volatile也能看到修改
     * 原因：某些操作会触发内存同步（如System.out.println、Thread.sleep等）
     */
    public static void demonstrateWhySometimesWorks() {
        System.out.println("\n========== 为什么有时候不加volatile也能看到修改？ ==========");
        
        Thread thread = new Thread(() -> {
            System.out.println("子线程开始执行");
            while (!flag) {
                // 如果这里有任何操作（如System.out.println、Thread.sleep等），
                // 可能会触发内存同步，从而看到flag的修改
                // 但这不是保证，只是可能发生
            }
            System.out.println("子线程退出");
        });
        
        thread.start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        flag = true;
        
        System.out.println("注意：某些情况下不加volatile也能工作，");
        System.out.println("     但这依赖于JVM实现，不是保证！");
        System.out.println("     生产环境必须使用volatile或其他同步机制");
    }

    /**
     * 演示count++的可见性问题
     */
    public static void demonstrateCountVisibility() {
        System.out.println("\n========== count++的可见性问题 ==========");
        
        // 不使用volatile
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++; // 非原子操作，且可能有可见性问题
            }
            System.out.println("Writer线程完成，count = " + count);
        });
        
        Thread reader = new Thread(() -> {
            int localCount = count;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reader线程读取，count = " + localCount);
            System.out.println("可能读取到旧值，因为存在可见性问题");
        });
        
        reader.start();
        writer.start();
        
        try {
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 综合演示可见性问题
     */
    public static void demonstrateAll() {
        System.out.println("\n========== 可见性问题综合演示 ==========");
        
        demonstrateVisibilityProblem();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 重置flag
        flag = false;
        volatileFlag = false;
        
        demonstrateVolatileSolution();
        demonstrateWhySometimesWorks();
        demonstrateCountVisibility();
    }
}

