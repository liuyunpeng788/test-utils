package queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author: liumch
 * @create: 2019/6/11 14:41
 **/

public class QueueTest {
    public static void main(String[] args) {
        BlockingDeque<Integer> queue = new LinkedBlockingDeque<>(4);
        for (int i = 0 ; i< queue.size() + 4;i++){
            //add ：如果队列满了，则直接拒绝
            queue.offer(i);
        }
        System.out.println("queue size:" + queue.size());
        System.out.println("================");
        for (int i = 0 ; i< queue.size()+ 4;i++){
            //add ：如果队列满了，则抛出异常
            queue.add(i);
        }
        System.out.println("queue size:" + queue.size());

    }
}
