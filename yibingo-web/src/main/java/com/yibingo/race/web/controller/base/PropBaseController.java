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

import com.yibingo.race.dal.entity.Prop;
import com.yibingo.race.core.service.base.PropBaseService;
import com.yibingo.race.dal.filterMapper.PropFilterMapper;
import com.yibingo.race.dal.putMapper.PropPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 道具
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "prop",
        tags = "道具"
)
@RestController
@RequestMapping("core/prop")
public class PropBaseController {
    @Autowired
    private PropBaseService propBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "道具查询",
            notes = "道具查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prop:info")
    public Map<String,Object> info(@RequestParam String id) {
            Prop prop = propBaseService.getById(id);

        return Result.success(prop).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "道具保存",
            notes = "道具保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prop:save")
    public Map<String,Object> save(@RequestBody PropPutMapper propPutMapper){
        propBaseService.save(PropPutMapper.convertToEntity(propPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "道具修改",
            notes = "道具修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prop:update")
    public Map<String,Object> update(@RequestBody Prop prop){
        propBaseService.updateById(prop);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "道具批量删除",
            notes = "道具批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:prop:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        propBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
