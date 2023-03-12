package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.LotteryExtendsService;
import com.yibingo.race.core.service.base.LotteryBaseService;
import com.yibingo.race.core.service.base.LotteryJoinBaseService;
import com.yibingo.race.core.service.base.LotteryJoinFilterService;
import com.yibingo.race.dal.detailMapper.LotteryDetailMapper;
import com.yibingo.race.dal.entity.LotteryJoin;
import com.yibingo.race.dal.entity.LotteryPrize;
import com.yibingo.race.dal.filterMapper.LotteryJoinFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/9/6 14:06
 */
@Api(
        value = "抽奖",
        tags = "抽奖"
)
@RestController
@RequestMapping("core/lottery/extends")
public class LotteryExtendsController {

    @Autowired
    private LotteryExtendsService lotteryExtendsService;

    @Autowired
    private LotteryBaseService lotteryBaseService;

    @Autowired
    private LotteryJoinBaseService lotteryJoinBaseService;

    @ApiOperation(
            value = "设定抽奖",
            notes = "设定抽奖"
    )
    @RequestMapping(
            value = "/make",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> makeLottery(@RequestBody LotteryPutMapper lotteryPutMapper) {
        lotteryExtendsService.makeLottery(lotteryPutMapper);
        return Result.success().map();
    }

    @ApiOperation(
            value = "设定奖品",
            notes = "设定奖品"
    )
    @RequestMapping(
            value = "/make/prize",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String,Object> makeLotteryPrize(@RequestBody JSONArray jsonArray)
    {
//        @RequestParam String lotteryId,
//        @RequestParam String prizeGrade,//一等奖二等奖
//        @RequestParam String prizeName,//奖项名字
//        @RequestParam(required = false) String nftId,//徽章id 藏品id 盲盒id
//        @RequestParam Integer type)//标志 奖品 是虚拟的还是实体的

        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String lotteryId = jsonObject.getStr("lotteryId");
            Integer prizeGrade = Integer.parseInt(jsonObject.getStr("prizeGrade"));
            String prizeName = jsonObject.getStr("prizeName");
            String nftId = jsonObject.getStr("nftId");
            Integer type = Integer.parseInt(jsonObject.getStr("type"));
            Integer count = Integer.parseInt(jsonObject.getStr("count"));
            Double possibility = Double.parseDouble(jsonObject.getStr("possibility"));
            lotteryExtendsService.makeLotteryPrize(lotteryId,prizeGrade,prizeName,nftId,type,count,possibility);

        }
        return Result.success().map();
    }



    @ApiOperation(
            value = "参与抽奖",
            notes = "参与抽奖"
    )
    @RequestMapping(
            value = "/join",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> joinLottery(String lotteryId) {
        String userId = StpUtil.getLoginIdAsString();
        lotteryExtendsService.joinLottery(lotteryId, userId);
        return Result.success().map();
    }

    @ApiOperation(
            value = "查询所有的抽奖",
            notes = "查询所有的抽奖"
    )
    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> listAll() {


        return Result.success(
                LotteryDetailMapper.buildMapList(lotteryBaseService.list())
        ).map();
    }

    @ApiOperation(
            value = "查询用户参与的抽奖",
            notes = "查询用户参与的抽奖"
    )
    @RequestMapping(
            value = "/joined",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> listJoined() {
        String userId = StpUtil.getLoginIdAsString();
        LotteryJoinFilterMapper lotteryJoinFilterMapper = new LotteryJoinFilterMapper();
        lotteryJoinFilterMapper.userId = userId;
        List<LotteryJoin> lotteryJoinList = LotteryJoinFilterService.getListByFilter(lotteryJoinBaseService, lotteryJoinFilterMapper);
        List<String> lotteryIdList = lotteryJoinList.stream().map(LotteryJoin::getLotteryId).collect(Collectors.toList());
        return Result.success(
                LotteryDetailMapper.buildMapList(lotteryBaseService.listByIds(lotteryIdList))
        ).map();

    }

    @ApiOperation(
            value = "查询用户抽奖记录",
            notes = "查询用户抽奖记录"
    )
    @RequestMapping(
            value = "/joined/result",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getResult(@RequestParam String lotteryId) {
        String userId = StpUtil.getLoginIdAsString();

        return Result.success(
                lotteryExtendsService.selectLotteryResult(lotteryId, userId)
        ).map();
    }


}
