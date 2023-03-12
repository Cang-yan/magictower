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

import com.yibingo.race.dal.entity.ExchangeBoxRecords;
import com.yibingo.race.core.service.base.ExchangeBoxRecordsBaseService;
import com.yibingo.race.dal.filterMapper.ExchangeBoxRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.ExchangeBoxRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 盲盒交易记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "exchangeboxrecords",
        tags = "盲盒交易记录"
)
@RestController
@RequestMapping("core/exchangeboxrecords")
public class ExchangeBoxRecordsBaseController {
    @Autowired
    private ExchangeBoxRecordsBaseService exchangeBoxRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "盲盒交易记录查询",
            notes = "盲盒交易记录查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangeboxrecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            ExchangeBoxRecords exchangeBoxRecords = exchangeBoxRecordsBaseService.getById(id);

        return Result.success(exchangeBoxRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "盲盒交易记录保存",
            notes = "盲盒交易记录保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangeboxrecords:save")
    public Map<String,Object> save(@RequestBody ExchangeBoxRecordsPutMapper exchangeBoxRecordsPutMapper){
        exchangeBoxRecordsBaseService.save(ExchangeBoxRecordsPutMapper.convertToEntity(exchangeBoxRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "盲盒交易记录修改",
            notes = "盲盒交易记录修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangeboxrecords:update")
    public Map<String,Object> update(@RequestBody ExchangeBoxRecords exchangeBoxRecords){
        exchangeBoxRecordsBaseService.updateById(exchangeBoxRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "盲盒交易记录批量删除",
            notes = "盲盒交易记录批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangeboxrecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        exchangeBoxRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
