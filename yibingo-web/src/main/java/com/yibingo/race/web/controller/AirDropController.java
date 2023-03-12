package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.AirDropService;
import com.yibingo.race.core.service.base.UserBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/13 16:04
 */
@Api(
        value = "空投" ,
        tags = "空投"
)
@RestController
@RequestMapping("core/airdrop" )
public class AirDropController {

    @Autowired
    private AirDropService airDropService;

    @Autowired
    private UserBaseService userBaseService;

    @ApiOperation(
            value = "空投藏品" ,
            notes = "空投藏品"
    )
    @RequestMapping(
            value = "/nft" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> dropNft(@RequestParam String metaNftId,
                                       @RequestBody List<Map<String, Object>> userQualifyAirDropList,
                                       @RequestParam Integer totalNeedCount) {
        airDropService.airDropNft(metaNftId, userQualifyAirDropList, totalNeedCount);
        return Result.success().map();
    }


    @ApiOperation(
            value = "空投徽章" ,
            notes = "空投徽章"
    )
    @RequestMapping(
            value = "/badge" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> dropBadge(@RequestParam String badgeNftId,
                                         @RequestBody List<Map<String, Object>> userQualifyAirDropList,
                                         @RequestParam Integer totalNeedCount) {
        airDropService.airDropBadge(badgeNftId, userQualifyAirDropList, totalNeedCount);
        return Result.success().map();

    }

    @ApiOperation(
            value = "空投盲盒" ,
            notes = "空投盲盒"
    )
    @RequestMapping(
            value = "/box" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> dropBox(@RequestParam String boxId,
                                       @RequestBody List<Map<String, Object>> userQualifyAirDropList,
                                       @RequestParam Integer totalNeedCount) {
        airDropService.airDropBox(boxId,userQualifyAirDropList,totalNeedCount);
        return Result.success().map();

    }

    @ApiOperation(
            value = "对指定用户进行空投徽章 藏品" ,
            notes = "对指定用户进行空投徽章 藏品"
    )
    @RequestMapping(
            value = "/specialUser" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> dropSpecial(@RequestBody List<Map<String, Object>> userQualifyAirDropList,
                                           @RequestParam String badgeNftId,
                                           @RequestParam Integer totalCount) {


        airDropService.airDropNftToUserSpecial(userQualifyAirDropList, badgeNftId,totalCount);
        return Result.success().map();
    }


    @ApiOperation(
            value = "对指定用户进行空投盲盒" ,
            notes = "对指定用户进行空投盲盒"
    )
    @RequestMapping(
            value = "/specialUser/box" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> dropBoxSpecial(@RequestBody List<Map<String, Object>> userQualifyAirDropList,
                                           @RequestParam String blindBoxId,
                                           @RequestParam Integer totalCount) {


        airDropService.airDropBoxToUserSpecial(userQualifyAirDropList, blindBoxId,totalCount);
        return Result.success().map();
    }
}
