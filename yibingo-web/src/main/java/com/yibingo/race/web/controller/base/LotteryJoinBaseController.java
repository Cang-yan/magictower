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

import com.yibingo.race.dal.entity.LotteryJoin;
import com.yibingo.race.core.service.base.LotteryJoinBaseService;
import com.yibingo.race.dal.filterMapper.LotteryJoinFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryJoinPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 抽奖参与人员;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "lotteryjoin",
        tags = "抽奖参与人员;"
)
@RestController
@RequestMapping("core/lotteryjoin")
public class LotteryJoinBaseController {
    @Autowired
    private LotteryJoinBaseService lotteryJoinBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "抽奖参与人员;查询",
            notes = "抽奖参与人员;查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryjoin:info")
    public Map<String,Object> info(@RequestParam String id) {
            LotteryJoin lotteryJoin = lotteryJoinBaseService.getById(id);

        return Result.success(lotteryJoin).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "抽奖参与人员;保存",
            notes = "抽奖参与人员;保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryjoin:save")
    public Map<String,Object> save(@RequestBody LotteryJoinPutMapper lotteryJoinPutMapper){
        lotteryJoinBaseService.save(LotteryJoinPutMapper.convertToEntity(lotteryJoinPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "抽奖参与人员;修改",
            notes = "抽奖参与人员;修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryjoin:update")
    public Map<String,Object> update(@RequestBody LotteryJoin lotteryJoin){
        lotteryJoinBaseService.updateById(lotteryJoin);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "抽奖参与人员;批量删除",
            notes = "抽奖参与人员;批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:lotteryjoin:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        lotteryJoinBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
