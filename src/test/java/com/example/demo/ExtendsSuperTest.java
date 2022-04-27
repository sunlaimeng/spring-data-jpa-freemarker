package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.jpa.SpringDataJpaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 经常往外读取的，适合用上界 extends
 * 经常往里插入的，适合用下界 super
 *
 * extends可以用于返回类型限定
 * super可以用于参数类型限定，不能用于返回类型限定
 *
 *
 * A extends B，则 B 是 A 的父类或者祖先
 * 若参数为T<? extends B>，意味着子类是不确定的，因此只能读取数据，而不能插入数据，即get()和set()方法中只能是get()有效
 *
 * A super B，则 A 是 B 的父类或者祖先
 * 若参数为T<? super B>，意味着父类是不确定的，因此只能插入数据，而不能读取数据，即get()和set()方法中只能是set()有效
 *
 * @author lamen 2022/4/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class ExtendsSuperTest {

    @Test
    public void testExtends() {
        // <? extends T> 上界通配符，不能存，只能取
        Plate<? extends Fruit> p = new Plate<>(new Apple());
        Fruit fruit = p.get();
        System.out.println(JSON.toJSONString(fruit));
    }

    @Test
    public void testSuper() {
        // <? super T> 下界通配符，可以存，取只能放在Object里
        Plate<? super Fruit> p = new Plate<>(new Fruit());
        p.set(new Fruit());
        p.set(new Apple());
        Object object = p.get();
        System.out.println(JSON.toJSONString(object));
    }

    private class Food {}
    private class Fruit extends Food {}
    private class Apple extends Fruit {}
    private class Plate<T> {
        private T item;
        public Plate(T t) {
            item = t;
        }
        public void set(T t) {
            item = t;
        }
        public T get() {
            return item;
        }
    }
}
