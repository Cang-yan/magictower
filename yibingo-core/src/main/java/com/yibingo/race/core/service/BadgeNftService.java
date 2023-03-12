package com.yibingo.race.core.service;

import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.NftReserveEnum;
import com.yibingo.race.dal.enums.NftStatusEnum;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.enums.UserStatusEnum;
import com.yibingo.race.dal.filterMapper.BadgeNftFilterMapper;
import com.yibingo.race.dal.filterMapper.WareHouseFilterMapper;
import com.yibingo.race.dal.putMapper.BadgeNftPutMapper;
import com.yibingo.race.dal.putMapper.BenefitBadgePutMapper;
import com.yibingo.race.dal.putMapper.WareHousePutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/14 17:22
 */
@Service
public class BadgeNftService {

    @Autowired
    private StockBaseService stockBaseService;
    @Autowired
    private BadgeNftBaseService badgeNftBaseService;
    @Autowired
    private BenefitBadgeBaseService benefitBadgeBaseService;
    @Autowired
    private WareHouseBaseService wareHouseBaseService;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private UserBaseService userBaseService;


    //查询目前所有徽章的基本信息

    /**
     * 查询目前所有徽章的基本信息  包括 权益，库存
     *
     * @param badgeNftFilterMapper
     * @return
     */
    public List<Map<String, Object>> getBadgeBaseInfo(BadgeNftFilterMapper badgeNftFilterMapper) {
        List<BadgeNft> badgeNftList = BadgeNftFilterService.getListByFilter(badgeNftBaseService, badgeNftFilterMapper);
        List<String> badgeNftIdList = badgeNftList.stream().map(BadgeNft::getId).collect(Collectors.toList());
        List<BenefitBadge> benefitBadgeList = benefitBadgeBaseService.listByIds(badgeNftIdList);
        List<Map<String, Object>> badgeInfoMapList = new ArrayList<>();
        for (BadgeNft badgeNft : badgeNftList) {
            Map<String, Object> badgeNftMap = baseInfoService.badgeInfoByEntity(badgeNft);
            Stock stock = stockBaseService.getById(badgeNft.getStockId());
            badgeNftMap.put("stockPreCount", stock.getPreCount());
            badgeNftMap.put("stockNowCount", stock.getNowCount());
            badgeNftMap.put("stockReserveCount", stock.getReserveCount());

            //这个每一个badge和benefit应该是一一对应的
            BenefitBadge benefitBadge = benefitBadgeList.stream().filter(
                    item -> item.getBadgeNftId() != null && item.getBadgeNftId().equals(badgeNft.getId())
            ).findFirst().orElse(null);

            badgeNftMap.put("benefitPrebuyCount", benefitBadge.getBenefitPrebuyCount());
            badgeNftMap.put("benefitIsAll", benefitBadge.getIsAll());
            badgeNftMap.put("benefitFamilyId", benefitBadge.getFamilyId());
            badgeNftMap.put("benefitFamilyName", benefitBadge.getFamilyName());

            badgeInfoMapList.add(badgeNftMap);
        }

        return badgeInfoMapList;

    }


    /**
     * 创建徽章
     *
     * @param badgeNftPutMapper
     * @param benefitBadgePutMapper
     * @param totalCount
     * @param reverseCount
     */
    public void createBadgeNft(BadgeNftPutMapper badgeNftPutMapper, BenefitBadgePutMapper benefitBadgePutMapper, Integer totalCount, Integer reverseCount) {
        // 创建徽章
        //涉及到 预留的  售卖的份数
        //系列  徽章系列吧
        //徽章权益

        Stock stock = new Stock();
        stock.setReserveCount(reverseCount);
        stock.setPreCount(totalCount);
        stock.setNowCount(totalCount - reverseCount);
        //存库存
        stockBaseService.save(stock);

        //存徽章
        BadgeNft badgeNft = BadgeNftPutMapper.convertToEntity(badgeNftPutMapper);
        badgeNft.setStockId(stock.getId());
        badgeNftBaseService.save(badgeNft);

        //存权益
        BenefitBadge benefitBadge = BenefitBadgePutMapper.convertToEntity(benefitBadgePutMapper);
        benefitBadge.setBadgeNftId(badgeNft.getId());
        benefitBadgeBaseService.save(benefitBadge);
        //藏品仓库要存一下
        WareHousePutMapper wareHousePutMapper = new WareHousePutMapper();
        wareHousePutMapper.setFamilyId(badgeNft.getFamilyId());
        wareHousePutMapper.setMetaNftId(badgeNft.getId());
        wareHousePutMapper.setType(NftTypeEnum.BADGE.getKey());
        wareHousePutMapper.setNftStatus(NftStatusEnum.NOT_SELL.getKey());
        wareHousePutMapper.setUserStatus(UserStatusEnum.MEANINGLESS.getKey());

        List<WareHouse> wareHouseList = new ArrayList<>();

        //为藏品仓库中每一条记录设置uu_tag
        for (int uuTag = 1; uuTag <= stock.getPreCount(); uuTag++) {
            WareHouse wareHouse = WareHousePutMapper.convertToEntity(wareHousePutMapper);

            //预留的藏品编号在前，售卖的藏品编号在后
            if (uuTag <= stock.getReserveCount()) {
                wareHouse.setIsReserve(NftReserveEnum.IS_RESERVE.getKey());
            } else {
                wareHouse.setIsReserve(NftReserveEnum.NOT_RESERVE.getKey());
            }

            //设置uu_tag
            wareHouse.setUuTag(uuTag);

            wareHouseList.add(wareHouse);

        }
        wareHouseBaseService.saveBatch(wareHouseList);

    }

