package designpattern.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 采用动态代理技术时，必须要有接口类，否则报错
 * @author: liumch
 * @create: 2019/6/14 9:47
 **/

public class CalcServiceProxyHandler implements InvocationHandler {
    Object target;

    public CalcServiceProxyHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("in invoke...");
        return method.invoke(target,args);
    }
}
