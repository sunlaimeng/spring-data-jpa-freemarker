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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ${tableRemarks}
 *
 * @author ${author} ${createTime}
 */
@Api(tags = "${tableRemarks}")
@Slf4j
@RestController
@RequestMapping(ComunityUrlConstants.BASE_API)
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${classNameLowerCaseFirst}Service;

    /**
     * 保存/修改
     */
    @ApiOperation(value = "${tableRemarks}保存/修改", notes = "保存/修改是同一个接口，当修改时须传入id字段")
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN', 'CUSTOMER_USER')")
    @PostMapping("/${classNameLowerCaseFirst}")
    public ResponseBean save(@RequestBody ${className}Dto ${classNameLowerCaseFirst}Dto) {
        try {
            ${classNameLowerCaseFirst}Service.save(${classNameLowerCaseFirst}Dto);
            return ResponseBean.ok();
        } catch (Exception e) {
            log.error("Save ${className} error.", e);
            return ResponseBean.failed(e.getMessage());
        }
    }

    /**
     * 列表
     */
    @ApiOperation(value = "${tableRemarks}列表", notes = "所有用户都能看到")
    @GetMapping("/${classNameLowerCaseFirst}/list")
    public ResponseBean<PageBean<${className}>> list(${className}Query ${classNameLowerCaseFirst}Query) {
        try {
            PageBean<${className}> page = ${classNameLowerCaseFirst}Service.findPage(${classNameLowerCaseFirst}Query);
            return ResponseBean.ok(page);
        } catch (Exception e) {
            log.error("Query ${className} list error.", e);
            return ResponseBean.failed(e.getMessage());
        }
    }

    /**
     * 详情
     */
    @ApiOperation(value = "${tableRemarks}详情", notes = "通过id查找详情")
    @GetMapping("/${classNameLowerCaseFirst}/details")
    public ResponseBean<${className}Dto> details(
            @ApiParam(value = "${tableRemarks}id")
            @RequestParam String id) {
        try {
            ${className} ${classNameLowerCaseFirst} = ${classNameLowerCaseFirst}Service.findById(id);
            return ResponseBean.ok(${classNameLowerCaseFirst});
        } catch (Exception e) {
            log.error("Query ${className} details error.", e);
            return ResponseBean.failed(e.getMessage());
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "${tableRemarks}删除", notes = "通过id删除")
    @DeleteMapping("/${classNameLowerCaseFirst}")
    public ResponseBean delete(
            @ApiParam(value = "${tableRemarks}id")
            @RequestParam String id) {
        try {
            ${classNameLowerCaseFirst}Service.deleteById(id);
            return ResponseBean.ok();
        } catch (Exception e) {
            log.error("Delete ${className} error.", e);
            return ResponseBean.failed(e.getMessage());
        }
    }
}