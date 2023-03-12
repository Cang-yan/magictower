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

import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.core.service.base.MarketBaseService;
import com.yibingo.race.dal.filterMapper.MarketFilterMapper;
import com.yibingo.race.dal.putMapper.MarketPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 商城
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "market",
        tags = "商城"
)
@RestController
@RequestMapping("core/market")
public class MarketBaseController {
    @Autowired
    private MarketBaseService marketBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "商城查询",
            notes = "商城查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:market:info")
    public Map<String,Object> info(@RequestParam String id) {
            Market market = marketBaseService.getById(id);

        return Result.success(market).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "商城保存",
            notes = "商城保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:market:save")
    public Map<String,Object> save(@RequestBody MarketPutMapper marketPutMapper){
        marketBaseService.save(MarketPutMapper.convertToEntity(marketPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "商城修改",
            notes = "商城修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:market:update")
    public Map<String,Object> update(@RequestBody Market market){
        marketBaseService.updateById(market);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "商城批量删除",
            notes = "商城批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:market:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        marketBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
