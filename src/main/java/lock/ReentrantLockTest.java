package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: liumch
 * @create: 2019/7/5 17:30
 **/
class ThreadService implements Runnable{
    private static Lock lock = new ReentrantLock();
    //添加了锁控制
    private int value;
    //没有添加锁控制
    private int ordinaryValue;
    //原子类
    private AtomicInteger atomicValue = new AtomicInteger(0) ;

    public int getValue() throws InterruptedException{
        if(lock.tryLock(100L, TimeUnit.MILLISECONDS)){
        try {
                value ++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        return value;
    }

    public void ordinaryValue(){
        ordinaryValue++;
    }

    /**
     * 因为value++ 不是原子操作，所以，即便加了volatile,也不是线程安全的
     *
     */

    public void getValueWithLock(){
        atomicValue.getAndIncrement();
    }

    @Override
    public void run() {
         ordinaryValue();
//        try {
//            getValue();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        getValueWithLock();

    }

    public int getFinalValue(){
        return value;
    }
    public int getFinalAtomicValue(){
        return atomicValue.intValue();
    }
    public int getFinalOrdinaryValue(){
        return ordinaryValue;
    }
}

public class ReentrantLockTest {

    public static void main(String[] args) throws InterruptedException{

        ThreadService service = new ThreadService();
        for (int i = 0; i <20000 ; i++) {
            new Thread( service,"thread-" +i).start();
        }
        Thread.sleep(1000);
        System.out.println("ordinaryValue = " +  service.getFinalOrdinaryValue());
        System.out.println("value = " +  service.getFinalValue());
        System.out.println("atomic value  = " +  service.getFinalAtomicValue());
    }

}
