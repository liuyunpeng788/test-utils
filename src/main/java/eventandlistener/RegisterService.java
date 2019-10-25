package eventandlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 用户注册服务
 * @author liumch
 * create :  2019/9/24 10:41
 **/
@Service
public class RegisterService {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发布用户注册事件
     */
    public void sendRegisterEvent(){
        applicationContext.publishEvent(new RegisterUserEvent(this,new UserDto("liumch","fonvoa123")));
    }
}
