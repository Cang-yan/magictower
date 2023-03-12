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

import com.yibingo.race.dal.entity.ExchangeRecords;
import com.yibingo.race.core.service.base.ExchangeRecordsBaseService;
import com.yibingo.race.dal.filterMapper.ExchangeRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.ExchangeRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 藏品交易记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "exchangerecords",
        tags = "藏品交易记录"
)
@RestController
@RequestMapping("core/exchangerecords")
public class ExchangeRecordsBaseController {
    @Autowired
    private ExchangeRecordsBaseService exchangeRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "藏品交易记录查询",
            notes = "藏品交易记录查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            ExchangeRecords exchangeRecords = exchangeRecordsBaseService.getById(id);

        return Result.success(exchangeRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "藏品交易记录保存",
            notes = "藏品交易记录保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerecords:save")
    public Map<String,Object> save(@RequestBody ExchangeRecordsPutMapper exchangeRecordsPutMapper){
        exchangeRecordsBaseService.save(ExchangeRecordsPutMapper.convertToEntity(exchangeRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "藏品交易记录修改",
            notes = "藏品交易记录修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerecords:update")
    public Map<String,Object> update(@RequestBody ExchangeRecords exchangeRecords){
        exchangeRecordsBaseService.updateById(exchangeRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "藏品交易记录批量删除",
            notes = "藏品交易记录批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:exchangerecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        exchangeRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
