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

import org.gw.server.common.data.page.PageBean;
import org.gw.server.dao.DaoUtil;
import org.gw.server.dao.sql.JpaAbstractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Jpa${className}Dao extends JpaAbstractDao<${className}Entity, ${className}> implements ${className}Dao {

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
        return getPageBean(query, true, pageable -> ${classNameLowerCaseFirst}Repository.findPage(${classNameLowerCaseFirst}Query, pageable));
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
