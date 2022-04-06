package com.example.jpa.freemarker.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lamen 2022/4/4
 */
public class Generator {

    /** 包路径 */
    private static final String PACKAGE_PATH = "com.example.jpa.freemarker.demo";
    /** 类文件生成路径 */
    private static final String CLASS_PATH = "src/main/java/com/example/jpa/freemarker/demo";
    /** 模板路径 */
    private static final String TEMPLATE_PATH = "src/main/resources/templates/ftl";
    /** 开发者名称 */
    private static final String AUTHOR = "lamen";

    /**
     * 根据表名生成java文件
     * @param jdbcTemplate
     * @param tableName 表名
     */
    public void generate(JdbcTemplate jdbcTemplate, String tableName) {
        Map<String, Object> map = jdbcTemplate.queryForMap("show create table " + tableName);
        String createTableSql = String.valueOf(map.get("Create Table"));
        generate(createTableSql);
    }

    /**
     * 根据sql生成java文件
     * @param sql 建表语句
     */
    public void generate(String sql) {
        ClassConfig classConfig = getClassConfig(sql);
        Map<String, Object> dataMap = covertMap(classConfig);
        String className = classConfig.getClassName();
        // 生成 ModelConstants
        generatorClassFile(classConfig, dataMap, "modelConstants.ftl", "ModelConstants.java");
        // 生成 EntityType
        generatorClassFile(classConfig, dataMap, "entityType.ftl", "EntityType.java");
        // 生成 Entity
        generatorClassFile(classConfig, dataMap, "entity.ftl", className + "Entity.java");
        // 生成 Bean
        generatorClassFile(classConfig, dataMap, "bean.ftl", className + ".java");
        // 生成 Id
        generatorClassFile(classConfig, dataMap, "id.ftl", className + "Id.java");
        // 生成 Repository
        generatorClassFile(classConfig, dataMap, "repository.ftl", className + "Repository.java");
        // 生成 Helper
        generatorClassFile(classConfig, dataMap, "helper.ftl", className + "Helper.java");
        // 生成 Query
        generatorClassFile(classConfig, dataMap, "query.ftl", className + "Query.java");
        // 生成 Dto
        generatorClassFile(classConfig, dataMap, "dto.ftl", className + "Dto.java");
        // 生成 Controller
        generatorClassFile(classConfig, dataMap, "controller.ftl", className + "Controller.java");
        // 生成 Service
        generatorClassFile(classConfig, dataMap, "service.ftl", className + "Service.java");
        // 生成 ServiceImpl
        generatorClassFile(classConfig, dataMap, "serviceImpl.ftl", className + "ServiceImpl.java");
        // 生成 Dao
        generatorClassFile(classConfig, dataMap, "dao.ftl", className + "Dao.java");
        // 生成 JpaDao
        generatorClassFile(classConfig, dataMap, "jpaDao.ftl", "Jpa" + className + "Dao.java");
    }

