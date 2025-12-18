package com.sherrylxf.jucstudy;

import com.sherrylxf.jucstudy.threadCreate.MyRunnable;
import com.sherrylxf.jucstudy.threadCreate.MyThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@SpringBootApplication
public class JucStudyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        创建线程的方式
        1. 继承Thread类
        2. 实现Runnable接口
        3. 使用匿名内部类，new Thread(new Runnable() -> {} )

        CPU单核只执行一个线程，多线程其实是不同线程通过时间片切换，是异步执行。
         */
        MyThread myThread = new MyThread();
        myThread.start();

        Thread myThread2 = new Thread(new MyRunnable());
        myThread2.start();

        Thread myThread3 = new Thread(() -> {
            System.out.println("Runnable函数式接口，用Lambda表达式创建线程");
        });

        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("Callable函数式接口重写call，用Lambda表达式创建线程");
            return 50;
        });
        Thread myThread4 = new Thread(futureTask);
        myThread4.start();
        Integer value = futureTask.get();
        System.out.println("FutureTask + Callable 创建线程，并有线程的返回值： " + value);

        System.out.println("主线程");

        SpringApplication.run(JucStudyApplication.class, args);
    }

}
