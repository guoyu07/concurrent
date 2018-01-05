package moe.cnkirito.java5before;

/**
 * @author xujingfeng
 * @version created at 2018/1/2
 * @since 1.8
 *
 * 1. 创建线程的几种方式
 * 2. 引出 happens-before 原则
 */
public class RunnableDemo {

    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello sub thread");
            }
        });

        thread.start();
        thread.join();//异步+阻塞=同步
        System.out.println("hello main thread 1");
        System.out.println("hello main thread 2");
    }

}
