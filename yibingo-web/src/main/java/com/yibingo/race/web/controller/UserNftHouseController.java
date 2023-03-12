package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.UserNftHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-01 9:11
 */
@Api(
        value = "用户仓库信息查询",
        tags = "用户仓库信息查询"
)
@RestController
@RequestMapping("core/user/house")
public class UserNftHouseController {


    @Autowired
    private UserNftHouseService userNftHouseService;

    @ApiOperation(
            value = "查询拥有的英雄藏品",
            notes = "查询拥有的英雄藏品"
    )
    @RequestMapping(
            value = "/metaNft/hero",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> hero() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectNftHeroList(userId)
        ).map();
    }

    @ApiOperation(
            value = "查询拥有的装备藏品",
            notes = "查询拥有的装备藏品"
    )
    @RequestMapping(
            value = "/metaNft/equip",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> equip() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectNftEquipList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询拥有的盲盒",
            notes = "查询拥有的盲盒"
    )
    @RequestMapping(
            value = "/box",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> box() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectBoxList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询拥有的道具",
            notes = "查询拥有的道具"
    )
    @RequestMapping(
            value = "/prop",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin

    public Map<String, Object> prop() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectPropList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询拥有的徽章",
            notes = "查询拥有的徽章"
    )
    @RequestMapping(
            value = "/badge",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> badge() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectBadgeList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询拥有的碎片",
            notes = "查询拥有的碎片"
    )
    @RequestMapping(
            value = "/piece",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> piece() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftHouseService.selectPieceList(userId)
        ).map();
    }
}
