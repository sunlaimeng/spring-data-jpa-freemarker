package com.example.jpa.querydsl.config;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author lamen 2022/4/16
 */
public class JpaMySQL8Dialect extends MySQL8Dialect {

    public JpaMySQL8Dialect() {
        this.registerFunction("group_concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(?1)"));
        this.registerFunction("group_concat_distinct_chinese_comma", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(distinct ?1 separator '、')"));
        this.registerFunction("group_concat_distinct_english_comma", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(distinct ?1 separator ',')"));
        this.registerFunction("from_unixtime", new SQLFunctionTemplate(StandardBasicTypes.STRING, "from_unixtime(?1, ?2)"));
        this.registerFunction("any_value", new SQLFunctionTemplate(StandardBasicTypes.STRING, "any_value(?1)"));
        this.registerFunction("find_in_set", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "find_in_set(?1, ?2)"));
    }
}