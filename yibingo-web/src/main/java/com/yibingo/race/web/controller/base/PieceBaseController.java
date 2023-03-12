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

import com.yibingo.race.dal.entity.Piece;
import com.yibingo.race.core.service.base.PieceBaseService;
import com.yibingo.race.dal.filterMapper.PieceFilterMapper;
import com.yibingo.race.dal.putMapper.PiecePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 碎片
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "piece",
        tags = "碎片"
)
@RestController
@RequestMapping("core/piece")
public class PieceBaseController {
    @Autowired
    private PieceBaseService pieceBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "碎片查询",
            notes = "碎片查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piece:info")
    public Map<String,Object> info(@RequestParam String id) {
            Piece piece = pieceBaseService.getById(id);

        return Result.success(piece).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "碎片保存",
            notes = "碎片保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piece:save")
    public Map<String,Object> save(@RequestBody PiecePutMapper piecePutMapper){
        pieceBaseService.save(PiecePutMapper.convertToEntity(piecePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "碎片修改",
            notes = "碎片修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piece:update")
    public Map<String,Object> update(@RequestBody Piece piece){
        pieceBaseService.updateById(piece);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "碎片批量删除",
            notes = "碎片批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piece:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        pieceBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
