package com.yibingo.race.web.controller.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.yibingo.race.dal.entity.PropHourse;
import com.yibingo.race.core.service.base.PropHourseBaseService;
import com.yibingo.race.dal.filterMapper.PropHourseFilterMapper;
import com.yibingo.race.dal.putMapper.PropHoursePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 道具仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "prophourse",
        tags = "道具仓库"
)
@RestController
@RequestMapping("core/prophourse")
public class PropHourseBaseController {
    @Autowired
    private PropHourseBaseService propHourseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "道具仓库查询",
            notes = "道具仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prophourse:info")
    public Map<String,Object> info(@RequestParam String id) {
            PropHourse propHourse = propHourseBaseService.getById(id);

        return Result.success(propHourse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "道具仓库保存",
            notes = "道具仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prophourse:save")
    public Map<String,Object> save(@RequestBody PropHoursePutMapper propHoursePutMapper){
        propHourseBaseService.save(PropHoursePutMapper.convertToEntity(propHoursePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "道具仓库修改",
            notes = "道具仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prophourse:update")
    public Map<String,Object> update(@RequestBody PropHourse propHourse){
        propHourseBaseService.updateById(propHourse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "道具仓库批量删除",
            notes = "道具仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prophourse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        propHourseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
