package designpattern;

import com.alibaba.fastjson.JSON;

/**
 * @author: liumch
 * @create: 2019/6/13 10:30
 **/

public class PersonBuilder {
    private int age;
    private  String name;
    private  String addr;
    private  boolean hasChild;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public static class Builder{
        private int age;
        private  String name;
        private  String addr;
        private  boolean hasChild;

        public Builder(String name,int age){
             this.name = name;
             this.age = age;
        }


        public  Builder setAddr(String addr){
            this.addr = addr;
            return this;
        }

        public  Builder setHasChild(boolean hasChild){
            this.hasChild = hasChild;
            return this;
        }

        public PersonBuilder build(){
            PersonBuilder personBuilder = new PersonBuilder();
            personBuilder.age = age;
            personBuilder.name = name;
            personBuilder.hasChild =hasChild;
            personBuilder.addr = addr;
            return personBuilder;
        }

    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
    private PersonBuilder(){}


    public static void main(String[] args) {
        PersonBuilder person = new PersonBuilder.Builder("张三",25)
                .setAddr("上海").build();
        System.out.println(person);
        person = new PersonBuilder.Builder("李四",19)
                .setAddr("上海").setHasChild(false).build();
        System.out.println(person);

    }
}
