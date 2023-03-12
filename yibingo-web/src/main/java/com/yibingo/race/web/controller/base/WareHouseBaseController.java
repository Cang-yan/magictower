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

import com.yibingo.race.dal.entity.WareHouse;
import com.yibingo.race.core.service.base.WareHouseBaseService;
import com.yibingo.race.dal.filterMapper.WareHouseFilterMapper;
import com.yibingo.race.dal.putMapper.WareHousePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 藏品仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "warehouse",
        tags = "藏品仓库"
)
@RestController
@RequestMapping("core/warehouse")
public class WareHouseBaseController {
    @Autowired
    private WareHouseBaseService wareHouseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "藏品仓库查询",
            notes = "藏品仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:warehouse:info")
    public Map<String,Object> info(@RequestParam String id) {
            WareHouse wareHouse = wareHouseBaseService.getById(id);

        return Result.success(wareHouse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "藏品仓库保存",
            notes = "藏品仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:warehouse:save")
    public Map<String,Object> save(@RequestBody WareHousePutMapper wareHousePutMapper){
        wareHouseBaseService.save(WareHousePutMapper.convertToEntity(wareHousePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "藏品仓库修改",
            notes = "藏品仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:warehouse:update")
    public Map<String,Object> update(@RequestBody WareHouse wareHouse){
        wareHouseBaseService.updateById(wareHouse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "藏品仓库批量删除",
            notes = "藏品仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:warehouse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        wareHouseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
