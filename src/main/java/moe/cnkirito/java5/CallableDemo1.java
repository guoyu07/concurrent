package moe.cnkirito.java5;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author xujingfeng
 * @version created at 2018/1/2
 * @since 1.8
 *
 * callable 和 future 的简单介绍
 */
public class CallableDemo1 {

    public static void main(String[] args) throws Exception {
        Callable<Integer> helloCallable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 4 / 2;
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(helloCallable);
        new Thread(future).start();
        Integer result = future.get();
        System.out.println(result);
    }

}
