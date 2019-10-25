package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: liumch
 * @create: 2019/8/23 16:59
 **/

public class RedisSetNxTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public static void main(String[] args) {
//         ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
//         lock.readLock();
//         ReentrantReadWriteLock.ReadLock lock  = new ReentrantReadWriteLock.ReadLock();
//         lock.tryLock();

    }
}
