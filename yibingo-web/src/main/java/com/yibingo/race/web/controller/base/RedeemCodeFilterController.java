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
import com.yibingo.race.core.service.base.RedeemCodeFilterService;
import com.yibingo.race.dal.filterMapper.RedeemCodeFilterMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 兑换码;扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Api(
        value = "redeemcode_filter",
        tags = "兑换码;条件查询"
)
@RestController
@RequestMapping("core/redeemcode/filter")
public class RedeemCodeFilterController {

    @Autowired
    private RedeemCodeBaseService redeemCodeBaseService;

    //需要的话再打开即可
    /* @ApiOperation(
            value = "搜索条件查询",
            notes = "搜索条件查询"
    )*/
    @RequestMapping(
            value = "/search",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String,Object> getListByFilter(@RequestParam(value = "updateTimeFrom", required = false)Long updateTimeFrom,
                             @RequestParam(value = "updateTimeTo", required = false) Long updateTimeTo,
                             @RequestParam(value = "createTimeFrom", required = false) Long createTimeFrom,
                             @RequestParam(value = "createTimeTo", required = false) Long createTimeTo,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "row", required = false) Long row,
                             @RequestParam(value = "orderBy", required = false) List<String> orderBy
    ) {
            RedeemCodeFilterMapper mapper = new RedeemCodeFilterMapper();
        mapper.updateTimeFrom = updateTimeFrom;
        mapper.updateTimeTo = updateTimeTo;
        mapper.createTimeFrom=createTimeFrom;
        mapper.createTimeTo=createTimeTo;
        mapper.page = page;
        mapper.row = row;
        mapper.orderBy = orderBy;
        return Result.success(RedeemCodeFilterService.getListByFilter(redeemCodeBaseService,mapper)).map();
    }






}