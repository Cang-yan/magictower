package com.yibingo.race.core.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.LotteryPrizeEnum;
import com.yibingo.race.dal.enums.LotteryRuleEnum;
import com.yibingo.race.dal.filterMapper.InvitationRecordsFilterMapper;
import com.yibingo.race.dal.filterMapper.LotteryJoinFilterMapper;
import com.yibingo.race.dal.filterMapper.LotteryRecordsFilterMapper;
import com.yibingo.race.dal.putMapper.LotteryPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/8/31 15:15
 */
@Service
public class LotteryExtendsService {

    @Autowired
    private LotteryBaseService lotteryBaseService;

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    @Autowired
    private LotteryJoinBaseService lotteryJoinBaseService;

    @Autowired
    private LotteryRecordsBaseService lotteryRecordsBaseService;

    @Autowired
    private LotteryPrizeBaseService lotteryPrizeBaseService;

    @Autowired
    private RedeemCodeBaseService redeemCodeBaseService;

    /**
     * 设定抽奖
     */
    public void makeLottery(LotteryPutMapper lotteryPutMapper) {
        lotteryBaseService.save(LotteryPutMapper.convertToEntity(lotteryPutMapper));
        //todo 设定只执行一次的定时任务
        
        
    }


    public void makeLotteryPrize(String lotteryId, Integer prizeGrade, String prizeName, String nftId, Integer type, Integer count, Double possibility) {
        LotteryPrize lotteryPrize = new LotteryPrize();
        lotteryPrize.setLotteryId(lotteryId);
        lotteryPrize.setText(prizeName);
        lotteryPrize.setGrade(prizeGrade);
        lotteryPrize.setPossibility(possibility);
        lotteryPrize.setStockCount(count);
        lotteryPrizeBaseService.save(lotteryPrize);
        List<RedeemCode> redeemCodeList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            RedeemCode redeemCode = new RedeemCode();
            if (type.equals(LotteryPrizeEnum.Real.getKey())) {
                redeemCode.setText(prizeName);
            } else {
                redeemCode.setText(IdUtil.randomUUID().substring(0, 6).toUpperCase());
                redeemCode.setGoodsId(nftId);
            }
            redeemCode.setLotteryPrizeId(lotteryPrize.getId());
            redeemCode.setType(type);
            redeemCodeList.add(redeemCode);
        }
        redeemCodeBaseService.saveBatch(redeemCodeList);

    }

    /**
     * 参与抽奖
     * 有规则判定
     */
    public void joinLottery(String lotteryId, String userId) {
        Lottery lottery = lotteryBaseService.getById(lotteryId);
        //User user = userBaseService.getById(userId);
        judge(lottery, userId);
        LotteryJoin lotteryJoin = new LotteryJoin();
        lotteryJoin.setLotteryId(lotteryId);
        lotteryJoin.setUserId(userId);
        lotteryJoinBaseService.save(lotteryJoin);
    }

    //条件判定
    private void judge(Lottery lottery, String userId) {
        LotteryJoinFilterMapper lotteryJoinFilterMapper = new LotteryJoinFilterMapper();
        lotteryJoinFilterMapper.userId = userId;
        if (!CollectionUtil.isEmpty(LotteryJoinFilterService.getListByFilter(lotteryJoinBaseService, lotteryJoinFilterMapper))) {
            throw new BaseException("不要重复参加");
        }

        if (lottery.getLimitRule().equals(LotteryRuleEnum.Invitation.getKey())) {
            InvitationRecordsFilterMapper invitationRecordsFilterMapper = new InvitationRecordsFilterMapper();
            invitationRecordsFilterMapper.hostId = userId;
            List<InvitationRecords> invitationRecordsList = InvitationRecordsFilterService.getListByFilter(invitationRecordsBaseService, invitationRecordsFilterMapper);

            if (invitationRecordsList.size() < lottery.getCoreNum()) throw new BaseException("邀新人数未达到要求");

            return;
        }

    }

    /**
     * 统一开奖
     */


    /**
     * 查询中奖情况
     *
     * @param lotteryId
     * @param userId
     */
    public Map<String, Object> selectLotteryResult(String lotteryId, String userId) {
        LotteryRecordsFilterMapper lotteryRecordsFilterMapper = new LotteryRecordsFilterMapper();
        lotteryRecordsFilterMapper.lotteryId = lotteryId;
        lotteryRecordsFilterMapper.userId = userId;
        List<LotteryRecords> lotteryRecordsList = LotteryRecordsFilterService.getListByFilter(lotteryRecordsBaseService, lotteryRecordsFilterMapper);
        if (lotteryRecordsList.isEmpty()) throw new BaseException("未中奖", 5555);
        LotteryRecords lotteryRecords = lotteryRecordsList.get(0);
        RedeemCode redeemCode = redeemCodeBaseService.getById(lotteryRecords.getRedeemCodeId());
        LotteryPrize lotteryPrize = lotteryPrizeBaseService.getById(redeemCode.getLotteryPrizeId());
        Map<String, Object> map = new HashMap<>();
        map.put("text", lotteryPrize.getText());
        map.put("grade", lotteryPrize.getGrade());
        //todo 如果是实体。这里应该是空
        map.put("redeemCode", redeemCode.getText());
        map.put("type",redeemCode.getType());
        return map;
    }


}
