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

import com.yibingo.race.dal.entity.BoxHouse;
import com.yibingo.race.core.service.base.BoxHouseBaseService;
import com.yibingo.race.dal.filterMapper.BoxHouseFilterMapper;
import com.yibingo.race.dal.putMapper.BoxHousePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 盲盒仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "boxhouse",
        tags = "盲盒仓库"
)
@RestController
@RequestMapping("core/boxhouse")
public class BoxHouseBaseController {
    @Autowired
    private BoxHouseBaseService boxHouseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "盲盒仓库查询",
            notes = "盲盒仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxhouse:info")
    public Map<String,Object> info(@RequestParam String id) {
            BoxHouse boxHouse = boxHouseBaseService.getById(id);

        return Result.success(boxHouse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "盲盒仓库保存",
            notes = "盲盒仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxhouse:save")
    public Map<String,Object> save(@RequestBody BoxHousePutMapper boxHousePutMapper){
        boxHouseBaseService.save(BoxHousePutMapper.convertToEntity(boxHousePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "盲盒仓库修改",
            notes = "盲盒仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxhouse:update")
    public Map<String,Object> update(@RequestBody BoxHouse boxHouse){
        boxHouseBaseService.updateById(boxHouse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "盲盒仓库批量删除",
            notes = "盲盒仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxhouse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        boxHouseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
