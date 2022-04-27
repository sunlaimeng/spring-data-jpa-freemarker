package com.example.jpa.querydsl.config;

/**
 * mysql方言模板
 *
 * @author lamen 2022/4/20
 */
public interface MySQL8DialectTemplate {

    /** group_concat(?1) */
    String GROUP_CONCAT = "group_concat({0})";

    /** group_concat(distinct ?1 separator '、') */
    String GROUP_CONCAT_DISTINCT_CHINESE_COMMA = "group_concat_distinct_chinese_comma({0})";

    /** group_concat(distinct ?1 separator ',') */
    String GROUP_CONCAT_DISTINCT_ENGLISH_COMMA = "group_concat_distinct_english_comma({0})";

    /** from_unixtime(?1, ?2) */
    String FROM_UNIXTIME_MILLISECOND = "from_unixtime({0}/1000, {1})";

    /** from_unixtime(?1, '%Y-%m-%d %H:%i:%s') */
    String FROM_UNIXTIME_MILLISECOND_YMDHIS = "from_unixtime({0}/1000, '%Y-%m-%d %H:%i:%s')";

    /** find_in_set(?1, ?2) */
    String FIND_IN_SET = "find_in_set({0}, {1})";

    /** any_value(?1) */
    String ANY_VALUE = "any_value({0})";

    /** any_value(?1) */
    static String anyValue(String value) {
        return "any_value(" + value + ")";
    }
}
