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

import com.yibingo.race.dal.entity.PieceComRecords;
import com.yibingo.race.core.service.base.PieceComRecordsBaseService;
import com.yibingo.race.dal.filterMapper.PieceComRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.PieceComRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 合成记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "piececomrecords",
        tags = "合成记录"
)
@RestController
@RequestMapping("core/piececomrecords")
public class PieceComRecordsBaseController {
    @Autowired
    private PieceComRecordsBaseService pieceComRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "合成记录查询",
            notes = "合成记录查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            PieceComRecords pieceComRecords = pieceComRecordsBaseService.getById(id);

        return Result.success(pieceComRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "合成记录保存",
            notes = "合成记录保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrecords:save")
    public Map<String,Object> save(@RequestBody PieceComRecordsPutMapper pieceComRecordsPutMapper){
        pieceComRecordsBaseService.save(PieceComRecordsPutMapper.convertToEntity(pieceComRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "合成记录修改",
            notes = "合成记录修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrecords:update")
    public Map<String,Object> update(@RequestBody PieceComRecords pieceComRecords){
        pieceComRecordsBaseService.updateById(pieceComRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "合成记录批量删除",
            notes = "合成记录批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        pieceComRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
