package baiduapi;

import java.util.*;

/**
 * @author: liumch
 * @create: 2019/7/21 22:25
 **/

public class HashMapMergeTest {
    public static void main(String[] args) {
        Map<String, Set<String>> m1 = new HashMap<>();
        Map<String, Set<String>> m2 = new HashMap<>();
        Set<String> s1 = new HashSet<>(10);
        Set<String> s2 = new HashSet<>(10);
        Set<String> s3 = new HashSet<>(10);
        s1.addAll(Arrays.asList("1","2","3","10","11","23","13","14"));
        s2.addAll(Arrays.asList("1","2","5","4","7"));
        s3.addAll(Arrays.asList("1","2","5","4","7"));
        m1.put("a",s1);
        m2.put("a",s2);
        m2.put("b",s3);
        m1.forEach((k,v)->m2.merge(k,v,(v1,v2)->{
            v1.addAll(v2);
            List<String> arr = new ArrayList<>(v1);
            arr = arr.subList(0,Math.min(10,v1.size()));
             return new HashSet<>(arr);
        }));
        m2.forEach((k,v)->{
            System.out.println("k:" + k + ",size:" + v.size());
            for (String s : v) {
                System.out.print(s + "|");
            }
            System.out.println("----");
        });

    }
}
