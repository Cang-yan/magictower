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

import com.yibingo.race.dal.entity.InvatitionRank;
import com.yibingo.race.core.service.base.InvatitionRankBaseService;
import com.yibingo.race.dal.filterMapper.InvatitionRankFilterMapper;
import com.yibingo.race.dal.putMapper.InvatitionRankPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 邀新排行榜
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "invatitionrank",
        tags = "邀新排行榜"
)
@RestController
@RequestMapping("core/invatitionrank")
public class InvatitionRankBaseController {
    @Autowired
    private InvatitionRankBaseService invatitionRankBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "邀新排行榜查询",
            notes = "邀新排行榜查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invatitionrank:info")
    public Map<String,Object> info(@RequestParam String id) {
            InvatitionRank invatitionRank = invatitionRankBaseService.getById(id);

        return Result.success(invatitionRank).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "邀新排行榜保存",
            notes = "邀新排行榜保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invatitionrank:save")
    public Map<String,Object> save(@RequestBody InvatitionRankPutMapper invatitionRankPutMapper){
        invatitionRankBaseService.save(InvatitionRankPutMapper.convertToEntity(invatitionRankPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "邀新排行榜修改",
            notes = "邀新排行榜修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invatitionrank:update")
    public Map<String,Object> update(@RequestBody InvatitionRank invatitionRank){
        invatitionRankBaseService.updateById(invatitionRank);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "邀新排行榜批量删除",
            notes = "邀新排行榜批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invatitionrank:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        invatitionRankBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
