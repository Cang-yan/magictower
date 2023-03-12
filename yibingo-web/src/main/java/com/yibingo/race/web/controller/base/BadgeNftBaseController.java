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

import com.yibingo.race.dal.entity.BadgeNft;
import com.yibingo.race.core.service.base.BadgeNftBaseService;
import com.yibingo.race.dal.filterMapper.BadgeNftFilterMapper;
import com.yibingo.race.dal.putMapper.BadgeNftPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 徽章藏品
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "badgenft",
        tags = "徽章藏品"
)
@RestController
@RequestMapping("core/badgenft")
public class BadgeNftBaseController {
    @Autowired
    private BadgeNftBaseService badgeNftBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "徽章藏品查询",
            notes = "徽章藏品查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgenft:info")
    public Map<String,Object> info(@RequestParam String id) {
            BadgeNft badgeNft = badgeNftBaseService.getById(id);

        return Result.success(badgeNft).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "徽章藏品保存",
            notes = "徽章藏品保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgenft:save")
    public Map<String,Object> save(@RequestBody BadgeNftPutMapper badgeNftPutMapper){
        badgeNftBaseService.save(BadgeNftPutMapper.convertToEntity(badgeNftPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "徽章藏品修改",
            notes = "徽章藏品修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgenft:update")
    public Map<String,Object> update(@RequestBody BadgeNft badgeNft){
        badgeNftBaseService.updateById(badgeNft);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "徽章藏品批量删除",
            notes = "徽章藏品批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:badgenft:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        badgeNftBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
