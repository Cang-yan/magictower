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

import com.yibingo.race.dal.entity.BoxUncoverRecords;
import com.yibingo.race.core.service.base.BoxUncoverRecordsBaseService;
import com.yibingo.race.dal.filterMapper.BoxUncoverRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.BoxUncoverRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 盲盒抽取记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "boxuncoverrecords",
        tags = "盲盒抽取记录"
)
@RestController
@RequestMapping("core/boxuncoverrecords")
public class BoxUncoverRecordsBaseController {
    @Autowired
    private BoxUncoverRecordsBaseService boxUncoverRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "盲盒抽取记录查询",
            notes = "盲盒抽取记录查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxuncoverrecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            BoxUncoverRecords boxUncoverRecords = boxUncoverRecordsBaseService.getById(id);

        return Result.success(boxUncoverRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "盲盒抽取记录保存",
            notes = "盲盒抽取记录保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxuncoverrecords:save")
    public Map<String,Object> save(@RequestBody BoxUncoverRecordsPutMapper boxUncoverRecordsPutMapper){
        boxUncoverRecordsBaseService.save(BoxUncoverRecordsPutMapper.convertToEntity(boxUncoverRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "盲盒抽取记录修改",
            notes = "盲盒抽取记录修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxuncoverrecords:update")
    public Map<String,Object> update(@RequestBody BoxUncoverRecords boxUncoverRecords){
        boxUncoverRecordsBaseService.updateById(boxUncoverRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "盲盒抽取记录批量删除",
            notes = "盲盒抽取记录批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxuncoverrecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        boxUncoverRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
