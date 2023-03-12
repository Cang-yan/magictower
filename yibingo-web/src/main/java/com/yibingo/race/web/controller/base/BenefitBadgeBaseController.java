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

import com.yibingo.race.dal.entity.BenefitBadge;
import com.yibingo.race.core.service.base.BenefitBadgeBaseService;
import com.yibingo.race.dal.filterMapper.BenefitBadgeFilterMapper;
import com.yibingo.race.dal.putMapper.BenefitBadgePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 徽章权益表
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "benefitbadge",
        tags = "徽章权益表"
)
@RestController
@RequestMapping("core/benefitbadge")
public class BenefitBadgeBaseController {
    @Autowired
    private BenefitBadgeBaseService benefitBadgeBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "徽章权益表查询",
            notes = "徽章权益表查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefitbadge:info")
    public Map<String,Object> info(@RequestParam String id) {
            BenefitBadge benefitBadge = benefitBadgeBaseService.getById(id);

        return Result.success(benefitBadge).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "徽章权益表保存",
            notes = "徽章权益表保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefitbadge:save")
    public Map<String,Object> save(@RequestBody BenefitBadgePutMapper benefitBadgePutMapper){
        benefitBadgeBaseService.save(BenefitBadgePutMapper.convertToEntity(benefitBadgePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "徽章权益表修改",
            notes = "徽章权益表修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefitbadge:update")
    public Map<String,Object> update(@RequestBody BenefitBadge benefitBadge){
        benefitBadgeBaseService.updateById(benefitBadge);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "徽章权益表批量删除",
            notes = "徽章权益表批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefitbadge:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        benefitBadgeBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
