package com.example.demo;

import com.example.jpa.SpringDataJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lamen 2022/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class ExtendsSuperTest {

    /**
     * PECS原则：生产者（Producer）使用extends，消费者（Consumer）使用super。
     * 站在泛型容器的角度，容器是提供对象（生产者）还是吸收对象（消费者）
     */
    @Test
    public void test() {
        // 只能读取
        List<? extends Number> a = new ArrayList<Number>();
        List<? extends Number> b = new ArrayList<Integer>();
        List<? extends Number> c = new ArrayList<Double>();

        // 只能添加
        List<? super Number> d = new ArrayList<Number>();
        List<? super Number> e = new ArrayList<Object>();
    }

}
