package pers.wxp.threadpool;

import java.util.concurrent.*;

/**
 * ExecutorService 提供了两种提交任务的方法：
 * execute()：提交不需要返回值的任务
 * submit()：提交需要返回值的任务
 */
public class ExecutorServiceDemo {
    //手动创建线程
    private ExecutorService pool = new ThreadPoolExecutor(3, 10,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(512), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    /**
     * Executors类创建线程池的时候实际就是调用ThreadPoolExecutor类的构造方法来创建,
     * 创建固定线程数
     *  允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致OOM。
     *  FixedThreadPool 和 SingleThreadExecutor ：
     *  允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致OOM。
     */
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    /**
     * 单线程池:顾名思义，也就是创建一个核心线程.
     */
    public ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    /**
     * CachedThreadPool 和 ScheduledThreadPool
     *  固定线程数，支持定时和周期性任务
     */
    public ExecutorService newCachedThreadPool = Executors.newCachedThreadPool ();
    /**
     * 可缓存线程池:最大线程数为Interge的最大值，空闲线程存活时间是1分钟。
     * 如果有大量耗时的任务，则不适该创建方式。它只适用于生命周期短的任务。
     */
    public ExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool (10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("开始等待");
        boolean b = executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("等待完毕");
        if(b){
            System.out.println("分线程已经结束");
        }
        System.out.println(Thread.currentThread().getName());
    }
}
