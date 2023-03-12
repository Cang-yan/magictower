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

import com.yibingo.race.dal.entity.RankSort;
import com.yibingo.race.core.service.base.RankSortBaseService;
import com.yibingo.race.dal.filterMapper.RankSortFilterMapper;
import com.yibingo.race.dal.putMapper.RankSortPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 品质分级,默认概率写进枚举
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "ranksort",
        tags = "品质分级,默认概率写进枚举"
)
@RestController
@RequestMapping("core/ranksort")
public class RankSortBaseController {
    @Autowired
    private RankSortBaseService rankSortBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "品质分级,默认概率写进枚举查询",
            notes = "品质分级,默认概率写进枚举查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:ranksort:info")
    public Map<String,Object> info(@RequestParam String id) {
            RankSort rankSort = rankSortBaseService.getById(id);

        return Result.success(rankSort).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "品质分级,默认概率写进枚举保存",
            notes = "品质分级,默认概率写进枚举保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:ranksort:save")
    public Map<String,Object> save(@RequestBody RankSortPutMapper rankSortPutMapper){
        rankSortBaseService.save(RankSortPutMapper.convertToEntity(rankSortPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "品质分级,默认概率写进枚举修改",
            notes = "品质分级,默认概率写进枚举修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:ranksort:update")
    public Map<String,Object> update(@RequestBody RankSort rankSort){
        rankSortBaseService.updateById(rankSort);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "品质分级,默认概率写进枚举批量删除",
            notes = "品质分级,默认概率写进枚举批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:ranksort:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        rankSortBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
