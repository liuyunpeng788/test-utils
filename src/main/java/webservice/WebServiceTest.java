package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * webservice 测试类
 * @author: liumch
 * @create: 2019/8/16 11:24
 **/
@WebService
public class WebServiceTest {
    public String say(String name){
        return "hello," + name ;
    }

    @WebMethod(exclude = true)
    public String getAge(){
        return "my age is 10";
    }

    public static void main(String[] args) {
        Endpoint.publish("http://172.16.100.192:8080/WebService",new WebServiceTest());
        System.out.println("发布成功");
    }
}
