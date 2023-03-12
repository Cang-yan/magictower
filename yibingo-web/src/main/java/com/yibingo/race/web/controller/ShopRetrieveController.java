package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.ShopRetrieveService;
import com.yibingo.race.dal.enums.NftTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * @author Li XingHan
 * @description 商城购物查询藏品
 * @date 2022-06-25 15:04
 */
@Api(
        value = "商城购物查询藏品",
        tags = "商城购物查询藏品"
)
@RestController
@RequestMapping("core/shop/retrieve")
public class ShopRetrieveController {


    @Autowired
    private ShopRetrieveService shopRetrieveService;

    /**
     * 查询所有在库藏品，类型为英雄
     */
    @ApiOperation(
            value = "查询类型为英雄的藏品",
            notes = "查询类型为英雄的藏品"
    )
    @RequestMapping(
            value = "/hero",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> hero() {
        return Result.success(
                shopRetrieveService.getMetaNftInShopByType(NftTypeEnum.HERO)
        ).map();
    }


    @ApiOperation(
            value = "查询类型为装备的藏品",
            notes = "查询类型为装备的藏品"
    )
    @RequestMapping(
            value = "/equip",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> equip() {
        return Result.success(
                shopRetrieveService.getMetaNftInShopByType(NftTypeEnum.EQUIP)
        ).map();
    }

    @ApiOperation(
            value = "查询徽章",
            notes = "查询徽章"
    )
    @RequestMapping(
            value = "/badge",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> badge(){
        return Result.success(
                shopRetrieveService.getBadgeInShop()
        ).map();
    }

    @ApiOperation(
            value = "查询盲盒",
            notes = "查询盲盒"
    )
    @RequestMapping(
            value = "/box",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> box(){
        return Result.success(
                shopRetrieveService.getBlindBoxInShop()
        ).map();
    }

    @ApiOperation(
            value = "详情页",
            notes = "详情页"
    )
    @RequestMapping(
            value = "/detail",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> detail(@RequestParam String marketId,@RequestParam Integer type){
        return Result.success(
                shopRetrieveService.getMetaNftInShopDetail(marketId,NftTypeEnum.getEnumByKey(type))
        ).map();
    }

}
