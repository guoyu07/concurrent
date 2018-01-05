package moe.cnkirito.java5;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author xujingfeng
 * @version created at 2018/1/2
 * @since 1.8
 * <p>
 * 线程池执行runnable和callable
 */
public class ExecutorDemo {

    @Test
    public void test1() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);//开多少个线程合适呢？ 引申 => db，redis等连接池中连接的个数
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        });

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 4 / 2;
            }
        });
        //....
        System.out.println(future.get());
        executorService.shutdown();
    }

//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ExecutorService isDaemon : " + Thread.currentThread().isDaemon());
//            }
//        });
//
//        System.out.println("Main isDaemon : " + Thread.currentThread().isDaemon());
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("new Thread() isDaemon : " + Thread.currentThread().isDaemon());
//            }
//        });
//        thread.start();
//        System.out.println("new Thread() isDaemon : " + thread.isDaemon());
//
//        executorService.shutdown();
//    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("ExecutorService isDaemon : " + Thread.currentThread().isDaemon());
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("new Thread() isDaemon : " + Thread.currentThread().isDaemon());
            }
        });
        thread.start();
        System.out.println("new Thread() isDaemon : " + thread.isDaemon());

        executorService.shutdown();
    }

}
