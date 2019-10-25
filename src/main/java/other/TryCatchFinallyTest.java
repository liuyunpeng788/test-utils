package other;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: liumch
 * @create: 2019/7/23 14:38
 **/

public class TryCatchFinallyTest {
    private static int testException() {
        try{
            InputStream in = new FileInputStream("d:\\test");
            return 1;
        }catch (IOException io){
            System.out.println("io exception ...");
            return -1;
        }catch (RuntimeException r){
            System.out.println("runtime exception...");
            return -2;
        }catch(Exception e){
            System.out.println("exception ...");
            return -3;
        }finally {
            return 0;
        }

    }

    private static int testException1() throws Exception {
        try{
            InputStream in = new FileInputStream("d:\\test");
            return 1;
        } finally {
            return 0;
        }

    }

    public static void main(String[] args)  {
        int i = testException();
        System.out.println(i);


        try {
            i = testException1();
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
