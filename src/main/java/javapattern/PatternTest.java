package javapattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liumch
 * @create: 2019/7/17 18:45
 **/

public class PatternTest {


    public static void main(String[] args) {
        String content = "三星电脑<span>电池不给力</span>我买了一个手机<span>效果很差</span>";
        Pattern pattern = Pattern.compile("<span>(.*?)</span>");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            String res = matcher.group(1);
            System.out.println("result :" + res);
        }
    }

}
