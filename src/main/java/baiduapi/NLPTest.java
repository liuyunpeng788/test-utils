//package baiduapi;
//
//import com.baidu.aip.nlp.AipNlp;
//import com.baidu.aip.nlp.ESimnetType;
//import org.apache.commons.lang3.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 调用百度api,实现文章的语义识别
// * @author: liumch
// * @create: 2019/7/16 16:52
// **/
//
//public class NLPTest {
//    public static final String BAIDU_AI_APP_ID = "16832882"; //"15371264";
//    public static final String BAIDU_AI_API_KEY =  "sFvE4PQ1O4IhWp0QwD9yoHt8";   // "4YRZA2Ad10AAzy6oPkq2G5ap";
//    public static final String BAIDU_AI_SECRET_KEY =  "IFoLvAT6zmE3dut6CA5Yb3slZDElSgyS"; // "BCMqSVFeTBgwULt3j3iEcj9TBLWkda73";
//
//    /**
//     * 获取访问token
//     * @param key 访问id
//     * @param secret 访问密钥
//     * @return token信息
//     */
//    private static String getAccessKey(String key , String secret){
//        RestTemplate restTemplate = new RestTemplate();
//        String url= String.format("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s", key,secret);
//        String res = null;
//        com.alibaba.fastjson.JSONObject obj;
//        for(int i =0 ;i<3;i++){
//            res = restTemplate.getForObject(url,String.class);
//            if(StringUtils.isNotBlank(res)){
//                obj = com.alibaba.fastjson.JSONObject.parseObject(res);
//                res = obj.getString("access_token");
//                if(StringUtils.isNotBlank(res)){
//                    return res;
//                }
//            }
//        }
//        return res;
//    }
//    public static void main(String[] args) {
//
//
//        // 初始化一个AipNlp
//        AipNlp client = new AipNlp(BAIDU_AI_APP_ID, BAIDU_AI_API_KEY, BAIDU_AI_SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//       /*
//        1 - 酒店
//        2 - KTV3 - 丽人
//        4 - 美食餐饮
//        5 - 旅游
//        6 - 健康
//        7 - 教育
//        8 - 商业
//        9 - 房产
//        10 - 汽车
//        11 - 生活
//        12 - 购物
//        13 - 3C
//        */
//       final Pattern pattern = Pattern.compile("<span>(.*?)</span>");
//
//        // 调用接口
//        String text = "味道和性价比都很不错得一家店，找也不算难找，刚开始还拍一拍照，后来看到吃的\n" +
//                "全忘了ㄟ( ▔, ▔ )ㄏ必点毛肚，口感很好，很新鲜，量也不错，冰粉甜甜的，但是口味又不算重，蛮清淡的，肉也不错，就是对我来说一盘的量有点少了，总体来说还是很不错的";
//
//        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE ) ;
//        String rm_pattern = "[^\\u0000-\\uFFFF|[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]]";
//        text = text.replaceAll(rm_pattern,"");
//
//        // 获取美食评论情感属性
//         System.out.println(new Date());
//        JSONObject res = client.commentTag(text, ESimnetType.SHOPPING,null);
//        System.out.println(new Date());
//        System.out.println(res.toString(2));
//        String content = text;
//         List<String> tags = new ArrayList<>();
//        JSONArray arr = res.getJSONArray("items");
//        System.out.println(arr.toString());
//        JSONObject obj;
//        Matcher matcher;
//        for(int i = 0; i< arr.length();i++){
//            obj = arr.getJSONObject(i);
//            String tag = "";
//            if(StringUtils.isNotBlank(obj.getString("prop"))){
//                tag += obj.getString("prop");
//            }
//            if(StringUtils.isNotBlank(obj.getString("adj"))){
//                tag += obj.getString("adj");
//            }
//            if(StringUtils.isNotBlank(tag)){
//                tags.add(tag);
//            }
//            //处理高亮
//            if(StringUtils.isNotBlank(obj.getString("abstract"))){
//                String highlightText = obj.getString("abstract");
//                matcher = pattern.matcher(highlightText);
//                while (matcher.find()){
//                    String matchWord = matcher.group(1);
//
//                    if(StringUtils.isNotBlank(matchWord)){
//                        if(matchWord.substring(0,1).matches("[?.*+]")){
//                            matchWord = matchWord.substring(1);
//                            content = content.replaceAll("[?]","").replaceAll("[.]","。")
//                                    .replaceAll("[+]","").replace("[*]","");
//                        }
//
////                        content = content.replaceAll("[?.*+]","");
//                        content = content.replaceAll(matchWord,"<pre>" + matchWord + "</pre>");
//                        System.out.println(content);
//                    }
//
//                }
//            }
//
//        }
//
//
//
//
//    }
//
//}
