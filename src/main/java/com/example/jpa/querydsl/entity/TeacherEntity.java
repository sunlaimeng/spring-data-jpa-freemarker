package com.example.jpa.querydsl.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "teacher")
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Long createTime;
}
