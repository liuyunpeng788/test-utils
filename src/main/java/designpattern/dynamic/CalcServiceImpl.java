package designpattern.dynamic;

/**
 * @author: liumch
 * @create: 2019/6/14 9:37
 **/


public class CalcServiceImpl implements CalcService {

    @Override
    public int add(int v1, int v2) {
        return v1 + v2;
    }

    @Override
    public int minus(int v1, int v2) {
        return v1 - v2;
    }
}
