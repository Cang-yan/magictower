package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.UserInviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/7 8:55
 */
@Api(
        value = "用户邀请相关" ,
        tags = "用户邀请相关"
)
@RestController
@RequestMapping("core/invite" )
public class UserInviteController {


    @Autowired
    private UserInviteService userInviteService;


    @ApiOperation(
            value = "获取邀请码" ,
            notes = "获取邀请码"
    )
    @RequestMapping(
            value = "/code" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getCode() {

        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userInviteService.getInviteCode(userId)
        ).map();


    }

    @ApiOperation(
            value = "邀请排行榜" ,
            notes = "邀请排行榜"
    )
    @RequestMapping(
            value = "/rank" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> rankInvitation() {
        String userId = StpUtil.getLoginIdAsString();

        //List<Map<String, Object>> rankListMap = userInviteService.rankInvitation(userId);

//        Map<String, Object> selfMap = rankListMap.stream().filter(
//                item -> item.get("userId" ) != null && item.get("userId" ).toString().equals(userId)
//        ).findFirst().orElse(new HashMap<>());
        return Result.success(
                userInviteService.rankInvitation(userId)
        ).map();

    }

    @ApiOperation(
            value = "邀请排行榜-管理员的" ,
            notes = "邀请排行榜-管理员的"
    )
    @RequestMapping(
            value = "/rank/admin" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> rankInvitationAdmin(@RequestParam Integer count){

        List<Map<String, Object>> maps = userInviteService.rankInvitation("");

        if (count<maps.size()) maps = maps.subList(0,count);

        return Result.success(
                maps
        ).map();
    }
}
