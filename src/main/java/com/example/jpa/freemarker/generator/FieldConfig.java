package com.example.jpa.freemarker.generator;

import lombok.Data;

/**
 * 字段
 * @author lamen 2022/4/4
 */
@Data
public class FieldConfig {
    /** 字段名称 */
    private String fieldName;
    /** 字段名称，首字母大写 */
    private String fieldNameUpperCaseFirst;
    /** 字段备注 */
    private String fieldRemarks;
    /** 字段类型 */
    private String fieldType;
    /** 表字段名称 */
    private String tableFieldName;
    /** 表字段名称大写 */
    private String tableFieldNameUpperCase;
}
