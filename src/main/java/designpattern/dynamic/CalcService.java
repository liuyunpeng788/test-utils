package designpattern.dynamic;

/**
 * @author: liumch
 * @create: 2019/6/14 9:36
 **/

public interface CalcService {
    /**
     *两个数相加
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个数的和
     */
    int add(int v1, int v2);

    /**
     *两个数相加
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个数的差
     */
    int minus(int v1, int v2);
}
