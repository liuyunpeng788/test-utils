package eventandlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 以eventlisten 注解的方式用户注册事件监听
 * @author liumch
 * create :  2019/9/24 10:58
 **/
@Component
public class AnnotationRegisterEventListen {
    @EventListener
    public void RegisterEventListen(RegisterUserEvent event){
        System.out.println("receive register event ,user info : " + event.getUserInfo());
    }

}
