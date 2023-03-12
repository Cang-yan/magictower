package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.UserNftRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-06-29 9:16
 */
@Api(
        value = "用户各种记录查询" ,
        tags = "用户各种记录查询"
)
@RestController
@RequestMapping("core/user/records" )
public class UserNftRecordsController {

    @Autowired
    private UserNftRecordsService userNftRecordsService;


    @ApiOperation(
            value = "查询邀请记录" ,
            notes = "查询邀请记录"
    )
    @RequestMapping(
            value = "/invitation" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> invitation() {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                userNftRecordsService.selectInvitationList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询合成记录" ,
            notes = "查询合成记录"
    )
    @RequestMapping(
            value = "/pieceCom" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> pieceCom(@RequestParam("userId" ) String userId) {
        return Result.success(
                userNftRecordsService.selectPieceComList(userId)
        ).map();
    }


    @ApiOperation(
            value = "查询藏品交易记录" ,
            notes = "查询藏品交易记录"
    )
    @RequestMapping(
            value = "/exchange" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> exchange(@RequestParam("userId" ) String userId) {
        return Result.success(
                userNftRecordsService.selectExchangeList(userId)
        ).map();
    }

    @ApiOperation(
            value = "查询藏品转增给我记录" ,
            notes = "查询藏品转增给我记录"
    )
    @RequestMapping(
            value = "/transfer" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> transferIn(){
        String userId = StpUtil.getLoginIdAsString();
        return Result.success(
                userNftRecordsService.selectTransferInList(userId)
        ).map();
    }

    @ApiOperation(
            value = "查询藏品转增给别人的记录" ,
            notes = "查询藏品转增给别人的记录"
    )
    @RequestMapping(
            value = "/transfer/out" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> transferOut(){
        String userId = StpUtil.getLoginIdAsString();
        return Result.success(
                userNftRecordsService.selectTransferOutList(userId)
        ).map();
    }

    @ApiOperation(
            value = "查询盲盒购买记录" ,
            notes = "查询盲盒购买记录"
    )
    @RequestMapping(
            value = "/exchangeBox" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> exchangeBox(@RequestParam("userId" ) String userId) {
        return Result.success(
                userNftRecordsService.selectExchangeBoxList(userId)
        ).map();
    }


}
