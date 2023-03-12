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

import com.yibingo.race.dal.entity.RedeemCode;
import com.yibingo.race.core.service.base.RedeemCodeBaseService;
import com.yibingo.race.dal.filterMapper.RedeemCodeFilterMapper;
import com.yibingo.race.dal.putMapper.RedeemCodePutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 兑换码;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "redeemcode",
        tags = "兑换码;"
)
@RestController
@RequestMapping("core/redeemcode")
public class RedeemCodeBaseController {
    @Autowired
    private RedeemCodeBaseService redeemCodeBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "兑换码;查询",
            notes = "兑换码;查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:redeemcode:info")
    public Map<String,Object> info(@RequestParam String id) {
            RedeemCode redeemCode = redeemCodeBaseService.getById(id);

        return Result.success(redeemCode).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "兑换码;保存",
            notes = "兑换码;保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:redeemcode:save")
    public Map<String,Object> save(@RequestBody RedeemCodePutMapper redeemCodePutMapper){
        redeemCodeBaseService.save(RedeemCodePutMapper.convertToEntity(redeemCodePutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "兑换码;修改",
            notes = "兑换码;修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:redeemcode:update")
    public Map<String,Object> update(@RequestBody RedeemCode redeemCode){
        redeemCodeBaseService.updateById(redeemCode);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "兑换码;批量删除",
            notes = "兑换码;批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:redeemcode:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        redeemCodeBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
