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

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gw.community.controller.AbstractControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * ${tableRemarks}
 *
 * @author ${author} ${createTime}
 */
@Slf4j
public class ${className}ControllerTest extends AbstractControllerTest {

    /**
     * 保存
     */
    @Test
    public void save() throws Exception {
        ${className}Dto ${classNameLowerCaseFirst}Dto = new ${className}Dto();
        <#list fieldConfigList as field>
        <#if field.fieldName == "id">
        <#else>
        ${classNameLowerCaseFirst}Dto.set${field.fieldNameUpperCaseFirst}(<#if field.fieldType == "String">""<#elseif field.fieldType == "Long">0L<#else>0</#if>);
        </#if>
        </#list>

        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/community/${apiPath}")
                .contentType(MediaType.APPLICATION_JSON).header("X-Authorization", token)
                .content(JSON.toJSONString(${classNameLowerCaseFirst}Dto))
        ).andReturn().getResponse().getContentAsByteArray();
        String result = new String(bytes);
        log.info("result: {}", result);
    }

    /**
     * 修改
     */
    @Test
    public void edit() throws Exception {
        ${className}Dto ${classNameLowerCaseFirst}Dto = new ${className}Dto();
        <#list fieldConfigList as field>
        ${classNameLowerCaseFirst}Dto.set${field.fieldNameUpperCaseFirst}(<#if field.fieldType == "String">""<#elseif field.fieldType == "Long">0L<#else>0</#if>);
        </#list>

        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/community/${apiPath}")
                .contentType(MediaType.APPLICATION_JSON).header("X-Authorization", token)
                .content(JSON.toJSONString(${classNameLowerCaseFirst}Dto))
        ).andReturn().getResponse().getContentAsByteArray();
        String result = new String(bytes);
        log.info("result: {}", result);
    }

    /**
     * 列表
     */
    @Test
    public void list() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("pageSize", "10");
        params.set("pageNo", "1");

        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/community/${apiPath}/list")
                .contentType(MediaType.APPLICATION_JSON).header("X-Authorization", token)
                .params(params)
        ).andReturn().getResponse().getContentAsByteArray();
        String result = new String(bytes);
        log.info("result: {}", result);
    }

    /**
     * 详情
     */
    @Test
    public void details() throws Exception {
        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/community/${apiPath}/details")
            .contentType(MediaType.APPLICATION_JSON).header("X-Authorization", token)
            .param("id", "40053c00-b18e-11ec-840a-8fd948b5f7d8")
        ).andReturn().getResponse().getContentAsByteArray();
        String result = new String(bytes);
        log.info("result: {}", result);
    }

    /**
     * 删除
     */
    @Test
    public void delete() throws Exception {
        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/community/${apiPath}")
            .contentType(MediaType.APPLICATION_JSON).header("X-Authorization", token)
            .param("id", "40053c00-b18e-11ec-840a-8fd948b5f7d8")
        ).andReturn().getResponse().getContentAsByteArray();
        String result = new String(bytes);
        log.info("result: {}", result);
    }
}