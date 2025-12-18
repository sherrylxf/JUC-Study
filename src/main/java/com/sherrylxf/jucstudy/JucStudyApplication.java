package com.sherrylxf.jucstudy;

import com.sherrylxf.jucstudy.threadCreate.MyRunnable;
import com.sherrylxf.jucstudy.threadCreate.MyThread;
import com.sherrylxf.jucstudy.threadMethod.ThreadMethodDemo;
import com.sherrylxf.jucstudy.threadPool.ThreadPoolDemo;
import com.sherrylxf.jucstudy.threadState.ThreadStateDemo;
import com.sherrylxf.jucstudy.threadState.ThreadStateSummary;
import com.sherrylxf.jucstudy.threadState.ThreadStateTransition;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class JucStudyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        /*
//        创建线程的方式
//        1. 继承Thread类
//        2. 实现Runnable接口
//        3. 使用匿名内部类，new Thread(new Runnable() -> {} )
//
//        CPU单核只执行一个线程，多线程其实是不同线程通过时间片切换，是异步执行。
//         */
//        System.out.println("========== 第一部分：线程创建方式演示 ==========");
//        MyThread myThread = new MyThread();
//        myThread.start();
//
//        Thread myThread2 = new Thread(new MyRunnable());
//        myThread2.start();
//
//        Thread myThread3 = new Thread(() -> {
//            System.out.println("Runnable函数式接口，用Lambda表达式创建线程");
//        });
//        myThread3.start();
//
//        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
//            System.out.println("Callable函数式接口重写call，用Lambda表达式创建线程");
//            return 50;
//        });
//        Thread myThread4 = new Thread(futureTask);
//        myThread4.start();
//        Integer value = futureTask.get();
//        System.out.println("FutureTask + Callable 创建线程，并有线程的返回值： " + value);
//
//        // 等待所有线程执行完成
//        myThread.join();
//        myThread2.join();
//        myThread3.join();
//
//        System.out.println("\n========== 第二部分：线程状态及状态转换演示 ==========");
//
//        // 打印线程状态总结
//        ThreadStateSummary.printFullSummary();
//
//        // 演示线程的6种状态
//        ThreadStateDemo.demonstrateThreadStates();
//
//        // 演示各种状态转换
//        ThreadStateTransition.demonstrateAllTransitions();
//
//        System.out.println("\n========== 第三部分：多线程方法演示 ==========");
//
//        // 演示所有多线程方法
//        ThreadMethodDemo.demonstrateAllMethods();
        
        System.out.println("\n========== 第四部分：线程池演示 ==========");
        
        // 演示所有线程池相关内容
        ThreadPoolDemo.demonstrateAll();
        
        System.out.println("\n========== 所有演示完成 ==========");
    }

}
