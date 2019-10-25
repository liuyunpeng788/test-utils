package threadpoolfuture;

import com.google.common.util.concurrent.*;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池测试
 * @author: liumch
 * @create: 2019/8/15 16:34
 **/

public class ThreadPoolFutureTest {
   private final static  ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 4, 1L, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(10), new ThreadFactory() {
        AtomicInteger id = new AtomicInteger(1);
        String groupPrefix = "ThreadPool-";
        SecurityManager sm = System.getSecurityManager();
        ThreadGroup group = null == sm ? Thread.currentThread().getThreadGroup():sm.getThreadGroup();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group,r,groupPrefix + "thread" + id.getAndIncrement());
            if(thread.isDaemon()){
                thread.setDaemon(false);
            }
            if(thread.getPriority() != Thread.NORM_PRIORITY){
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }, new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(poolExecutor);

        for (int i = 0; i < 10 ; i++) {
            ListenableFuture<Integer> future = executorService.submit(new Callable<Integer>(){
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(100);
                    return new Random().nextInt();
                }
            });
            Futures.addCallback(future, new FutureCallback<Integer>() {
                @Override
                public void onSuccess(@Nullable Integer result) {
                    System.out.println( new Date() + "success:" + result);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("failure:" + t.getMessage());
                }
            },executorService);
//
        }
        executorService.shutdown();

    }
}
