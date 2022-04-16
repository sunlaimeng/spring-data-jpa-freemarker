package com.example.jpa.querydsl.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author lamen 2022/4/16
 */
public class JpaMysqlDialect extends MySQL5Dialect {

    public JpaMysqlDialect() {
        super();
        this.registerFunction("group_concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(?1)"));
        this.registerFunction("from_unixtime", new SQLFunctionTemplate(StandardBasicTypes.STRING, "from_unixtime(?1, ?2)"));
        this.registerFunction("find_in_set", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "find_in_set(?1, ?2)"));
    }
}