package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试多个条件下的Condition 互斥
 * @author: liumch
 * @create: 2019/7/5 18:14
 **/


/**
 * 资源类
 * 实现的功能： 1、当条件A 满足的时候，执行i 自增5次。当条件B满足时，j 自增10次。当条件C满足时，k自增15次。
 *  2、A执行完触发条件B，B执行完触发C。C执行完触发A
 */
class Resource  {
    //默认非公平锁
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();
    int num = 1;


    public void printA() throws IllegalMonitorStateException{
        lock.lock();
        try {
            if(num != 1){
                c1.await();
            }
             for(int i = 0; i<5;i++){
                System.out.println(Thread.currentThread().getName() + " print a...");
            }
            num = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printB() throws IllegalMonitorStateException{
        lock.lock();

        try {
            if(num != 2){
                c2.await();
            }

            for(int i = 0; i<10;i++){
                System.out.println(Thread.currentThread().getName() + " print b...");
            }
            num = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printC() throws IllegalMonitorStateException{
        lock.lock();
        try {
            if(num != 3){
                c3.await();
            }
             for(int i = 0; i<15;i++){
                System.out.println(Thread.currentThread().getName() + " print c...");
            }
            num = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}

public class ReentrantLockConditionTest {
    public static void main(String[] args) throws InterruptedException{
        Resource resource = new Resource();

        new Thread(()-> {
            for (int i = 0; i < 5 ; i++) {
                resource.printA();
            }    
        }
        ,"thread-A" ).start();
        new Thread(()-> {
            for (int i = 0; i < 10 ; i++) {
                resource.printB();
            }
        },"thread-B" ).start();
        new Thread(()-> {
            for (int i = 0; i < 15 ; i++) {
                resource.printC();
            }
        },"thread-C" ).start();

    }
}
