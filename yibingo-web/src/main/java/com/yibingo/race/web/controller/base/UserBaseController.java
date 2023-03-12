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

import com.yibingo.race.dal.entity.User;
import com.yibingo.race.core.service.base.UserBaseService;
import com.yibingo.race.dal.filterMapper.UserFilterMapper;
import com.yibingo.race.dal.putMapper.UserPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 用户表
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "user",
        tags = "用户表"
)
@RestController
@RequestMapping("core/user")
public class UserBaseController {
    @Autowired
    private UserBaseService userBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "用户表查询",
            notes = "用户表查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:user:info")
    public Map<String,Object> info(@RequestParam String id) {
            User user = userBaseService.getById(id);

        return Result.success(user).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "用户表保存",
            notes = "用户表保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:user:save")
    public Map<String,Object> save(@RequestBody UserPutMapper userPutMapper){
        userBaseService.save(UserPutMapper.convertToEntity(userPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "用户表修改",
            notes = "用户表修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:user:update")
    public Map<String,Object> update(@RequestBody User user){
        userBaseService.updateById(user);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "用户表批量删除",
            notes = "用户表批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:user:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        userBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
