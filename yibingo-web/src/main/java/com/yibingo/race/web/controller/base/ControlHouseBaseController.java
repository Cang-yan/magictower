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

import com.yibingo.race.dal.entity.ControlHouse;
import com.yibingo.race.core.service.base.ControlHouseBaseService;
import com.yibingo.race.dal.filterMapper.ControlHouseFilterMapper;
import com.yibingo.race.dal.putMapper.ControlHousePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 管理员仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "controlhouse",
        tags = "管理员仓库"
)
@RestController
@RequestMapping("core/controlhouse")
public class ControlHouseBaseController {
    @Autowired
    private ControlHouseBaseService controlHouseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "管理员仓库查询",
            notes = "管理员仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:controlhouse:info")
    public Map<String,Object> info(@RequestParam String id) {
            ControlHouse controlHouse = controlHouseBaseService.getById(id);

        return Result.success(controlHouse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "管理员仓库保存",
            notes = "管理员仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:controlhouse:save")
    public Map<String,Object> save(@RequestBody ControlHousePutMapper controlHousePutMapper){
        controlHouseBaseService.save(ControlHousePutMapper.convertToEntity(controlHousePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "管理员仓库修改",
            notes = "管理员仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:controlhouse:update")
    public Map<String,Object> update(@RequestBody ControlHouse controlHouse){
        controlHouseBaseService.updateById(controlHouse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "管理员仓库批量删除",
            notes = "管理员仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:controlhouse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        controlHouseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
