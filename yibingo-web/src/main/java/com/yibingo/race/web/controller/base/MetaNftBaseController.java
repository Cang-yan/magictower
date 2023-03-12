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

import com.yibingo.race.dal.entity.MetaNft;
import com.yibingo.race.core.service.base.MetaNftBaseService;
import com.yibingo.race.dal.filterMapper.MetaNftFilterMapper;
import com.yibingo.race.dal.putMapper.MetaNftPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 藏品
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "metanft",
        tags = "藏品"
)
@RestController
@RequestMapping("core/metanft")
public class MetaNftBaseController {
    @Autowired
    private MetaNftBaseService metaNftBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "藏品查询",
            notes = "藏品查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:metanft:info")
    public Map<String,Object> info(@RequestParam String id) {
            MetaNft metaNft = metaNftBaseService.getById(id);

        return Result.success(metaNft).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "藏品保存",
            notes = "藏品保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:metanft:save")
    public Map<String,Object> save(@RequestBody MetaNftPutMapper metaNftPutMapper){
        metaNftBaseService.save(MetaNftPutMapper.convertToEntity(metaNftPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "藏品修改",
            notes = "藏品修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:metanft:update")
    public Map<String,Object> update(@RequestBody MetaNft metaNft){
        metaNftBaseService.updateById(metaNft);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "藏品批量删除",
            notes = "藏品批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:metanft:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        metaNftBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
