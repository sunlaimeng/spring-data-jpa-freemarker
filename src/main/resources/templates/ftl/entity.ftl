/**
 * Copyright © 2022-2022 The GW Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ${packagePath};

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gw.server.dao.model.BaseSqlEntity;
import org.gw.server.dao.util.mapping.JsonStringType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * ${tableRemarks}
 *
 * @author ${author} ${createTime}
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = ModelConstants.${tableNameUpperCase}_NAME)
public class ${className}Entity extends BaseSqlEntity<${className}> {

    <#list fieldConfigList as field>
    <#if field.fieldName == "id">
    <#elseif field.fieldName == "createdTime">
    /** ${field.fieldRemarks} */
    @CreatedDate
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private long ${field.fieldName};
    <#elseif field.fieldName == "updateTime">
    /** ${field.fieldRemarks} */
    @LastModifiedDate
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private long ${field.fieldName};
    <#elseif field.fieldType == "JsonNode">
    /** ${field.fieldRemarks} */
    @Type(type = "json")
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private ${field.fieldType} ${field.fieldName};
    <#elseif field.fieldType == "Long">
    /** ${field.fieldRemarks} */
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private long ${field.fieldName};
    <#elseif field.fieldType == "Integer">
    /** ${field.fieldRemarks} */
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private int ${field.fieldName};
    <#else>
    /** ${field.fieldRemarks} */
    @Column(name = ModelConstants.${tableNameUpperCase}_${field.tableFieldNameUpperCase}_PROPERTY)
    private ${field.fieldType} ${field.fieldName};
    </#if>
    </#list>

    public ${className}Entity() {
    }

    public ${className}Entity(${className} ${classNameLowerCaseFirst}) {
        if (${classNameLowerCaseFirst}.getId() != null) {
            this.setUuid(${classNameLowerCaseFirst}.getId().getId());
        }
        <#list fieldConfigList as field>
        <#if field.fieldName == "id">
        <#else>
        this.${field.fieldName} = ${classNameLowerCaseFirst}.get${field.fieldNameUpperCaseFirst}();
        </#if>
        </#list>
    }

    @Override
    public ${className} toData() {
        ${className} ${classNameLowerCaseFirst} = new ${className}(new ${className}Id(this.getUuid()));
        <#list fieldConfigList as field>
        <#if field.fieldName == "id">
        <#else>
        ${classNameLowerCaseFirst}.set${field.fieldNameUpperCaseFirst}(${field.fieldName});
        </#if>
        </#list>
        return ${classNameLowerCaseFirst};
    }
}