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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ${className}Repository extends JpaRepository<${className}Entity, UUID>, JpaSpecificationExecutor<${className}Entity> {

    @Query(value = "select <#list fieldConfigList as field><#if field_has_next>`${field.tableFieldName}`, <#else>`${field.tableFieldName}`</#if></#list> from ${tableName} where is_delete = 'f' and id = :id", nativeQuery = true)
    ${className}Entity findById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update ${tableName} set is_delete = 't' where id = :id", nativeQuery = true)
    void deleteById(@Param("id") String id);

    @Query(value = "select <#list fieldConfigList as field><#if field_has_next>${tableNameAlias}.`${field.tableFieldName}`, <#else>${tableNameAlias}.`${field.tableFieldName}`</#if></#list> " +
            "from ${tableName} ${tableNameAlias} " +
            "where ${tableNameAlias}.is_delete = 'f' " +
            <#list fieldConfigList as field>
            "and if(:${r"#{#"}${classNameLowerCaseFirst}Query.${field.fieldName}} is null, 1 = 1, ${tableNameAlias}.`${field.tableFieldName}` = :${r"#{#"}${classNameLowerCaseFirst}Query.${field.fieldName}}) " +
            </#list>
            "group by ${tableNameAlias}.id order by ${tableNameAlias}.created_time desc",
            countQuery = "select count(${tableNameAlias}.id) " +
                    "from ${tableName} ${tableNameAlias} " +
                    "where ${tableNameAlias}.is_delete = 'f' " +
                    <#list fieldConfigList as field>
                    "and if(:${r"#{#"}${classNameLowerCaseFirst}Query.${field.fieldName}} is null, 1 = 1, ${tableNameAlias}.`${field.tableFieldName}` = :${r"#{#"}${classNameLowerCaseFirst}Query.${field.fieldName}}) " +
                    </#list>
                    "group by ${tableNameAlias}.id",
            nativeQuery = true)
    Page<${className}Entity> findPage(@Param("${classNameLowerCaseFirst}Query") ${className}Query ${classNameLowerCaseFirst}Query, Pageable pageable);
}