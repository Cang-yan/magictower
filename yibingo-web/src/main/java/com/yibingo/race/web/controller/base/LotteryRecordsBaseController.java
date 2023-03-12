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

import com.yibingo.race.dal.entity.LotteryRecords;
import com.yibingo.race.core.service.base.LotteryRecordsBaseService;
import com.yibingo.race.dal.filterMapper.LotteryRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 抽奖记录;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "lotteryrecords",
        tags = "抽奖记录;"
)
@RestController
@RequestMapping("core/lotteryrecords")
public class LotteryRecordsBaseController {
    @Autowired
    private LotteryRecordsBaseService lotteryRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "抽奖记录;查询",
            notes = "抽奖记录;查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryrecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            LotteryRecords lotteryRecords = lotteryRecordsBaseService.getById(id);

        return Result.success(lotteryRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "抽奖记录;保存",
            notes = "抽奖记录;保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryrecords:save")
    public Map<String,Object> save(@RequestBody LotteryRecordsPutMapper lotteryRecordsPutMapper){
        lotteryRecordsBaseService.save(LotteryRecordsPutMapper.convertToEntity(lotteryRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "抽奖记录;修改",
            notes = "抽奖记录;修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryrecords:update")
    public Map<String,Object> update(@RequestBody LotteryRecords lotteryRecords){
        lotteryRecordsBaseService.updateById(lotteryRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "抽奖记录;批量删除",
            notes = "抽奖记录;批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryrecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        lotteryRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
