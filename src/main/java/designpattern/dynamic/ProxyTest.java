package designpattern.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *   采用动态代理技术时，必须要有接口类，否则报错
 * @author: liumch
 * @create: 2019/6/14 9:34
 **/

public class ProxyTest {

    public static void main(String[] args) {

        CalcService calcService = new CalcServiceImpl();

        System.out.println("method 1: use 原生的方法：");
        CalcServiceProxyHandler handler = new CalcServiceProxyHandler(calcService);
        CalcService proxyInstance = (CalcService)Proxy.newProxyInstance(
                calcService.getClass().getClassLoader()
                , calcService.getClass().getInterfaces()
                , handler
        );
        System.out.println(proxyInstance.add(1,3));

        System.out.println("method 2: 使用匿名函数：");
        CalcService proxyInstance2 = (CalcService)Proxy.newProxyInstance(
                calcService.getClass().getClassLoader()
                , calcService.getClass().getInterfaces()
                , new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(calcService,args);
                    }
                }
        );
        System.out.println(proxyInstance2.add(1,3));

        System.out.println("method 2: use lambda 表达式：");
        CalcService serv = (CalcService)Proxy.newProxyInstance(
                calcService.getClass().getClassLoader()
                , calcService.getClass().getInterfaces()
                , (proxy, method, args1) -> method.invoke(calcService, args1));
        System.out.println(serv.add(1,3));
    }
}
