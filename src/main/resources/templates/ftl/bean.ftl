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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gw.server.common.data.HasCustomerId;
import org.gw.server.common.data.HasName;
import org.gw.server.common.data.HasTenantId;
import org.gw.server.common.data.SearchTextBasedWithAdditionalInfo;
import org.gw.server.common.data.id.CustomerId;
import org.gw.server.common.data.id.TenantId;

@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class ${className} extends SearchTextBasedWithAdditionalInfo<${className}Id> implements HasName, HasTenantId, HasCustomerId {

    private TenantId tenantId;
    private CustomerId customerId;
    <#list fieldConfigList as field>
    <#if field.fieldName == "id">
    <#elseif field.fieldType == "Long">
    /** ${field.fieldRemarks} */
    @ApiModelProperty(value = "${field.fieldRemarks}")
    private long ${field.fieldName};
    <#elseif field.fieldType == "Integer">
    /** ${field.fieldRemarks} */
    @ApiModelProperty(value = "${field.fieldRemarks}")
    private int ${field.fieldName};
    <#else>
    /** ${field.fieldRemarks} */
    @ApiModelProperty(value = "${field.fieldRemarks}")
    private ${field.fieldType} ${field.fieldName};
    </#if>
    </#list>

    public ${className}() {
        super();
    }

    public ${className}(${className}Id id) {
        super(id);
    }

    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public ${className}(${className} ${classNameLowerCaseFirst}) {
        super(${classNameLowerCaseFirst});
        this.tenantId = ${classNameLowerCaseFirst}.getTenantId();
        this.customerId = ${classNameLowerCaseFirst}.getCustomerId();
        <#list fieldConfigList as field>
        <#if field.fieldName == "id">
        <#else>
        this.${field.fieldName} = ${classNameLowerCaseFirst}.get${field.fieldNameUpperCaseFirst}();
        </#if>
        </#list>
    }

    public ${className} convert${className}(${className} ${classNameLowerCaseFirst}, ${className}Dto ${classNameLowerCaseFirst}Dto) {
        <#list fieldConfigList as field>
        <#if field.fieldName == "id">
        <#else>
        ${classNameLowerCaseFirst}.set${field.fieldNameUpperCaseFirst}(${classNameLowerCaseFirst}Dto.get${field.fieldNameUpperCaseFirst}() == null ? ${classNameLowerCaseFirst}.get${field.fieldNameUpperCaseFirst}() : ${classNameLowerCaseFirst}Dto.get${field.fieldNameUpperCaseFirst}());
        </#if>
        </#list>
        return ${classNameLowerCaseFirst};
    }
}