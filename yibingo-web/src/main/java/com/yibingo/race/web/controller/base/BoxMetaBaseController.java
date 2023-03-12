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

import com.yibingo.race.dal.entity.BoxMeta;
import com.yibingo.race.core.service.base.BoxMetaBaseService;
import com.yibingo.race.dal.filterMapper.BoxMetaFilterMapper;
import com.yibingo.race.dal.putMapper.BoxMetaPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 盲盒藏品绑定关系
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "boxmeta",
        tags = "盲盒藏品绑定关系"
)
@RestController
@RequestMapping("core/boxmeta")
public class BoxMetaBaseController {
    @Autowired
    private BoxMetaBaseService boxMetaBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "盲盒藏品绑定关系查询",
            notes = "盲盒藏品绑定关系查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxmeta:info")
    public Map<String,Object> info(@RequestParam String id) {
            BoxMeta boxMeta = boxMetaBaseService.getById(id);

        return Result.success(boxMeta).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "盲盒藏品绑定关系保存",
            notes = "盲盒藏品绑定关系保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxmeta:save")
    public Map<String,Object> save(@RequestBody BoxMetaPutMapper boxMetaPutMapper){
        boxMetaBaseService.save(BoxMetaPutMapper.convertToEntity(boxMetaPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "盲盒藏品绑定关系修改",
            notes = "盲盒藏品绑定关系修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxmeta:update")
    public Map<String,Object> update(@RequestBody BoxMeta boxMeta){
        boxMetaBaseService.updateById(boxMeta);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "盲盒藏品绑定关系批量删除",
            notes = "盲盒藏品绑定关系批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:boxmeta:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        boxMetaBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
