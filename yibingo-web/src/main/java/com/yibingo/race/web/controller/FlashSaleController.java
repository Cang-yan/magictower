package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.FlashSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/7 20:34
 */
@Api(
        value = "秒杀相关的接口",
        tags = "秒杀相关的接口"
)
@RestController
@RequestMapping("core/flashsale")
public class FlashSaleController {
    @Autowired
    private FlashSaleService flashSaleService;


    @ApiOperation(
            value = "查询秒杀优先购数量 以及当前库存",
            notes = "查询秒杀优先购数量 以及当前库存"
    )
    @RequestMapping(
            value = "/get/limit",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getLimitNum(@RequestParam String marketId) {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                flashSaleService.getLimitPurchaseNum(userId, marketId)
        ).map();
    }


    @ApiOperation(
            value = "查询秒杀结果",
            notes = "查询秒杀结果"
    )
    @RequestMapping(
            value = "/get",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getSeckillResult(@RequestParam String buyOrderId) {
        //轮询查询
        return flashSaleService.getSeckillResult(buyOrderId);
    }

    @ApiOperation(
            value = "秒杀——优先购买",
            notes = "秒杀——优先购买"
    )
    @RequestMapping(
            value = "/prior",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> priorSecKill(@RequestParam String marketId,
                                            @RequestParam Integer puchaseNum,
                                            @RequestParam Integer limitPurchase) {

        String userId = StpUtil.getLoginIdAsString();

        String buyOrderId = flashSaleService.secendPriorKill(userId, marketId, puchaseNum, limitPurchase);

        return flashSaleService.getSeckillResult(buyOrderId);

    }

    @ApiOperation(
            value = "秒杀——普通购买",
            notes = "秒杀——普通购买"
    )
    @RequestMapping(
            value = "/common",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> secKill(
            @RequestParam String marketId) {
        String userId = StpUtil.getLoginIdAsString();

        String buyOrderId = flashSaleService.secondKill(userId, marketId, 1);

        return flashSaleService.getSeckillResult(buyOrderId);


    }

    @ApiOperation(
            value = "手动增加数量",
            notes = "手动增加数量"
    )
    @RequestMapping(
            value = "/add/limit",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> addLimit(@RequestBody List<String> userIdList, @RequestParam Integer addNum) {

        flashSaleService.changeLimitPurchaseNum(userIdList, addNum);
        return Result.success().map();

    }

    @ApiOperation(
            value = "手动初始化",
            notes = "手动初始化"
    )
    @RequestMapping(
            value = "initialization",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> initialization() {
        flashSaleService.afterPropertiesSet();
        return Result.success().map();
    }

}
