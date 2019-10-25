package dingding;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

/**
 * 钉钉告警接口实现
 * @author: liumch
 * @create: 2019/8/14 11:11
 **/

public class DingDingAlert {

    private static final String alertUrl = "https://oapi.dingtalk.com/robot/send?access_token=c966629e8c9c9cb42b3bf0017b4398296969fb6ec2d680b1101ff2337756c54f" ;
    /**
     * 发送钉钉告警
     * @param msg 告警的消息详情
     */
    private static void dingdingAlert(String msg){
        if(StringUtils.isNotBlank(msg)){
            JSONObject contentObj = new JSONObject();
            contentObj.put("title","百度观点提取API接口调用异常");
            contentObj.put("text", "### 百度观点提取API接口调用异常\n" + ">" + msg);
            contentObj.put("content",msg);
            JSONObject obj = new JSONObject();
            obj.put("msgtype","markdown");
            obj.put("markdown",contentObj);
            obj.put("isAtAll",true);
            RestTemplate restTemplate = new RestTemplate();
            JSONObject postResult = restTemplate.postForObject(alertUrl, obj, JSONObject.class);
            if(postResult.getInteger("errcode").equals(0)){
                System.out.println("发送告警成功");
            }
        }

    }

    /**
     *  api 访问的结果
     */
    private static Queue<Boolean> resQueue = new ArrayBlockingQueue<>(10);
    private synchronized static void insertElem(boolean success){

        if(resQueue.size() < 10){
            resQueue.add(success);
        }else {
            resQueue.poll();
            resQueue.offer(success);
        }
    }
    private static boolean  needAlert(){
        if(resQueue.size() < 10){
            return false;
        }
        Iterator<Boolean> iterator = resQueue.iterator();
        while (iterator.hasNext()){
            if( iterator.next()){
                return false;
            }
        }
        return true;
    }

    private synchronized static void clearQueue(){
        resQueue.clear();
    }

    public static void main(String[] args) {
        IntStream.range(1,100).forEach(x ->{
            boolean success = new Random().nextBoolean();
            System.out.println(success);
            insertElem(success);
            if(needAlert()){
                System.out.println("should alert ...");
//                dingdingAlert("[告警测试，请忽略] 已经连续十次无法获取返回信息,请及时处理 ...");
                clearQueue();
            }
        });


    }
}