    /**
     * 生成java文件
     * @param classConfig 类模板中参数所有配置
     * @param dataMap 配置classConfig转换成map形式
     * @param templateName 模板名称
     * @param fileName 生成类文件名称
     */
    private void generatorClassFile(ClassConfig classConfig, Map<String, Object> dataMap, String templateName, String fileName) {
        // 1. 配置实例
        Configuration configuration = new Configuration(Configuration.getVersion());
        Writer writer = null;
        try {
            // 2. 模版路径
            configuration.setDirectoryForTemplateLoading(new File(classConfig.getTemplatePath()));
            // 3. 模版文件
            Template template = configuration.getTemplate(templateName);
            // 4. java文件路径
            File packagePath = new File(classConfig.getClassPath());
            if (!packagePath.exists()) {
                packagePath.mkdirs();
            }
            File javaFile = new File(classConfig.getClassPath() + "/" + fileName);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // 5. 生成java文件
            template.process(dataMap, writer);

            System.out.println(fileName + " success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * 将模板中需要的配置转成map
     * @param classConfig 模板中参数配置
     * @return
     */
    private Map<String, Object> covertMap(ClassConfig classConfig) {
        return JSONObject.parseObject(JSON.toJSONString(classConfig), new TypeReference<Map<String, Object>>(){});
    }

    /**
     * 将sql解析成配置参数
     * @param sql 创建表语句
     * @return
     */
    private ClassConfig getClassConfig(String sql) {
        String[] sqlArray = sql.split("\n");
        // 建表语句第一行
        String sql0 = sqlArray[0];
        // 表名称
        String tableName = sql0.substring(sql0.indexOf("`") + 1, sql0.lastIndexOf("`"));
        // 表名称别名
        String tableNameAlias = tableName.substring(0, 1);
        // 表名称大写
        String tableNameUpperCase = tableName.toUpperCase();
        // 类文件名称
        String className = underlineToCamelCase(upperCaseFirst(tableName));
        // 类文件名称，首字母小写
        String classNameLowerCaseFirst = underlineToCamelCase(tableName);
        // 字段信息
        List<FieldConfig> fieldConfigList = new ArrayList<>();
        for (int i = 1; i < sqlArray.length; i++) {
            String rowSql = sqlArray[i].trim();
            if (rowSql.contains("KEY")) {
                break;
            }
            FieldConfig fieldConfig = getFieldConfig(rowSql);
            fieldConfigList.add(fieldConfig);
        }
        // 建表语句最后一行
        String sqlN = sqlArray[sqlArray.length - 1];
        // 表备注
        String tableRemarks = "";
        if (sqlN.contains("COMMENT")) {
            tableRemarks = sqlN.substring(sqlN.indexOf("COMMENT='") + "COMMENT='".length(), sqlN.lastIndexOf("'"));
        }
        // 创建时间
        String createTime = DateFormatUtils.format(System.currentTimeMillis(), "yyyy/MM/dd");

        ClassConfig classConfig = new ClassConfig();
        classConfig.setPackagePath(PACKAGE_PATH);
        classConfig.setTemplatePath(TEMPLATE_PATH);
        classConfig.setClassPath(CLASS_PATH);
        classConfig.setAuthor(AUTHOR);
        classConfig.setCreateTime(createTime);
        classConfig.setTableName(tableName);
        classConfig.setTableNameAlias(tableNameAlias);
        classConfig.setTableNameUpperCase(tableNameUpperCase);
        classConfig.setTableRemarks(tableRemarks);
        classConfig.setClassName(className);
        classConfig.setClassNameLowerCaseFirst(classNameLowerCaseFirst);
        classConfig.setFieldConfigList(fieldConfigList);

        return classConfig;
    }

    /**
     * 根据每一行的sql解析成字段信息
     * @param rowSql 每一行的sql
     * @return
     */
    private FieldConfig getFieldConfig(String rowSql) {
        // 表字段名称
        String tableFieldName = rowSql.substring(rowSql.indexOf("`") + 1, rowSql.lastIndexOf("`"));
        // 表字段名称大写
        String tableFieldNameUpperCase = tableFieldName.toUpperCase();
        // 字段名称
        String fieldName = underlineToCamelCase(tableFieldName);
        // 字段名称，首字母大写
        String fieldNameUpperCaseFirst = upperCaseFirst(fieldName);
        // 字段备注
        String fieldRemarks = "";
        if (rowSql.contains("COMMENT")) {
            fieldRemarks = rowSql.substring(rowSql.indexOf("COMMENT '") + "COMMENT '".length(), rowSql.lastIndexOf("',"));
        }
        String[] spaces = rowSql.split(" ");
        String space = spaces[1];
        // 字段类型
        String fieldType = "";
        if (space.contains("varchar") || space.contains("text") || space.contains("char")) {
            fieldType = "String";
        } else if (space.contains("bigint")) {
            fieldType = "Long";
        } else if (space.contains("int")) {
            fieldType = "Integer";
        } else if (space.contains("geometry")) {
            fieldType = "Point";
        } else if (space.contains("json")) {
            fieldType = "JsonNode";
        }
        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setTableFieldName(tableFieldName);
        fieldConfig.setTableFieldNameUpperCase(tableFieldNameUpperCase);
        fieldConfig.setFieldName(fieldName);
        fieldConfig.setFieldNameUpperCaseFirst(fieldNameUpperCaseFirst);
        fieldConfig.setFieldRemarks(fieldRemarks);
        fieldConfig.setFieldType(fieldType);
        return fieldConfig;
    }

    /**
     * 首字母大写
     */
    private String upperCaseFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 下划线，转换为驼峰式
     */
    private String underlineToCamelCase(String s) {
        if (s == null || s.trim().length() == 0) {
            return s;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ('_' == ch) {
                flag = true;
            } else {
                if (flag) {
                    result.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}
