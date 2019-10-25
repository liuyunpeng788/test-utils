package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: liumch
 * @create: 2019/7/24 14:32
 **/

public class SubStringTest {


    /**
     * 原来的解析规则
     * @param keyword 关键字符串
     */
    private static void ruleParse(String keyword){
         keyword = keyword.substring(keyword.indexOf("&") + 1);
        keyword = keyword.replaceAll("and", "AND");          //替换TM的大小写的and
        String str1 = null;
        String str3 = null;
        String str4 = null;
//          logger.info("keyword: "+keyword);
        try {
            str1 = keyword.substring(0, keyword.indexOf(")"))
                    .replace("text:", "")
                    .replace("(", "")
                    .replace("\"", "")
                    .replaceAll(" ", "")
                    .trim();
            str3 = keyword.substring(keyword.indexOf(")"))
                    .split("AND")[1]
                    .replace(")", "")
                    .replace("\"", "")
                    .replaceAll(" ", "")
                    .replace("(", "")
                    .trim();
            if(getSubStringCount(keyword, "AND") > 1){
                str4 = keyword.substring(keyword.indexOf(")"))
                        .split("AND")[2]
                        .replace(")", "")
                        .replace("\"", "")
                        .replaceAll(" ", "")
                        .replace("(", "")
                        .trim();
            } else {
                str4 = "";
            }

        } catch (NullPointerException e){
             e.printStackTrace();

        } catch (ArrayIndexOutOfBoundsException e){
             e.printStackTrace();

        }
        System.out.println(str1 + "|" + str3 + "|" + str4);
    }


    /**
     * 原来提取子串的方法
     * @param str 字符串
     * @param key key
     * @return 出现次数
     */
    public static int getSubStringCount(String str, String key) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

    /**
     * 采用stack的方式，提取的结果
     * @param source 源语句
     * @param left 左分隔符
     * @param right 右分隔符
     */
    private static void parse(String source,Character left,Character right){
        source = left.equals("(")?source.substring(source.indexOf("(")+1,source.lastIndexOf(")")):source;
        System.out.println("source:" +  source);

        Stack<Character> stack = new Stack<>();
        List<String> list = new ArrayList<>(3);
        for (int i = 0; i < source.length(); i++) {
            Character ch = source.charAt(i);
            if(ch.equals(right) && !stack.empty()){
               StringBuffer buffer = new StringBuffer();
               while(!stack.empty()){
                   Character popChar = stack.pop();
                   if(popChar.equals(left)){
                       break;
                   }else{
                       buffer.append(popChar);
                   }
               }
               String res = buffer.reverse().toString();
               if(!"AND".equals(res.trim())){
                   //两个关键词串联在一起，忽略掉
                   list.add(res);
               }
            }else{
                stack.push(ch);
            }
        }
        System.out.println(String.join("|",list));
    }

    private static void test( String source,Character left,Character right ){
        System.out.println("===================");
        parse(source,left,right);
        System.out.println("-------------------");
        ruleParse(source);
        System.out.println("===================");
    }

    public static void main(String[] args) {
        Character left = '(';
        Character right = ')';
        String source = "text:(((\"复星\") AND (\"浙商成长基金\" OR \"复星资本\" OR \"基金管理部\"))  AND ())";
        test(source,left,right);
        source = "text:((() AND (\"复星\" OR \"Fosun\" OR \"郭广昌\")) AND ( NOT \"居士\"))";
        test(source,left,right);

        source = "text:((() AND (\"Clubmed\" OR \"地中海俱乐部\" OR \"亚布力\")) AND ())";
        test(source,left,right);
        source= "text:((() AND (\"Gland And Pharma\"))  AND ())";
        test(source,left,right);
        source = "\"Club Med\" OR \"Amazing Family\" OR \"非凡家庭\"";
        left = right = '"';
        parse(source,left,right);

    }
}
