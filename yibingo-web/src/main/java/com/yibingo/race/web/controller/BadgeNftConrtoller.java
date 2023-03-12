package com.yibingo.race.web.controller;

import cn.hutool.json.JSONObject;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.BadgeNftService;
import com.yibingo.race.dal.filterMapper.BadgeNftFilterMapper;
import com.yibingo.race.dal.putMapper.BadgeNftPutMapper;
import com.yibingo.race.dal.putMapper.BenefitBadgePutMapper;
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
 * @time: 2022/7/15 10:45
 */
@Api(
        value = "徽章操作" ,
        tags = "徽章操作"
)
@RestController
@RequestMapping("core/badge" )
public class BadgeNftConrtoller {

    @Autowired
    private BadgeNftService badgeNftService;

    @ApiOperation(
            value = "搜索条件查询" ,
            notes = "搜索条件查询"
    )
    @RequestMapping(
            value = "/search" ,
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getListByFilter(@RequestParam(value = "updateTimeFrom" , required = false) Long updateTimeFrom,
                                               @RequestParam(value = "updateTimeTo" , required = false) Long updateTimeTo,
                                               @RequestParam(value = "createTimeFrom" , required = false) Long createTimeFrom,
                                               @RequestParam(value = "createTimeTo" , required = false) Long createTimeTo,
                                               @RequestParam(value = "page" , required = false, defaultValue = "1" ) Long page,
                                               @RequestParam(value = "row" , required = false, defaultValue = "10" ) Long row,
                                               @RequestParam(value = "orderBy" , required = false) List<String> orderBy
    ) {
        BadgeNftFilterMapper mapper = new BadgeNftFilterMapper();
        mapper.updateTimeFrom = updateTimeFrom;
        mapper.updateTimeTo = updateTimeTo;
        mapper.createTimeFrom = createTimeFrom;
        mapper.createTimeTo = createTimeTo;
        mapper.page = page;
        mapper.row = row;
        mapper.orderBy = orderBy;

        return Result.success(
                badgeNftService.getBadgeBaseInfo(mapper)
        ).map();
    }


    @ApiOperation(
            value = "创建徽章" ,
            notes = "创建徽章"
    )
    @RequestMapping(
            value = "/create" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> createBadge(@RequestBody JSONObject jsonObject,
                                           @RequestParam Integer totalCount,
                                           @RequestParam Integer reverseCount) {
        BadgeNftPutMapper badgeNftPutMapper = new BadgeNftPutMapper();
        //这应该是前端拿系列的family拿过来
        badgeNftPutMapper.setFamilyId(jsonObject.getStr("familyId" ));
        badgeNftPutMapper.setFamilyName(jsonObject.getStr("familyName" ));
        badgeNftPutMapper.setContractAddress(jsonObject.getStr("contractAddress" ));
        badgeNftPutMapper.setContractAgreement(jsonObject.getStr("contractAgreement" ));
        badgeNftPutMapper.setIntroduce(jsonObject.getStr("introduce" ));
        badgeNftPutMapper.setNickname(jsonObject.getStr("nickname" ));
        badgeNftPutMapper.setUrl(jsonObject.getStr("url" ));

        BenefitBadgePutMapper benefitBadgePutMapper = new BenefitBadgePutMapper();
        benefitBadgePutMapper.setBenefitPrebuyCount(Integer.parseInt(jsonObject.getStr("benefitPrebuyCount" )));
        benefitBadgePutMapper.setIsAll(Integer.parseInt(jsonObject.getStr("benefitIsAll" )));
        benefitBadgePutMapper.setFamilyId(jsonObject.getStr("benefitFamilyId" ));
        benefitBadgePutMapper.setFamilyName(jsonObject.getStr("benefitFamilyName" ));

        badgeNftService.createBadgeNft(badgeNftPutMapper, benefitBadgePutMapper, totalCount, reverseCount);
//        badgeNftService.createBadgeNft(mapperVO.badgeNftPutMapper, mapperVO.benefitBadgePutMapper, totalCount, reverseCount);
        return Result.success().map();
    }


    @ApiOperation(
            value = "取同时拥有指定徽章集合的用户" ,
            notes = "取同时拥有指定徽章集合的用户"
    )
    @RequestMapping(
            value = "/get/all" ,
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> getOwnAllThose(@RequestBody List<String> badgeNftIdList) {

        return Result.success(
                badgeNftService.getOwnAllThose(badgeNftIdList)
        ).map();
    }


}
