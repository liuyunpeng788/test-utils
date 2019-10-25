package collection;

import java.util.*;

/**
 * Set 集合类测试
 * 测试set元素的重复监测方法，是用equal还是==
 * @author: liumch
 * @create: 2019/7/8 11:16
 **/

class Student{
    private int age;
    private String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}

public class SetTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A","B");
        System.out.println(list.toString());

        Set<Student> set = new HashSet();
        Student st = new Student(10,"test1");
        set.add(st);
        Student st1 = new Student(1,"test1");
        set.add(st1);
//        assert (st1 == st);
        System.out.println(set.size());

        List<Integer> values = Arrays.asList(1,2,3,4,5);
        ListIterator<Integer> it = values.listIterator();
        while(it.hasNext()){
//            Integer v = it.next();
            it.set(it.next() + 1);
        }
        values.stream().forEach(System.out::print);
    }
}
