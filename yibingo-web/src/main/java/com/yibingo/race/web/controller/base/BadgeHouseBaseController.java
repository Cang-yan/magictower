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

import com.yibingo.race.dal.entity.BadgeHouse;
import com.yibingo.race.core.service.base.BadgeHouseBaseService;
import com.yibingo.race.dal.filterMapper.BadgeHouseFilterMapper;
import com.yibingo.race.dal.putMapper.BadgeHousePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 徽章仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "badgehouse",
        tags = "徽章仓库"
)
@RestController
@RequestMapping("core/badgehouse")
public class BadgeHouseBaseController {
    @Autowired
    private BadgeHouseBaseService badgeHouseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "徽章仓库查询",
            notes = "徽章仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgehouse:info")
    public Map<String,Object> info(@RequestParam String id) {
            BadgeHouse badgeHouse = badgeHouseBaseService.getById(id);

        return Result.success(badgeHouse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "徽章仓库保存",
            notes = "徽章仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgehouse:save")
    public Map<String,Object> save(@RequestBody BadgeHousePutMapper badgeHousePutMapper){
        badgeHouseBaseService.save(BadgeHousePutMapper.convertToEntity(badgeHousePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "徽章仓库修改",
            notes = "徽章仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgehouse:update")
    public Map<String,Object> update(@RequestBody BadgeHouse badgeHouse){
        badgeHouseBaseService.updateById(badgeHouse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "徽章仓库批量删除",
            notes = "徽章仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgehouse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        badgeHouseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
