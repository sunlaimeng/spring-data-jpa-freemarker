package com.example.jpa.querydsl.entity;

import lombok.Data;

/**
 * @author lamen 2022/4/16
 */
@Data
public class TeacherVo {
    /** 老师id */
    private Integer teacherId;
    /** 老师姓名 */
    private String teacherName;
    /** 学生成绩平均数 */
    private Double scoreAvg;
    /** 学生成绩总数 */
    private Integer scoreSum;
    /** 学生姓名组合 */
    private String groupStudentName;
    /** 时间格式化 */
    private String createTime;
}