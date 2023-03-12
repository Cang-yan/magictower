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

import com.yibingo.race.dal.entity.Lottery;
import com.yibingo.race.core.service.base.LotteryBaseService;
import com.yibingo.race.dal.filterMapper.LotteryFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 抽奖;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "lottery",
        tags = "抽奖;"
)
@RestController
@RequestMapping("core/lottery")
public class LotteryBaseController {
    @Autowired
    private LotteryBaseService lotteryBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "抽奖;查询",
            notes = "抽奖;查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lottery:info")
    public Map<String,Object> info(@RequestParam String id) {
            Lottery lottery = lotteryBaseService.getById(id);

        return Result.success(lottery).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "抽奖;保存",
            notes = "抽奖;保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lottery:save")
    public Map<String,Object> save(@RequestBody LotteryPutMapper lotteryPutMapper){
        lotteryBaseService.save(LotteryPutMapper.convertToEntity(lotteryPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "抽奖;修改",
            notes = "抽奖;修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lottery:update")
    public Map<String,Object> update(@RequestBody Lottery lottery){
        lotteryBaseService.updateById(lottery);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "抽奖;批量删除",
            notes = "抽奖;批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lottery:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        lotteryBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
