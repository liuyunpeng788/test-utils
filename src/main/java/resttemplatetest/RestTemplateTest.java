package resttemplatetest;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * @author: liumch
 * @create: 2019/8/16 16:09
 **/

public class RestTemplateTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String keywordExtractUrl = "http://localhost:9006/feedback/keyword";
        if (StringUtils.isEmpty(keywordExtractUrl)) {
            System.out.println("评论关注关键词提取URL为空");
            return ;
        }

        String body = restTemplate.getForObject(keywordExtractUrl + "/" + 0 + "?startDate=2019-08-14&endDate=2019-08-15",
                String.class);
        if (!StringUtils.isEmpty(body)) {
            JSONObject jsonObject = JSONObject.parseObject(body);
            Integer code = jsonObject.getInteger("errorCode");
            if (HttpStatus.OK.value() == code) {
                System.out.println("[" + 0 + "]关键词抽取请求发送成功.");
                return ;
            }
        }
        System.out.println("评论数据提取请求发送失败,响应信息:" +  body);
        return ;
    }


}
