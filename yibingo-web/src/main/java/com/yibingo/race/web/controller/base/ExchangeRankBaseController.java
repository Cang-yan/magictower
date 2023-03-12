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

import com.yibingo.race.dal.entity.ExchangeRank;
import com.yibingo.race.core.service.base.ExchangeRankBaseService;
import com.yibingo.race.dal.filterMapper.ExchangeRankFilterMapper;
import com.yibingo.race.dal.putMapper.ExchangeRankPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 交易排行榜
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "exchangerank",
        tags = "交易排行榜"
)
@RestController
@RequestMapping("core/exchangerank")
public class ExchangeRankBaseController {
    @Autowired
    private ExchangeRankBaseService exchangeRankBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "交易排行榜查询",
            notes = "交易排行榜查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerank:info")
    public Map<String,Object> info(@RequestParam String id) {
            ExchangeRank exchangeRank = exchangeRankBaseService.getById(id);

        return Result.success(exchangeRank).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "交易排行榜保存",
            notes = "交易排行榜保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerank:save")
    public Map<String,Object> save(@RequestBody ExchangeRankPutMapper exchangeRankPutMapper){
        exchangeRankBaseService.save(ExchangeRankPutMapper.convertToEntity(exchangeRankPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "交易排行榜修改",
            notes = "交易排行榜修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerank:update")
    public Map<String,Object> update(@RequestBody ExchangeRank exchangeRank){
        exchangeRankBaseService.updateById(exchangeRank);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "交易排行榜批量删除",
            notes = "交易排行榜批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerank:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        exchangeRankBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
