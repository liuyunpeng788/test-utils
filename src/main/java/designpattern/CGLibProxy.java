package designpattern;

import designpattern.dynamic.CalcServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 当只有实现类没有接口类时，可以采用cglib动态代理技术实现代理
 * @author: liumch
 * @create: 2019/6/14 11:02
 **/

public class CGLibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    /**
     * 生成代理
     * @param cls ： 被代理的实体类，不能是接口
     * @return 生成的代理类
     */
    public Object getProxy(Class cls){
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("拦截父类的所有方法");
        Object result = methodProxy.invokeSuper(o,objects);
        return result;
    }

    public static void main(String[] args) {
        CGLibProxy proxy = new CGLibProxy();
        CalcServiceImpl calcServiceProxy = (CalcServiceImpl)proxy.getProxy(CalcServiceImpl.class);
        System.out.println("cglib proxy ....");
        int res = calcServiceProxy.add(1,3);
        System.out.println(res);

    }
}
