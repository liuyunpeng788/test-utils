package json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liumch
 * @create: 2019/6/13 15:03
 **/

public class JSONArrayTest {

    private static String parseBankPref(JSONObject cusTagsResJson, String srcCode) {
        JSONObject jsonObject = cusTagsResJson.getJSONObject(srcCode);
        if(null == jsonObject){
            return null;
        }
        return String.join(",",jsonObject.keySet());

    }
    /**
     * 社交类型  j100
     * demo : "GBM_BHM_SOC": "婚恋: \"低&0.0\", 通讯: \"低&0.26909512806234415\", 交友: \"中&0.3425875713417744\", 职场: \"低&0.0\"",
     * @param cusTagsResJson 解析对象
     * @param srcCode 查询key
     * @return  社交类型偏好
     */
    private static String parseSocialInfoByKey(final JSONObject cusTagsResJson, String keyWord){
        String data = cusTagsResJson.getString("GBM_BHM_SOC");
        if(StringUtils.isNotBlank(data)){
            for(String item: data.split("[,，]")){
                String[] value = item.split(":");
                if(StringUtils.isNotBlank(value[0]) && keyWord.equals(value[0].trim())){
                    return value[1];
                }
            }
        }
        return null;
    }
    public static void main(String[] args) {


        JSONObject json = new JSONObject();

        json.put("a",1);
        System.out.println(json.toJSONString());

        json.put("GBM_BHM_PURB_CONP",Arrays.asList("服装","化妆品"));
        System.out.println(json.toJSONString());
        JSONArray arr = json.getJSONArray("GBM_BHM_PURB_CONP");
        List<String> list = arr.toJavaList(String.class);
        list.forEach(System.out::print);
        System.out.println();
        String traps = "真丝商厦(步行街店)&0.949367088607595,糯米(浦东嘉里城)&0.31645569620253167,恒基名人购物中心&1.5822784810126582,新锐畅送&0.949367088607595,汇智国际商业中心&87.34177215189874,浦建路118号巴黎春天&0.31645569620253167,天之骄子生活新天地&0.6329113924050633,绿色米兰城市奥特莱斯(东方商厦店)&0.949367088607595,瑞而士健康屋（南京路东方商厦）店&1.5822784810126582,下沉式广场&0.31645569620253167,鹿王羊绒工厂店&0.31645569620253167,lacoste&0.949367088607595,南京路353广场&1.5822784810126582,新都汇邻里中心(金高店)&0.6329113924050633,南京路步行街&0.31645569620253167,瑞而士健康屋（杨浦东方商厦）店&0.949367088607595,正大乐天&0";
        String  res = Arrays.stream(traps.trim().split(",")).map(x->x.split("&")[0]).collect(Collectors.joining(","));
        System.out.println(res);

        String strJson = "{\"GBM_BHM_BANK_PREF\": {\"工商银行\": 0.2015840450403168,\"浦发银行\": 99.27596736485519,\"中信银行\": 0.5224485901044896}}";
        JSONObject obj = JSONObject.parseObject(strJson);
        System.out.println(parseBankPref(obj,"GBM_BHM_BANK_PREF"));
    }
}
