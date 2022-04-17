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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.gw.server.common.data.page.PageBean;
import org.gw.server.dao.DaoUtil;
import org.gw.server.dao.sql.JpaAbstractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Component
public class Jpa${className}Dao2 extends JpaAbstractDao<${className}Entity, ${className}> implements ${className}Dao {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    private ${className}Repository ${classNameLowerCaseFirst}Repository;

    @Override
    protected Class<${className}Entity> getEntityClass() {
        return ${className}Entity.class;
    }

    @Override
    protected CrudRepository<${className}Entity, UUID> getCrudRepository() {
        return ${classNameLowerCaseFirst}Repository;
    }

    @Override
    public PageBean<${className}> findPage(${className}Query ${classNameLowerCaseFirst}Query) {
        Q${className}Entity ${classNameLowerCaseFirst}Entity = Q${className}Entity.${classNameLowerCaseFirst}Entity;

        // condition
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        <#list fieldConfigList as field>
        <#if field.fieldType == "String">
        if (StringUtils.isNotEmpty(${classNameLowerCaseFirst}Query.get${field.fieldNameUpperCaseFirst}())) {
            booleanBuilder.and(${classNameLowerCaseFirst}Entity.${field.fieldName}.eq(${classNameLowerCaseFirst}Query.get${field.fieldNameUpperCaseFirst}()));
        }
        <#else>
        if (${classNameLowerCaseFirst}Query.get${field.fieldNameUpperCaseFirst}() != null) {
            booleanBuilder.and(${classNameLowerCaseFirst}Entity.${field.fieldName}.eq(${classNameLowerCaseFirst}Query.get${field.fieldNameUpperCaseFirst}()));
        }
        </#if>
        </#list>

        // sql
        JPAQuery<${className}Entity> query = queryFactory.select(${classNameLowerCaseFirst}Entity)
                .from(${classNameLowerCaseFirst}Entity)
                .where(booleanBuilder)
                .orderBy(${classNameLowerCaseFirst}Entity.createdTime.desc());

        // result
        Integer currentPage = ${classNameLowerCaseFirst}Query.getCurrentPage() > 0 ? ${classNameLowerCaseFirst}Query.getCurrentPage() - 1 : 0;
        Integer pageSize = ${classNameLowerCaseFirst}Query.getPageSize();
        long totalCount = query.fetchCount();
        List<${className}Entity> dataList = null;
        if (totalCount > 0) {
            dataList = query.offset(currentPage * pageSize).limit(pageSize).fetch();
        }

        PageBean<${className}> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount((int) totalCount);
        pageBean.setData(DaoUtil.convertDataList(dataList));
        return pageBean;
    }

    @Override
    public ${className} findById(String id) {
        return DaoUtil.getData(${classNameLowerCaseFirst}Repository.findById(id));
    }

    @Override
    public void deleteById(String id) {
        ${classNameLowerCaseFirst}Repository.deleteById(id);
    }
}
