package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.jpa.SpringDataJpaApplication;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lamen 2022/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class StreamTest {

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "张三");
        map.put("b", "李四");
        map.put("c", "王五");

        String name = "a,b,c";
        String[] names = name.split(",");

        // 转成字符串
        String value = Arrays.stream(names).distinct().map(s -> map.get(s)).collect(Collectors.joining("、", "[", "]"));
        System.out.println(value);

        // 转成集合
        List<String> list = Arrays.stream(names).map(s -> map.get(s)).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));

        // 转成map
        List<User> userList = new ArrayList<>();
        userList.add(new User(null, "张三", 18));
        userList.add(new User(1, "李四", 15));
        userList.add(new User(2, "王五", 20));
        Map<String, Integer> collect = userList.stream().filter(user -> user.getId() != null).collect(Collectors.toMap(User::getName, User::getAge));
        System.out.println(JSON.toJSONString(collect));
    }

    @Data
    @AllArgsConstructor
    private class User {
        private Integer id;
        private String name;
        private int age;
    }


    @Test
    public void test2() {
        List<Student> list = Lists.newArrayList();
        list.add(new Student("测试", "男", 18));
        list.add(new Student("开发", "男", 20));
        list.add(new Student("运维", "女", 19));
        list.add(new Student("DBA", "女", 22));
        list.add(new Student("运营", "男", 24));
        list.add(new Student("产品", "女", 21));
        list.add(new Student("经理", "女", 25));
        list.add(new Student("产品", "女", 21));

        // 求性别为男的学生集合
        List<Student> l1 = list.stream().filter(student -> student.sex.equals("男")).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(l1));

        // map的key值true为男，false为女的集合
        Map<Boolean, List<Student>> map = list.stream().collect(Collectors.partitioningBy(student -> student.getSex().equals("男")));
        System.out.println(JSON.toJSONString(map));

        // 求性别为男的学生总岁数
        Integer sum = list.stream().filter(student -> student.sex.equals("男")).mapToInt(Student::getAge).sum();
        System.out.println(sum);

        // 按性别进行分组统计人数
        Map<String, Integer> map1 = list.stream().collect(Collectors.groupingBy(Student::getSex, Collectors.summingInt(p -> 1)));
        System.out.println(JSON.toJSONString(map1));

        // 判断是否有年龄大于25岁的学生
        boolean check = list.stream().anyMatch(student -> student.getAge() > 25);
        System.out.println(check);

        // 获取所有学生的姓名集合
        List<String> l2 = list.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(l2));

        // 求所有人的平均年龄
        double avg = list.stream().collect(Collectors.averagingInt(Student::getAge));
        System.out.println(avg);

        // 求年龄最大的学生
        Student s = list.stream().reduce((student, student2) -> student.getAge() > student2.getAge() ? student : student2).get();
        System.out.println(JSON.toJSONString(s));

        Student stu = list.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getAge))).get();
        System.out.println(JSON.toJSONString(stu));

        // 按照年龄从大到小排序
        List<Student> l3 = list.stream().sorted((s1, s2) -> s2.getAge().compareTo(s1.getAge())).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(l3));

        // 求年龄最大的两个学生
        List<Student> l4 = l3.stream().limit(2).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(l4));

        // 获取所有的名字，组成一条语句
        String str = list.stream().map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(str);

        // 获取年龄的最大值、最小值、平均值、求和等等
        IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(Student::getAge).summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getCount());
    }

    @Data
    @AllArgsConstructor
    private class Student {
        String name;
        String sex;
        Integer age;
    }
}
