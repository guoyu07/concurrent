package moe.cnkirito.java7;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author xujingfeng
 * @version created at 2018/1/2
 * @since 1.8
 * 计算 [1,n] 的和
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        /**
         * get()和join()有两个主要的区别：
         * join()方法同步返回,不能被中断。如果你中断调用join()方法的线程，这个方法将抛出InterruptedException异常。如果任务抛出任何未受检异常，
         * get()方法异步返回将返回一个ExecutionException异常，而join()方法将返回一个RuntimeException异常。
         */
        //同步返回结果
        //Future<Integer> result = pool.submit(new CountTask(0, 2000));
        //System.out.println(result.get());
        //异步返回结果
        long start = System.currentTimeMillis();
        CountTask task = new CountTask(0, 2000);
        pool.execute(task);
        pool.shutdown();
        Integer count = task.join();
        System.out.println(count);
        System.out.println("fork join cost: " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        count = 0;
        for (int i = 0; i <= 2000; i++) {
            count += i;
        }
        System.out.println(count);
        System.out.println("normal cost: " + (System.currentTimeMillis() - start) + " ms");


    }
}

/**
 * 计算1..n相加总和的简单demo
 */
class CountTask extends RecursiveTask<Integer> {

    /**
     * serialID
     */
    private static final long serialVersionUID = 1L;
    //边界值
    private static final int THRESHOLD = 50;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++)
                sum += i;
        } else {
            int mid = (start + end) / 2;
            CountTask t1 = new CountTask(start, mid);
            CountTask t2 = new CountTask(mid + 1, end);
            t1.fork();
            t2.fork();
            sum = t1.join() + t2.join();
        }
        return sum;
    }
}