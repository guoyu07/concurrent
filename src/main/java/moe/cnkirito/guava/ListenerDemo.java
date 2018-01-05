package moe.cnkirito.guava;

import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author xujingfeng
 * @version created at 2018/1/3
 * @since 1.8
 */
public class ListenerDemo {
    @Test
    public void testSync() {
        //同步阻塞
        for(int i=0;i<10;i++){
            delay1s();
            System.out.println("byebye world");
        }
    }

    @Test
    public void testAsync() throws Exception {
        //异步非阻塞+阻塞
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        List<ListenableFuture<String>> listenableFutures = new ArrayList<>();
        for(int i=0;i<10;i++){
            final ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    delay1s();
                    return "byebye world";
                }
            });
            listenableFutures.add(listenableFuture);
        }
        for (ListenableFuture<String> listenableFuture : listenableFutures) {
            System.out.println(listenableFuture.get());
        }

    }

    @Test
    public void testCallback() {
        //异步非阻塞+回调
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        for(int i=0;i<10;i++){
            final ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    delay1s();
                    return "byebye world";
                }
            });
            Futures.addCallback(listenableFuture, new FutureCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    public static void delay1s(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
