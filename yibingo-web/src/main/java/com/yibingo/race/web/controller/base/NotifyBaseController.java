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

import com.yibingo.race.dal.entity.Notify;
import com.yibingo.race.core.service.base.NotifyBaseService;
import com.yibingo.race.dal.filterMapper.NotifyFilterMapper;
import com.yibingo.race.dal.putMapper.NotifyPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 公告通知
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "notify",
        tags = "公告通知"
)
@RestController
@RequestMapping("core/notify")
public class NotifyBaseController {
    @Autowired
    private NotifyBaseService notifyBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "公告通知查询",
            notes = "公告通知查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:notify:info")
    public Map<String,Object> info(@RequestParam String id) {
            Notify notify = notifyBaseService.getById(id);

        return Result.success(notify).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "公告通知保存",
            notes = "公告通知保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:notify:save")
    public Map<String,Object> save(@RequestBody NotifyPutMapper notifyPutMapper){
        notifyBaseService.save(NotifyPutMapper.convertToEntity(notifyPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "公告通知修改",
            notes = "公告通知修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:notify:update")
    public Map<String,Object> update(@RequestBody Notify notify){
        notifyBaseService.updateById(notify);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "公告通知批量删除",
            notes = "公告通知批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:notify:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        notifyBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
