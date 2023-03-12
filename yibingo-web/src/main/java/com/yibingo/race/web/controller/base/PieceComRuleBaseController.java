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

import com.yibingo.race.dal.entity.PieceComRule;
import com.yibingo.race.core.service.base.PieceComRuleBaseService;
import com.yibingo.race.dal.filterMapper.PieceComRuleFilterMapper;
import com.yibingo.race.dal.putMapper.PieceComRulePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 碎片合成规则
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "piececomrule",
        tags = "碎片合成规则"
)
@RestController
@RequestMapping("core/piececomrule")
public class PieceComRuleBaseController {
    @Autowired
    private PieceComRuleBaseService pieceComRuleBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "碎片合成规则查询",
            notes = "碎片合成规则查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrule:info")
    public Map<String,Object> info(@RequestParam String id) {
            PieceComRule pieceComRule = pieceComRuleBaseService.getById(id);

        return Result.success(pieceComRule).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "碎片合成规则保存",
            notes = "碎片合成规则保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrule:save")
    public Map<String,Object> save(@RequestBody PieceComRulePutMapper pieceComRulePutMapper){
        pieceComRuleBaseService.save(PieceComRulePutMapper.convertToEntity(pieceComRulePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "碎片合成规则修改",
            notes = "碎片合成规则修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrule:update")
    public Map<String,Object> update(@RequestBody PieceComRule pieceComRule){
        pieceComRuleBaseService.updateById(pieceComRule);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "碎片合成规则批量删除",
            notes = "碎片合成规则批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:piececomrule:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        pieceComRuleBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