    //todo 涉及到wareHouse的userID的指定，即藏品的获取，一定要搞的万无一失


    /**
     * 取 同时拥有指定的徽章的用户和空投机会
     * 查找指定徽章拥有 用户Id
     *
     * @param badgeNftIdList
     * @return
     */

    public Map<String, Object> getOwnAllThose(List<String> badgeNftIdList) {

        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        //得先判断有主人
        wareHouseFilterMapper.nftStatus = NftStatusEnum.DID_SELL.getKey();
        wareHouseFilterMapper.metaNftIdIn = badgeNftIdList;
        List<WareHouse> wareHouseList = WareHouseFilterService.getListByFilter(wareHouseBaseService, wareHouseFilterMapper);
        if (wareHouseList.isEmpty()) return new HashMap<>();
        //用户的徽章拥有情况
        List<Map<String,Object>> userOwnConditionMapList = getUserOwnCondition(wareHouseList, badgeNftIdList);
        //用户所能获得的空投机会
        List<Map<String, Object>> userQualifyAirDropMapList = userQualifyAirDrop(userOwnConditionMapList);
        Map<String, Object> endInfoMap = new HashMap<>();
        Integer totalCount = 0;
        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropMapList) {
            endInfoMap.put(userQualifyAirDrop.get("userId").toString(), userQualifyAirDrop);
            totalCount = totalCount + Integer.parseInt(userQualifyAirDrop.get("airDropCount").toString());
        }
        endInfoMap.put("totalCount", totalCount);

        //return getUserBaseInfoByNft(wareHouseList);
        return endInfoMap;

    }


    //查找指定徽章拥有其中之一id
