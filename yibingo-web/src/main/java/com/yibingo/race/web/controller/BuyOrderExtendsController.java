package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.BaseInfoService;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.detailMapper.BuyOrderDetailMapper;
import com.yibingo.race.dal.entity.BuyOrder;
import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.filterMapper.BuyOrderFilterMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/8/29 17:49
 */
@Api(
        value = "buyorder_extends" ,
        tags = "订单扩展管理"
)
@RestController
@RequestMapping("core/buyorder/extends" )
public class BuyOrderExtendsController {

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    @Autowired
    private MarketBaseService marketBaseService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @ApiOperation(
            value = "搜索条件查询" ,
            notes = "搜索条件查询"
    )
    @RequestMapping(
            value = "/search" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getListByFilter(@RequestParam(value = "updateTimeFrom" , required = false) Long updateTimeFrom,
                                               @RequestParam(value = "updateTimeTo" , required = false) Long updateTimeTo,
                                               @RequestParam(value = "createTimeFrom" , required = false) Long createTimeFrom,
                                               @RequestParam(value = "createTimeTo" , required = false) Long createTimeTo,
                                               @RequestParam(value = "page" , required = false, defaultValue = "1" ) Long page,
                                               @RequestParam(value = "row" , required = false, defaultValue = "10" ) Long row,
                                               @RequestParam(value = "orderBy" , required = false) List<String> orderBy,
                                               @RequestParam(value = "status" , required = false) Integer status

    ) {
        String userId = StpUtil.getLoginIdAsString();
        List<Map<String, Object>> dataList = new ArrayList<>();
        BuyOrderFilterMapper mapper = new BuyOrderFilterMapper();
        mapper.updateTimeFrom = updateTimeFrom;
        mapper.updateTimeTo = updateTimeTo;
        mapper.createTimeFrom = createTimeFrom;
        mapper.createTimeTo = createTimeTo;
        mapper.page = page;
        mapper.row = row;
        mapper.orderBy = orderBy;
        mapper.status = status;
        mapper.userId = userId;
        List<BuyOrder> buyOrderList = BuyOrderFilterService.getListByFilter(buyOrderBaseService, mapper);
        for (BuyOrder buyOrder : buyOrderList) {
            Map<String, Object> data = BuyOrderDetailMapper.buildMap(buyOrder);
            Market market = marketBaseService.getById(buyOrder.getMarketId());
            data.put("marketId" , market.getId());
            data.put("type" , market.getNftType());

            String relationId = market.getRelationId();
            Map<String, Object> baseNftMap = new HashMap<>();
            if (market.getNftType().equals(NftTypeEnum.BlindBox.getKey())) {
                baseNftMap = baseInfoService.blindBoxInfoById(relationId);

            } else if (market.getNftType().equals(NftTypeEnum.BADGE.getKey())) {
                baseNftMap = baseInfoService.badgeInfoById(relationId);
            } else if (!market.getNftType().equals(NftTypeEnum.NONE.getKey())) {
                baseNftMap = baseInfoService.nftBaseInfoById(relationId);
            }
            data.put("baseNft",baseNftMap);
            dataList.add(data);
        }

        return Result.success(dataList).map();
    }

}
