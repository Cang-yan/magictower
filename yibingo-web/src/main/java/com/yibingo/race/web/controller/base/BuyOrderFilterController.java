package com.yibingo.race.web.controller.base;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.core.service.base.BuyOrderFilterService;
import com.yibingo.race.dal.filterMapper.BuyOrderFilterMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 购买订单扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-07 15:56:08
 */
@Api(
        value = "buyorder_extends" ,
        tags = "购买订单扩展管理"
)
@RestController
@RequestMapping("core/buyorder/filter" )
public class BuyOrderFilterController {

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    //需要的话再打开即可
    /*@ApiOperation(
            value = "搜索条件查询" ,
            notes = "搜索条件查询"
    )*/
    @RequestMapping(
            value = "/search" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> getListByFilter(@RequestParam(value = "updateTimeFrom" , required = false) Long updateTimeFrom,
                                               @RequestParam(value = "updateTimeTo" , required = false) Long updateTimeTo,
                                               @RequestParam(value = "createTimeFrom" , required = false) Long createTimeFrom,
                                               @RequestParam(value = "createTimeTo" , required = false) Long createTimeTo,
                                               @RequestParam(value = "page" , required = false, defaultValue = "1" ) Long page,
                                               @RequestParam(value = "row" , required = false, defaultValue = "10" ) Long row,
                                               @RequestParam(value = "orderBy" , required = false) List<String> orderBy,
                                               @RequestParam(value = "status" , required = false) Integer status

    ) {
        BuyOrderFilterMapper mapper = new BuyOrderFilterMapper();
        mapper.updateTimeFrom = updateTimeFrom;
        mapper.updateTimeTo = updateTimeTo;
        mapper.createTimeFrom = createTimeFrom;
        mapper.createTimeTo = createTimeTo;
        mapper.page = page;
        mapper.row = row;
        mapper.orderBy = orderBy;
        mapper.status = status;
        return Result.success(BuyOrderFilterService.getListByFilter(buyOrderBaseService, mapper)).map();
    }


}