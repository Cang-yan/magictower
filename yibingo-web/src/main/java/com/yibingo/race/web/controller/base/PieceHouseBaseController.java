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

import com.yibingo.race.dal.entity.PieceHouse;
import com.yibingo.race.core.service.base.PieceHouseBaseService;
import com.yibingo.race.dal.filterMapper.PieceHouseFilterMapper;
import com.yibingo.race.dal.putMapper.PieceHousePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 碎片仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "piecehouse",
        tags = "碎片仓库"
)
@RestController
@RequestMapping("core/piecehouse")
public class PieceHouseBaseController {
    @Autowired
    private PieceHouseBaseService pieceHouseBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "碎片仓库查询",
            notes = "碎片仓库查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piecehouse:info")
    public Map<String,Object> info(@RequestParam String id) {
            PieceHouse pieceHouse = pieceHouseBaseService.getById(id);

        return Result.success(pieceHouse).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "碎片仓库保存",
            notes = "碎片仓库保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piecehouse:save")
    public Map<String,Object> save(@RequestBody PieceHousePutMapper pieceHousePutMapper){
        pieceHouseBaseService.save(PieceHousePutMapper.convertToEntity(pieceHousePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "碎片仓库修改",
            notes = "碎片仓库修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piecehouse:update")
    public Map<String,Object> update(@RequestBody PieceHouse pieceHouse){
        pieceHouseBaseService.updateById(pieceHouse);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "碎片仓库批量删除",
            notes = "碎片仓库批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piecehouse:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        pieceHouseBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
