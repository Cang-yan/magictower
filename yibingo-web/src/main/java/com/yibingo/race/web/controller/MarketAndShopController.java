package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.base.MarketBaseService;
import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.dal.enums.NftTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/18 16:35
 */
@Api(
        value = "商城",
        tags = "商城"
)
@RestController
@RequestMapping("core/market/shop")
public class MarketAndShopController {
    @Autowired
    private MarketBaseService marketBaseService;


    @ApiOperation(
            value = "上架藏品",
            notes = "上架藏品"
    )
    @RequestMapping(
            value = "/on/nft",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> onNftShop(@RequestParam String metaNftId,
                                         @RequestParam Double price,
                                         @RequestParam Integer isForSell,
                                         @RequestParam String sellTime,
                                         @RequestParam String familyId,
                                         @RequestParam Integer nowCount,
                                         @RequestParam Integer limitPurchase,
                                         @RequestParam Integer nftType
    ) {
        Long sellTimeMills = Long.parseLong(sellTime);

        Market market = new Market();
        market.setRelationId(metaNftId);
        market.setPrice(price);
        market.setIsForSell(isForSell);
        market.setSellTime(new Date(sellTimeMills));
        market.setFamilyId(familyId);
        market.setNowCount(nowCount);
        market.setLimitPurchase(limitPurchase);
        market.setNftType(nftType);
        marketBaseService.save(market);
        return Result.success().map();

    }

    @ApiOperation(
            value = "上架徽章",
            notes = "上架徽章"
    )
    @RequestMapping(
            value = "/on/badge",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> onBadge(@RequestParam String metaNftId,
                                       @RequestParam Double price,
                                       @RequestParam Integer isForSell,
                                       @RequestParam String sellTime,
                                       @RequestParam String familyId,
                                       @RequestParam Integer nowCount,
                                       @RequestParam Integer limitPurchase) {
        Long sellTimeMills = Long.parseLong(sellTime);

        Market market = new Market();
        market.setRelationId(metaNftId);
        market.setPrice(price);
        market.setIsForSell(isForSell);
        market.setSellTime(new Date(sellTimeMills));
        market.setFamilyId(familyId);
        market.setNowCount(nowCount);
        market.setLimitPurchase(limitPurchase);
        market.setNftType(NftTypeEnum.BADGE.getKey());
        marketBaseService.save(market);
        return Result.success().map();


    }

    @ApiOperation(
            value = "上架盲盒",
            notes = "上架盲盒"
    )
    @RequestMapping(
            value = "/on/box",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> onBox(@RequestParam String metaNftId,
                                     @RequestParam Double price,
                                     @RequestParam Integer isForSell,
                                     @RequestParam String sellTime,
                                     @RequestParam String familyId,
                                     @RequestParam Integer nowCount,
                                     @RequestParam Integer limitPurchase) {
        Long sellTimeMills = Long.parseLong(sellTime);
        Market market = new Market();
        market.setRelationId(metaNftId);
        market.setPrice(price);
        market.setIsForSell(isForSell);
        market.setSellTime(new Date(sellTimeMills));
        market.setFamilyId(familyId);
        market.setNowCount(nowCount);
        market.setLimitPurchase(limitPurchase);
        market.setNftType(NftTypeEnum.BlindBox.getKey());
        marketBaseService.save(market);
        return Result.success().map();


    }

}
