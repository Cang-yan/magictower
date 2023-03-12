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

import com.yibingo.race.dal.entity.Family;
import com.yibingo.race.core.service.base.FamilyBaseService;
import com.yibingo.race.dal.filterMapper.FamilyFilterMapper;
import com.yibingo.race.dal.putMapper.FamilyPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 系列
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "family",
        tags = "系列"
)
@RestController
@RequestMapping("core/family")
public class FamilyBaseController {
    @Autowired
    private FamilyBaseService familyBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "系列查询",
            notes = "系列查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:family:info")
    public Map<String,Object> info(@RequestParam String id) {
            Family family = familyBaseService.getById(id);

        return Result.success(family).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "系列保存",
            notes = "系列保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:family:save")
    public Map<String,Object> save(@RequestBody FamilyPutMapper familyPutMapper){
        familyBaseService.save(FamilyPutMapper.convertToEntity(familyPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "系列修改",
            notes = "系列修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:family:update")
    public Map<String,Object> update(@RequestBody Family family){
        familyBaseService.updateById(family);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "系列批量删除",
            notes = "系列批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:family:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        familyBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
