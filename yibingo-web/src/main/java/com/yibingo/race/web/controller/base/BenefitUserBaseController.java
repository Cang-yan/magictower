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

import com.yibingo.race.dal.entity.BenefitUser;
import com.yibingo.race.core.service.base.BenefitUserBaseService;
import com.yibingo.race.dal.filterMapper.BenefitUserFilterMapper;
import com.yibingo.race.dal.putMapper.BenefitUserPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 用户获得的权益表
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "benefituser",
        tags = "用户获得的权益表"
)
@RestController
@RequestMapping("core/benefituser")
public class BenefitUserBaseController {
    @Autowired
    private BenefitUserBaseService benefitUserBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "用户获得的权益表查询",
            notes = "用户获得的权益表查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefituser:info")
    public Map<String,Object> info(@RequestParam String id) {
            BenefitUser benefitUser = benefitUserBaseService.getById(id);

        return Result.success(benefitUser).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "用户获得的权益表保存",
            notes = "用户获得的权益表保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefituser:save")
    public Map<String,Object> save(@RequestBody BenefitUserPutMapper benefitUserPutMapper){
        benefitUserBaseService.save(BenefitUserPutMapper.convertToEntity(benefitUserPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "用户获得的权益表修改",
            notes = "用户获得的权益表修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefituser:update")
    public Map<String,Object> update(@RequestBody BenefitUser benefitUser){
        benefitUserBaseService.updateById(benefitUser);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "用户获得的权益表批量删除",
            notes = "用户获得的权益表批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:benefituser:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        benefitUserBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
