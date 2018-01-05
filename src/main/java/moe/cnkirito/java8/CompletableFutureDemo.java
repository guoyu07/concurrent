package moe.cnkirito.java8;

import java.util.concurrent.CompletableFuture;

/**
 * @author xujingfeng
 * @version created at 2018/1/3
 * @since 1.8
 * <p>
 * 假设 [ 接口1，...，接口5]的每一次调用耗时为 200ms （其中接口2依赖接口1，接口5依赖接口3，接口4），那么总耗时为 1s，这整个是一个串行的过程
 * 在 java8 中的解法
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> combineFuture = method2Async(method1Async()).thenCombine(method5Async(method3Async
                (), method4Async()), (result1, result2) ->
                result1 + result2);
        System.out.println(combineFuture.get());
        System.out.println("completableFuture cost " + (System.currentTimeMillis() - start) + " ms");
    }

    static CompletableFuture<Integer> method1Async() {
        return CompletableFuture.supplyAsync(() -> {
            delay200ms();
            return 1;
        });
    }

    static CompletableFuture<Integer> method2Async(CompletableFuture<Integer> method1future) {
        return CompletableFuture.supplyAsync(() -> {
            delay200ms();
            try {
                return 2 + method1future.get();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    static CompletableFuture<Integer> method3Async() {
        return CompletableFuture.supplyAsync(() -> {
            delay200ms();
            return 3;
        });
    }

    static CompletableFuture<Integer> method4Async() {
        return CompletableFuture.supplyAsync(() -> {
            delay200ms();
            return 4;
        });
    }

    static CompletableFuture<Integer> method5Async(CompletableFuture<Integer> method3future,
                                                   CompletableFuture<Integer> method4future) throws Exception {
        delay200ms();
        CompletableFuture<Integer> combineFuture = method3future.thenCombine(method4future, ((integer, integer2) -> integer + integer2));
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (combineFuture.get() + 5);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    static void delay200ms() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
