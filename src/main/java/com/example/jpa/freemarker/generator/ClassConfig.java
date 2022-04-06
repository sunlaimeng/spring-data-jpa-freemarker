package com.example.jpa.freemarker.generator;

import lombok.Data;

import java.util.List;

/**
 * 类
 * @author lamen 2022/4/4
 */
@Data
public class ClassConfig {
    /** 包路径 */
    private String packagePath;
    /** 模板路径 */
    private String templatePath;
    /** java类文件生成路径 */
    private String classPath;
    /** 类文件名称 */
    private String className;
    /** 类文件名称，首字母小写 */
    private String classNameLowerCaseFirst;
    /** 开发者姓名 */
    private String author;
    /** 开发时间 */
    private String createTime;
    /** 表名称 */
    private String tableName;
    /** 表名称别名 */
    private String tableNameAlias;
    /** 表名称大写 */
    private String tableNameUpperCase;
    /** 表注释 */
    private String tableRemarks;
    /** 字段信息 */
    private List<FieldConfig> fieldConfigList;
}
