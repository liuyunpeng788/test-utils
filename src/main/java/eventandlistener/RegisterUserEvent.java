package eventandlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 * @author liumch
 * create :  2019/9/24 10:34
 **/
@Slf4j
public class RegisterUserEvent extends ApplicationEvent {
    private UserDto userDto;

    /**
     * 注册用户事件
     * @param source 事件源
     * @param dto 用户对象
     */
    public RegisterUserEvent(Object source,UserDto dto) {
        super(source);
        this.userDto = dto;
    }

    public UserDto getUserInfo(){
        return userDto;
    }

}
