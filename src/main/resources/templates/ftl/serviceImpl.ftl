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

import org.gw.server.common.data.id.TenantId;
import org.gw.server.common.data.page.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Dao ${classNameLowerCaseFirst}Dao;

    @Override
    public void save(${className}Dto ${classNameLowerCaseFirst}Dto) {
        ${className} ${classNameLowerCaseFirst} = null;
        if (${classNameLowerCaseFirst}Dto.getId() != null) {
            ${className} ${classNameLowerCaseFirst}ById = ${classNameLowerCaseFirst}Dao.findById(${classNameLowerCaseFirst}Dto.getId());
            if (${classNameLowerCaseFirst}ById != null) {
                ${classNameLowerCaseFirst} = ${classNameLowerCaseFirst}ById.convert${className}(${classNameLowerCaseFirst}ById, ${classNameLowerCaseFirst}Dto);
            }
        }
        if (${classNameLowerCaseFirst} == null) {
            ${classNameLowerCaseFirst} = new ${className}();
            ${classNameLowerCaseFirst} = ${classNameLowerCaseFirst}.convert${className}(${classNameLowerCaseFirst}, ${classNameLowerCaseFirst}Dto);
        }
        ${classNameLowerCaseFirst}Dao.save(TenantId.SYS_TENANT_ID, ${classNameLowerCaseFirst});
    }

    @Override
    public PageBean<${className}> findPage(${className}Query ${classNameLowerCaseFirst}Query) {
        return ${classNameLowerCaseFirst}Dao.findPage(${classNameLowerCaseFirst}Query);
    }

    @Override
    public ${className} findById(String id) {
        return ${classNameLowerCaseFirst}Dao.findById(id);
    }

    @Override
    public void deleteById(String id) {
        ${classNameLowerCaseFirst}Dao.deleteById(id);
    }
}