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

import com.yibingo.race.dal.entity.InvitationRecords;
import com.yibingo.race.core.service.base.InvitationRecordsBaseService;
import com.yibingo.race.dal.filterMapper.InvitationRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.InvitationRecordsPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 邀请记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-28 09:38:46
 */
@Api(
        value = "invitationrecords",
        tags = "邀请记录"
)
@RestController
@RequestMapping("core/invitationrecords")
public class InvitationRecordsBaseController {
    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "邀请记录查询",
            notes = "邀请记录查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invitationrecords:info")
    public Map<String,Object> info(@RequestParam String id) {
            InvitationRecords invitationRecords = invitationRecordsBaseService.getById(id);

        return Result.success(invitationRecords).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "邀请记录保存",
            notes = "邀请记录保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invitationrecords:save")
    public Map<String,Object> save(@RequestBody InvitationRecordsPutMapper invitationRecordsPutMapper){
        invitationRecordsBaseService.save(InvitationRecordsPutMapper.convertToEntity(invitationRecordsPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "邀请记录修改",
            notes = "邀请记录修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invitationrecords:update")
    public Map<String,Object> update(@RequestBody InvitationRecords invitationRecords){
        invitationRecordsBaseService.updateById(invitationRecords);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "邀请记录批量删除",
            notes = "邀请记录批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:invitationrecords:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        invitationRecordsBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
