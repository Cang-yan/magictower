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

import com.yibingo.race.dal.entity.UserInvite;
import com.yibingo.race.core.service.base.UserInviteBaseService;
import com.yibingo.race.dal.filterMapper.UserInviteFilterMapper;
import com.yibingo.race.dal.putMapper.UserInvitePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 用户邀请码
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "userinvite",
        tags = "用户邀请码"
)
@RestController
@RequestMapping("core/userinvite")
public class UserInviteBaseController {
    @Autowired
    private UserInviteBaseService userInviteBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "用户邀请码查询",
            notes = "用户邀请码查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userinvite:info")
    public Map<String,Object> info(@RequestParam String id) {
            UserInvite userInvite = userInviteBaseService.getById(id);

        return Result.success(userInvite).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "用户邀请码保存",
            notes = "用户邀请码保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userinvite:save")
    public Map<String,Object> save(@RequestBody UserInvitePutMapper userInvitePutMapper){
        userInviteBaseService.save(UserInvitePutMapper.convertToEntity(userInvitePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "用户邀请码修改",
            notes = "用户邀请码修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userinvite:update")
    public Map<String,Object> update(@RequestBody UserInvite userInvite){
        userInviteBaseService.updateById(userInvite);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "用户邀请码批量删除",
            notes = "用户邀请码批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:userinvite:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        userInviteBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
