package collection;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试Arrays.asList返回的结果是否会被修改
 * @author liumch
 * create :  2019/9/4 15:37
 **/
@Getter
@Setter
@AllArgsConstructor
class StudentList{
    private String name;
    private Integer age;
    private String addr;
    private String idCode;
}

public class ArrayAsListTest {
    private final static int NUM = 1000000;
    public static void testOutFor(){

        System.out.println(new Date());
        System.out.println("外循环系统剩余内存：" + Runtime.getRuntime().freeMemory());
        StudentList st;
        List<StudentList> list = new ArrayList<>(NUM);
        for (int i = 0; i < NUM; i++) {
            st = new StudentList("张珊"+i,1+i,"上海市徐汇区宜山路10903号","430231190001311372");
            list.add(st);
        }
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("处理结束,外循环系统剩余内存：" + Runtime.getRuntime().freeMemory());

        System.out.println(new Date());
    }


    public static void testInFor(){

        System.out.println(new Date());
        System.out.println("内循环系统剩余内存：" + Runtime.getRuntime().freeMemory());
         List<StudentList> list = new ArrayList<>(NUM);
        for (int i = 0; i < NUM; i++) {
            StudentList st = new StudentList("张珊"+i,1+i,"上海市徐汇区宜山路10903号","430231190001311372");
            list.add(st);
        }
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("处理结束,内循环系统剩余内存：" + Runtime.getRuntime().freeMemory());
        System.out.println(new Date());
    }
    public static void main(String[] args) {

        int[] arr = new int[]{122836,129899,  116703, 109305,117140,105271,97077,99749,63381};
        int[] arr1 = new int[]{209018,174627,163319,120667,95450,93328,76686,28266};
        System.out.println(Arrays.stream(arr).sum());
        System.out.println(Arrays.stream(arr1).sum());

//        testOutFor();
//        testInFor();

    }


}
