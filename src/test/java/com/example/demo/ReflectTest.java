package com.example.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lamen 2022/8/15
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        // 获取class对象
        Class<?> clazz = Person.class;
        // 获取私有无参构造
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        // 创建实例对象
        Object object = constructor.newInstance();

        // 获取方法（有参）
        Method method = clazz.getDeclaredMethod("getName", String.class);
        // 设置方法的可见性
        method.setAccessible(true);
        // 方法执行
        method.invoke(object, "张三");

        // 获取方法（无参）
        method = clazz.getDeclaredMethod("getName");
        // 设置方法的可见性
        method.setAccessible(true);
        // 方法执行
        Object invoke = method.invoke(object);
        System.out.println("方法返回值：" + invoke);

        // 获取字段
        Field field = clazz.getDeclaredField("name");
        // 设置字段的可见性
        field.setAccessible(true);
        // 获取字段值
        Object value = field.get(object);
        System.out.println("字段值：" + value);
    }
}

class Person {
    private String name = "abc";
    private void getName(String name) {
        System.out.println("方法参数：" + name);
    }
    private String getName() {
        return "hello";
    }
}