//    public List<Map<String,Object>> getOwnOneThose(List<String> badgeNftIdList){
//
//
//    }

    /**
     * 用藏品nft仓库 拿用户基本信息
     *
     * @param wareHouseList
     * @return
     */
    private List<Map<String, Object>> getUserBaseInfoByNft(List<WareHouse> wareHouseList) {
        List<Map<String, Object>> userInfoList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();
        for (WareHouse wareHouse : wareHouseList) {
            if (wareHouse.getUserId() == null) throw new BaseException("藏品仓库id" + wareHouse.getId() + ",存在问题，请核实");
            userIdList.add(wareHouse.getUserId());
        }
        //就是说这个人有几个
        //这里不可能为空
        //List<String> endUserIdList = userIdList.stream().distinct().collect(Collectors.toList());
        //数据库查询是去重了的
        List<User> userList = userBaseService.listByIds(userIdList);

        for (User user : userList) {
            Map<String, Object> infoMap = baseInfoService.userBaseInfoByEntity(user);
            userInfoList.add(infoMap);
        }
        return userInfoList;
    }

    /**
     * 取出同时拥有指定徽章的用户  假设拥有情况如下
     * A     B      C     D
     * 1     2       4    1
     * 让每个减一，完整的减一轮则算一个空投资格  也可以取最小的那个值，但是每个啥样可能就不太清楚了
     *
     * @param conditionMapList
     * @return
     *
     *
     *    {
     *     "userId": "1",
     *     "airDropCount": 2
     *   },
     *   {
     *     "userId": "2",
     *     "airDropCount": 3
     *   },
     *   {
     *     "userId": "3",
     *     "airDropCount": 1
     *   },
     *
     *
     */
    private List<Map<String, Object>> userQualifyAirDrop(List<Map<String, Object>> conditionMapList) {
        List<Map<String, Object>> userQualifyAirDropMapList = new ArrayList<>();
        //Map<String, Integer> userQualifyAirDrop = new HashMap<>();
        for (Map<String, Object> conditionMap : conditionMapList) {
            //Map<String, Integer> userConditionMap = conditionMap.getValue();
            Map<String, Object> userQualifyAirDropMap = new HashMap<>();
            userQualifyAirDropMap.put("userId", conditionMap.get("userId"));
            int loopFlag = 1;
            Integer qualifyAirDrop = 0;

            do {
                int i = 0;
                for (Map.Entry<String, Object> userConditionMapEntry : conditionMap.entrySet()) {
                    //如果存在为0，说明本次判定中该用户没有符合条件，判定终止
                    if (userConditionMapEntry.getKey().equals("userId")){
                        continue;
                    }
                    Integer valueInMap = Integer.parseInt(userConditionMapEntry.getValue().toString());
                    if (valueInMap<= 0) {
                        loopFlag = 0;
                        break;
                    }
                    //每循环一次就减掉一个1
                    Integer newValue = valueInMap-1;
                    userConditionMapEntry.setValue(newValue);
                    i++;

                }
                //若满循环一次，就说明满足空投资格一次
                if (i == conditionMap.size()-1) qualifyAirDrop = qualifyAirDrop + 1;

            } while (loopFlag == 1);

            //用户id和空投份数绑定
            userQualifyAirDropMap.put("airDropCount",qualifyAirDrop);
            userQualifyAirDropMapList.add(userQualifyAirDropMap);
        }

        return userQualifyAirDropMapList;
     /*   for (Map.Entry<String, Map<String, Integer>> conditionMapEntry : conditionMap.entrySet()) {
            Map<String, Integer> userConditionMap = conditionMapEntry.getValue();
            int loopFlag = 1;
            Integer qualifyAirDrop = 0;
            do {
                int i = 0;
                for (Map.Entry<String, Integer> userConditionMapEntry : userConditionMap.entrySet()) {
                    //如果存在为0，说明本次判定中该用户没有符合条件，判定终止
                    if (userConditionMapEntry.getValue() <= 0) {
                        loopFlag = 0;
                        break;
                    }
                    //每循环一次就减掉一个1
                    userConditionMapEntry.setValue(userConditionMapEntry.getValue() - 1);
                    i++;

                }
                //若满循环一次，就说明满足空投资格一次
                if (i == userConditionMap.size()) qualifyAirDrop = qualifyAirDrop + 1;

            } while (loopFlag == 1);

            //用户id和空投份数绑定
            userQualifyAirDrop.put(conditionMapEntry.getKey(), qualifyAirDrop);

        }
        return userQualifyAirDrop;*/
    }


    /**
     * 指定徽章的用户拥有情况
     * <p>
     * return值为：
     * {
     * "userId": "1",
     * "15521200": 2,
     * "4987981651": 4
     * },
     * {
     * "userId": "2",
     * "15521200": 2,
     * "4987981651": 4
     * },
     * {
     * "userId": "3",
     * "15521200": 2,
     * "4987981651": 4
     * },
     *
     * @param wareHouseList
     * @param badgeNftIdList
     * @return
     */
    private List<Map<String, Object>> getUserOwnCondition(List<WareHouse> wareHouseList, List<String> badgeNftIdList) {

        List<Map<String, Object>> conditionMapList = new ArrayList<>();
        List<String> userIdList = wareHouseList.stream().map(WareHouse::getUserId).distinct().collect(Collectors.toList());
        for (String userId : userIdList) {
            List<WareHouse> userWareHouse = wareHouseList.stream().filter(
                    item -> item.getUserId() != null && item.getUserId().equals(userId)
            ).collect(Collectors.toList());
            //该用户的藏品拥有情况
            Map<String, Object> userConditionInfoMap = new HashMap<>();
            userConditionInfoMap.put("userId", userId);

            for (String badgeNftId : badgeNftIdList) {

                Integer badgeNftNum = userWareHouse.stream().filter(
                        item -> item.getMetaNftId() != null && item.getMetaNftId().equals(badgeNftId)
                ).collect(Collectors.toList()).size();

                userConditionInfoMap.put(badgeNftId, badgeNftNum);
            }

            conditionMapList.add(userConditionInfoMap);
        }

        return conditionMapList;

      /*  Map<String, Map<String, Integer>> conditionMap = new HashMap<>();
        List<String> userIdList = wareHouseList.stream().map(WareHouse::getUserId).distinct().collect(Collectors.toList());

        for (String userId : userIdList) {
            List<WareHouse> userWareHouse = wareHouseList.stream().filter(
                    item -> item.getUserId() != null && item.getUserId().equals(userId)
            ).collect(Collectors.toList());
            //该用户的藏品拥有情况
            Map<String, Integer> userWareHouseCondition = new HashMap<>();

            for (String badgeNftId : badgeNftIdList) {

                Integer badgeNftNum = userWareHouse.stream().filter(
                        item -> item.getMetaNftId() != null && item.getMetaNftId().equals(badgeNftId)
                ).collect(Collectors.toList()).size();
                userWareHouseCondition.put(badgeNftId, badgeNftNum);
            }

            conditionMap.put(userId, userWareHouseCondition);
        }


        return conditionMap;*/
    }
}
