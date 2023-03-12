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

import com.yibingo.race.dal.entity.BuyOrder;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.dal.filterMapper.BuyOrderFilterMapper;
import com.yibingo.race.dal.putMapper.BuyOrderPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 购买订单
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "buyorder",
        tags = "购买订单"
)
@RestController
@RequestMapping("core/buyorder")
public class BuyOrderBaseController {
    @Autowired
    private BuyOrderBaseService buyOrderBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

     @ApiOperation(
            value = "购买订单查询",
            notes = "购买订单查询"
    )
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:buyorder:info")
    public Map<String,Object> info(@RequestParam String id) {
            BuyOrder buyOrder = buyOrderBaseService.getById(id);

        return Result.success(buyOrder).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "购买订单保存",
            notes = "购买订单保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:buyorder:save")
    public Map<String,Object> save(@RequestBody BuyOrderPutMapper buyOrderPutMapper){
        buyOrderBaseService.save(BuyOrderPutMapper.convertToEntity(buyOrderPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "购买订单修改",
            notes = "购买订单修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:buyorder:update")
    public Map<String,Object> update(@RequestBody BuyOrder buyOrder){
        buyOrderBaseService.updateById(buyOrder);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "购买订单批量删除",
            notes = "购买订单批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:buyorder:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        buyOrderBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
