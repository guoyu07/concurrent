package moe.cnkirito.java5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author xujingfeng
 * @version created at 2017/12/27
 * @since 1.8
 * <p>
 * 假设 [ 接口1，...，接口5]的每一次调用耗时为 200ms （其中接口2依赖接口1，接口5依赖接口3，接口4），那么总耗时为 1s，这整个是一个串行的过程
 */
public class CallableDemo2 {

    public static void main(String[] args) throws Exception {

        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        Future<Integer> resultFuture1 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return method2(method1());//200ms+200ms
            }
        });
        Future<Integer> resultFuture2 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Future<Integer> resultFuture3 = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return method3();
                    }
                });
                Future<Integer> resultFuture4 = executorService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return method4();
                    }
                });
                return method5(resultFuture3.get(), resultFuture4.get());
            }
        });
        int result = resultFuture1.get() + resultFuture2.get();

        System.out.println("result = " + result + ", total cost " + (System.currentTimeMillis() - start) + " ms");

        executorService.shutdown();
    }

    static int method1() {
        delay200ms();
        return 1;
    }

    static int method2(int method1) {
        delay200ms();
        return 2 + method1;
    }

    static int method3() {
        delay200ms();
        return 3;
    }

    static int method4() {
        delay200ms();
        return 4;
    }

    static int method5(int method3, int method4) {
        delay200ms();
        return 5 + method3 + method4;
    }

    static void delay200ms() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
