package lock;

/**
 * @author: liumch
 * @create: 2019/6/25 17:09
 **/

public class SychronizedTest implements Runnable {
    private  int i = 0;
    public  void increase(){
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 2000 ; j++) {
            increase();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result in run:" + i);
    }

    public static void test() throws InterruptedException{
        Thread s1 = new Thread(new SychronizedTest());
        Thread s2 = new Thread(new SychronizedTest());
        s1.run();
        s2.run();
        s1.join();
        s2.join();

//        System.out.println("result:" +  i);
    }
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j <10 ; j++) {
            test();
        }
    }
}
