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

import com.yibingo.race.dal.entity.UserAuth;
import com.yibingo.race.core.service.base.UserAuthBaseService;
import com.yibingo.race.dal.filterMapper.UserAuthFilterMapper;
import com.yibingo.race.dal.putMapper.UserAuthPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 用户权限值
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "userauth",
        tags = "用户权限值"
)
@RestController
@RequestMapping("core/userauth")
public class UserAuthBaseController {
    @Autowired
    private UserAuthBaseService userAuthBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "用户权限值查询",
            notes = "用户权限值查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userauth:info")
    public Map<String,Object> info(@RequestParam String id) {
            UserAuth userAuth = userAuthBaseService.getById(id);

        return Result.success(userAuth).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "用户权限值保存",
            notes = "用户权限值保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userauth:save")
    public Map<String,Object> save(@RequestBody UserAuthPutMapper userAuthPutMapper){
        userAuthBaseService.save(UserAuthPutMapper.convertToEntity(userAuthPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "用户权限值修改",
            notes = "用户权限值修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userauth:update")
    public Map<String,Object> update(@RequestBody UserAuth userAuth){
        userAuthBaseService.updateById(userAuth);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "用户权限值批量删除",
            notes = "用户权限值批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userauth:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        userAuthBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
