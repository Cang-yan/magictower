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

import com.yibingo.race.dal.entity.BlindBoxPool;
import com.yibingo.race.core.service.base.BlindBoxPoolBaseService;
import com.yibingo.race.dal.filterMapper.BlindBoxPoolFilterMapper;
import com.yibingo.race.dal.putMapper.BlindBoxPoolPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 盲盒抽奖池子
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "blindboxpool",
        tags = "盲盒抽奖池子"
)
@RestController
@RequestMapping("core/blindboxpool")
public class BlindBoxPoolBaseController {
    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "盲盒抽奖池子查询",
            notes = "盲盒抽奖池子查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:blindboxpool:info")
    public Map<String,Object> info(@RequestParam String id) {
            BlindBoxPool blindBoxPool = blindBoxPoolBaseService.getById(id);

        return Result.success(blindBoxPool).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "盲盒抽奖池子保存",
            notes = "盲盒抽奖池子保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:blindboxpool:save")
    public Map<String,Object> save(@RequestBody BlindBoxPoolPutMapper blindBoxPoolPutMapper){
        blindBoxPoolBaseService.save(BlindBoxPoolPutMapper.convertToEntity(blindBoxPoolPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "盲盒抽奖池子修改",
            notes = "盲盒抽奖池子修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:blindboxpool:update")
    public Map<String,Object> update(@RequestBody BlindBoxPool blindBoxPool){
        blindBoxPoolBaseService.updateById(blindBoxPool);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "盲盒抽奖池子批量删除",
            notes = "盲盒抽奖池子批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:blindboxpool:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        blindBoxPoolBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
