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

import com.yibingo.race.dal.entity.LotteryPrize;
import com.yibingo.race.core.service.base.LotteryPrizeBaseService;
import com.yibingo.race.dal.filterMapper.LotteryPrizeFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryPrizePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 抽奖奖品;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "lotteryprize",
        tags = "抽奖奖品;"
)
@RestController
@RequestMapping("core/lotteryprize")
public class LotteryPrizeBaseController {
    @Autowired
    private LotteryPrizeBaseService lotteryPrizeBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "抽奖奖品;查询",
            notes = "抽奖奖品;查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryprize:info")
    public Map<String,Object> info(@RequestParam String id) {
            LotteryPrize lotteryPrize = lotteryPrizeBaseService.getById(id);

        return Result.success(lotteryPrize).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "抽奖奖品;保存",
            notes = "抽奖奖品;保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryprize:save")
    public Map<String,Object> save(@RequestBody LotteryPrizePutMapper lotteryPrizePutMapper){
        lotteryPrizeBaseService.save(LotteryPrizePutMapper.convertToEntity(lotteryPrizePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "抽奖奖品;修改",
            notes = "抽奖奖品;修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryprize:update")
    public Map<String,Object> update(@RequestBody LotteryPrize lotteryPrize){
        lotteryPrizeBaseService.updateById(lotteryPrize);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "抽奖奖品;批量删除",
            notes = "抽奖奖品;批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryprize:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        lotteryPrizeBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
