package ltp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author: liumch
 * @create: 2019/8/6 11:04
 **/

public class LTPLocalTest {


    private static String url = "http://121.199.68.250:10086/ltp";
    private static String url_1 = "http://121.199.68.250:10087/ltp";
    private static final int HTTP_CLIENT_RETRY_COUNT = 5;

    private static final int MAXIMUM_TOTAL_CONNECTION = 100;
    private static final int MAXIMUM_CONNECTION_PER_ROUTE = 5;
    private static final int CONNECTION_VALIDATE_AFTER_INACTIVITY_MS = 200 * 1000;

    private static HttpComponentsClientHttpRequestFactory getSimpleHttpRequestFactory(){
//        SimpleClientHttpRequestFactory

        HttpClientBuilder clientBuilder = HttpClients.custom();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        // Set the maximum number of total open connections.
        connectionManager.setMaxTotal(MAXIMUM_TOTAL_CONNECTION);
        // Set the maximum number of concurrent connections per route, which is 2 by default.
        connectionManager.setDefaultMaxPerRoute(MAXIMUM_CONNECTION_PER_ROUTE);

        connectionManager.setValidateAfterInactivity(CONNECTION_VALIDATE_AFTER_INACTIVITY_MS);

        clientBuilder.setConnectionManager(connectionManager);

        clientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(HTTP_CLIENT_RETRY_COUNT, true, new ArrayList<>()) {

            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                System.out.println(String.format("Retry request, execution count: %d, exception: %s", executionCount, exception));
                return super.retryRequest(exception, executionCount, context);
            }

        });
        HttpComponentsClientHttpRequestFactory  httpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        httpRequestFactory.setConnectTimeout(600000);
        httpRequestFactory.setReadTimeout(600000);
        httpRequestFactory.setConnectionRequestTimeout(600000);
        return httpRequestFactory;
    }
    private static RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate(getSimpleHttpRequestFactory());
        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为"ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }

        messageConverters.add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM}));
        messageConverters.add(1,converter);

        return restTemplate;
    }

    /**
     *
     * 防止一句话太长导致服务奔溃。所以，需要拆开来处理
     * @param content 拆分后的句子
     * @param isMall 是否是商场
     * @return 句子解析的结果
     */
    private static JSONArray requestLTPServer(String content,boolean isMall){
        content = content.replaceAll(rm_pattern,"");
        RestTemplate restTemplate = getRestTemplate();
        JSONArray arr = new JSONArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/x-www-form-urlencoded");
        if(StringUtils.isNotBlank(content)){
            MultiValueMap<String, String> requestEntity;
            String[] contents = content.split("[。！？]");
            for(String text : contents){
                if(StringUtils.isNotBlank(text)){
                    requestEntity = new LinkedMultiValueMap<>();
                    requestEntity.add("f","json");
                    requestEntity.add("s",text);
                    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestEntity,headers);
                    ResponseEntity<JSONArray> response = restTemplate.postForEntity( isMall? url:url_1 ,httpEntity, JSONArray.class);
                    if(response.getStatusCode().equals(HttpStatus.OK) && !CollectionUtils.isEmpty(response.getBody())) {
                        arr.add(response.getBody().getJSONArray(0).getJSONArray(0));
                    }
                }
            }
        }
        return arr;
    }

    private static  String rm_pattern = "[^\\u0000-\\uFFFF|[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]]";
    public static void main(String[] args) {
        String text = "事情做不完就明天再做吧,运气好的话,明天死了就不用做了";
//        String text = "活了三十多年了，这算是我人生中最糟糕的一次购物经历了吧。,在百盛优客城市广场购物时，不小心把信用卡落在了ELAND，发现后立即打电话给客服，客服说没收到过信用卡，让我去柜台问问。我当即表示家住的非常远，来回一次需要3个多小时，请她们帮我问下这家专柜有没有人拾到过我的信用卡。完全没想到客服一口拒绝了我，说是反正她那里没有，要我本人自己去柜台问。当下我就傻眼了，这是什么客服啊，我都告诉你我来回需要三个多小时了，你还让我亲自跑来问，万一没有，我不是白跑了吗？再说，问一下对于你而言，不过是一个电话的事情。再不济，您老辛苦点亲自从四楼下到二楼去问一下，应该也花不了五分钟的时间。,周一憋了一肚子气，蹭蹭蹭地冲到了百盛优客，果然，我的信用卡就在ELAND的柜台。拿会信用卡，直接去客服部投诉，找到了所谓的客服经理投诉。好吧，不得不承认，找客服投诉客服，是我天真了。我问客服经理，你们不是韩国商场吗，韩国人不是以服务好著称的吗，你们怎么可以连我的正当要求都断然拒绝，完全不顾你们顾客着急的心理，非要我亲自千里迢迢来看是否在才行。那位经理首先承认是他们做的不到位，然后说……我们是虽然是韩国的商场，但这不是在中国吗？每个国家的国情不一样。,人家客服经理连这话都说出来了，我还能说什么。我要求找他的领导投诉，这位经理来了一句，客服就是他管了，他不会给我其他领导的电话的。,无奈之下只能走人，真是第一次碰到这种投诉无门的事情啊。走的时候遇到里面的员工，告诉我店长办公室在6楼，可以直接找店长投诉。好吧，反正都花时间来了，顺道就上去看看呗。终于找到了他们最大的韩国店长了，反映了事情，对方完全没反应，一心只想让我快点走，好像他们有什么人来参观的样子。,好吧好吧，果然是适应了中国国情的韩国商场，如此服务态度以及敷衍的态度真是让人失望。这样的商场，我是永远不会再去了。";
//        Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE ) ;
//        String rm_pattern = "[^\\u0000-\\uFFFF|[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]]";
//        text = text.replaceAll(rm_pattern,"");
//        RestTemplate restTemplate = getRestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("content-type","application/x-www-form-urlencoded");
//
//        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
//
//        requestEntity.add("s",text);
//        requestEntity.add("f","json");
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestEntity,headers);

        long start = System.currentTimeMillis();

        for ( int i = 0; i < 1 ; i++) {
            final int cur = i;
            new Thread(()->{

                long t1=System.currentTimeMillis();
//                ResponseEntity<JSONArray> response = restTemplate.postForEntity( url,httpEntity,JSONArray.class);
                JSONArray arr = requestLTPServer(text,false);
                System.out.println(String.format("request cost %f s",(System.currentTimeMillis()-t1)*1.0/1000));
                if(!CollectionUtils.isEmpty(arr)){
//                    JSONArray arr = response.getBody().getJSONArray(0);
                    System.out.println(arr.toJSONString());
                    //一个段落里的每句话，按照句号、问好、感叹号等分隔
                    arr.parallelStream().forEach(x->{
                        JSONArray subSentenceArr = (JSONArray) x;
                        //一个句子中的每个短句
                        for (int k = 0; k < subSentenceArr.size(); k++) {
                            JSONObject pos = subSentenceArr.getJSONObject(k);
                            k = pos.getInteger("id");
                            //提取名词,线程安全
                            StringBuilder sb = new StringBuilder();
                            if(pos.getString("pos").equalsIgnoreCase("n")
                                    ||(pos.getString("pos").equalsIgnoreCase("v") && pos.getString("relate").equalsIgnoreCase("SBV"))){
                                JSONObject pObject = subSentenceArr.getJSONObject(pos.getInteger("parent"));
                                if(pObject.getString("pos").equalsIgnoreCase("a")  ){
                                    sb.append(pos.getString("cont")).append(pObject.getString("cont"));
                                    k = Math.max(k,pObject.getInteger("id"));
                                    System.out.println(sb.toString());
                                }
//                                else if(pObject.getString("relate").equalsIgnoreCase("SBV")){
//                                    sb.append(pos.getString("cont")).append(pObject.getString("cont"));
//                                    pObject = subSentenceArr.getJSONObject(pObject.getInteger("parent"));
//                                    if(pObject.getString("pos").equalsIgnoreCase("a")){
//                                        k = Math.max(k,pObject.getInteger("id"));
//                                        sb.append(pObject.getString("cont"));
//                                        System.out.println(sb.toString());
//                                        k = Math.max(k,pObject.getInteger("id"));
//                                    }
//
//                                }
                            }
                        }
                    });
                }
                long t2 = System.currentTimeMillis();
                System.out.println("thread-" + cur + " cost " + (t2 - t1)*0.1/1000 + " s");
            },"thread-" + i).start();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时： "+ 1.0*(end - start)/1000 + " s");
    }
}
