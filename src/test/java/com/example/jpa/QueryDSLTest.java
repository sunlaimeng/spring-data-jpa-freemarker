package com.example.jpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.jpa.querydsl.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author lamen 2022/4/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class QueryDSLTest {

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 基本
     */
    @Test
    public void base() {
        QTeacher teacher = QTeacher.teacher;
        QStudent student = QStudent.student;

        Long count = queryFactory.select(teacher.id.count()).from(teacher).fetchOne();
        System.out.println(count);

        Integer sum = queryFactory.select(teacher.id.sum()).from(teacher).fetchOne();
        System.out.println(sum);

        Double avg = queryFactory.select(teacher.id.avg()).from(teacher).fetchOne();
        System.out.println(avg);

        Integer max = queryFactory.select(teacher.id.max()).from(teacher).fetchOne();
        System.out.println(max);

        List<Teacher> t1 = queryFactory.select(teacher).from(teacher).groupBy(teacher.name).fetch();
        System.out.println(JSON.toJSONString(t1));

        List<Teacher> t2 = queryFactory.select(teacher).from(teacher).where(teacher.name.eq("张老师")).fetch();
        System.out.println(JSON.toJSONString(t2));

        List<Tuple> t3 = queryFactory.select(teacher, student).from(teacher)
                .leftJoin(student).on(student.teacherId.eq(teacher.id))
                .where(teacher.name.eq("张老师")).fetch();
        for (Tuple tuple : t3) {
            Teacher t = tuple.get(teacher);
            Student s = tuple.get(student);
            System.out.println(JSON.toJSONString(t));
            System.out.println(JSON.toJSONString(s));
        }
    }

    /**
     * 分页
     */
    @Test
    public void page() {
        QTeacher teacher = QTeacher.teacher;
        QStudent student = QStudent.student;

        int currentPage = 1;
        int pageSize = 10;
        String name = null;
        Integer id = null;

        // 查询条件
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            booleanBuilder.and(teacher.name.like("%" + name + "%"));
        }
        if (id != null) {
            booleanBuilder.and(teacher.id.eq(id));
        }

        // sql语句
        JPAQuery<TeacherVo> query = queryFactory.select(
                Projections.bean(TeacherVo.class,
                        teacher.id, teacher.name,
                        student.id.as("studentId"), student.name.as("studentName")))
                .from(teacher)
                .leftJoin(student).on(student.teacherId.eq(teacher.id))
                .where(booleanBuilder)
                .groupBy(teacher.name)
                .orderBy(teacher.id.desc());

        // 分页结果
        List<TeacherVo> list = query.offset((currentPage - 1) * pageSize).limit(pageSize).fetch();
        // 获取总数
        long count = query.fetchCount();

        System.out.println(count);
        for (TeacherVo teacherBean : list) {
            System.out.println(JSON.toJSONString(teacherBean, SerializerFeature.WriteMapNullValue));
        }
    }

    /**
     * 自定义函数
     */
    @Test
    public void custom() {
        QTeacher teacher = QTeacher.teacher;
        QStudent student = QStudent.student;

        // 查询入参
        int currentPage = 1;
        int pageSize = 10;
        Integer id = null;
        String name = null;
        String studentNameList = "张三,李四,赵六";

        // where条件
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (id != null) {
            booleanBuilder.and(teacher.id.eq(id));
        }
        if (StringUtils.isNotEmpty(name)) {
            booleanBuilder.and(teacher.name.like("%" + name + "%"));
        }
        if (StringUtils.isNotEmpty(studentNameList)) {
            booleanBuilder.and(Expressions.numberTemplate(Integer.class, "find_in_set({0}, {1})", student.name, studentNameList).gt(0));
        }

        // 字段转化
        StringTemplate groupStudentName = Expressions.stringTemplate("group_concat({0})", student.name);
        StringTemplate createTime = Expressions.stringTemplate("from_unixtime({0}/1000, '%Y-%m-%d %H:%i:%s')", teacher.createTime);

        // sql语句
        JPAQuery<TeacherVo> query = queryFactory.select(
                Projections.bean(TeacherVo.class,
                        teacher.id.as("teacherId"), teacher.name.as("teacherName"),
                        student.score.avg().as("scoreAvg"), student.score.sum().as("scoreSum"),
                        groupStudentName.as("groupStudentName"), createTime.as("createTime")))
                .from(teacher)
                .leftJoin(student).on(student.teacherId.eq(teacher.id))
                .where(booleanBuilder)
                .groupBy(teacher.name)
                .orderBy(teacher.id.desc(), teacher.createTime.desc());

        // 分页结果
        List<TeacherVo> list = query.offset((currentPage - 1) * pageSize).limit(pageSize).fetch();

        // 获取总数
        long count = query.fetchCount();

        System.out.println(count);
        for (TeacherVo teacherBean : list) {
            System.out.println(JSON.toJSONString(teacherBean, SerializerFeature.WriteMapNullValue));
        }
    }
}