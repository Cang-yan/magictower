package com.yibingo.race.core.service;

import cn.hutool.core.collection.CollectionUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.ExchangeTypeEnum;
import com.yibingo.race.dal.enums.NftReserveEnum;
import com.yibingo.race.dal.enums.NftStatusEnum;
import com.yibingo.race.dal.enums.UserStatusEnum;
import com.yibingo.race.dal.filterMapper.UserFilterMapper;
import com.yibingo.race.dal.filterMapper.WareHouseFilterMapper;
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
 * @time: 2022/7/13 11:52
 */
@Service
public class AirDropService {


    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;

    @Autowired
    private StockBaseService stockBaseService;

    @Autowired
    private BoxHouseBaseService boxHouseBaseService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;

    @Autowired
    private ExchangeRecordsBaseService exchangeRecordsBaseService;

    @Autowired
    private ExchangeBoxRecordsBaseService exchangeBoxRecordsBaseService;

    @Autowired
    private UserBaseService userBaseService;




    /**
     * 空投藏品
     *
     * @param metaNftId
     * @param userQualifyAirDropList
     * @param totalUserCount
     */
    public void airDropNft(String metaNftId, List<Map<String, Object>> userQualifyAirDropList, Integer totalUserCount) {
        //空投
        airDropNftAndBagde(metaNftId, userQualifyAirDropList, totalUserCount);
        //减少库存
        String stockId = metaNftBaseService.getById(metaNftId).getStockId();
        Stock stock = stockBaseService.getById(stockId);
        stock.setReserveCount(stock.getReserveCount() - totalUserCount);
        stockBaseService.updateById(stock);

    }


    /**
     * 空投徽章
     *
     * @param badgeNftId
     * @param userQualifyAirDropList
     * @param totalUserCount
     */
    public void airDropBadge(String badgeNftId, List<Map<String, Object>> userQualifyAirDropList, Integer totalUserCount) {
        //空投
        airDropNftAndBagde(badgeNftId, userQualifyAirDropList, totalUserCount);
        //减少库存
        String stockId = badgeNftBaseService.getById(badgeNftId).getStockId();
        Stock stock = stockBaseService.getById(stockId);
        stock.setReserveCount(stock.getReserveCount() - totalUserCount);
        stockBaseService.updateById(stock);

    }

    /**
     * 空投盲盒 盲盒  一次只能给一个
     * @param boxId
     * @param userQualifyAirDropList
     * @param totalUserCount
     */
    public void airDropBox(String boxId, List<Map<String, Object>> userQualifyAirDropList, Integer totalUserCount) {
        BlindBoxPool blindBoxPool = blindBoxPoolBaseService.getById(boxId);
        Stock stock = stockBaseService.getById(blindBoxPool.getStockId());
        if (stock.getReserveCount() < totalUserCount) throw new BaseException("盲盒预留库存不足，请重新选择用户" );
        List<BoxHouse> boxHouseList = new ArrayList<>();
        List<ExchangeBoxRecords> exchangeBoxRecordsList = new ArrayList<>();

        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList){
            String userId = userQualifyAirDrop.get("userId").toString();

            for (int i =0;i<Integer.parseInt(userQualifyAirDrop.get("airDropCount").toString());i++){
                //把一个盲盒扔进盲盒仓库即可
                BoxHouse boxHouse = new BoxHouse();
                boxHouse.setBlindBoxId(boxId);
                boxHouse.setUserId(userId);
                boxHouseList.add(boxHouse);

                //记录在案
                ExchangeBoxRecords exchangeBoxRecords = new ExchangeBoxRecords();
                exchangeBoxRecords.setBlindBoxId(boxId);
                exchangeBoxRecords.setCount(1);
                exchangeBoxRecords.setHostUserId("root" );
                exchangeBoxRecords.setGuestUserId(userId);
                exchangeBoxRecords.setPrice(0.00);
                exchangeBoxRecords.setType(ExchangeTypeEnum.AirDrop.getKey());
                exchangeBoxRecordsList.add(exchangeBoxRecords);
            }

        }

        boxHouseBaseService.saveBatch(boxHouseList);
        exchangeBoxRecordsBaseService.saveBatch(exchangeBoxRecordsList);

        //修改库存
        stock.setReserveCount(stock.getReserveCount()- totalUserCount);
        stockBaseService.updateById(stock);

    }



    /**
     * 给特定用户空投指定数量的徽章和藏品
     * @param userQualifyAirDropList
     * @param badgeNftId
     * @param totalCount
     */
    public void airDropNftToUserSpecial(List<Map<String, Object>> userQualifyAirDropList, String badgeNftId,Integer totalCount) {
       /* List<String> phoneList = new ArrayList<>();
        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList) {
            phoneList.add(userQualifyAirDrop.get("phone" ).toString());
        }

        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.phoneIn = phoneList;
        List<User> userList = UserFilterService.getListByFilter(userBaseService, userFilterMapper);

        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList) {
            String phone = userQualifyAirDrop.get("phone" ).toString();
            User user = userList.stream().filter(
                    item -> item.getPhone() !=null&& item.getPhone().equals(phone)
            ).findFirst().orElse(null);
            if (user==null) throw new BaseException("phone为"+phone+"的用户未注册");
            userQualifyAirDrop.remove("phone");
            userQualifyAirDrop.put("userId",user.getId());
        }*/

        phoneListTransferToId(userQualifyAirDropList);

        airDropNftAndBagde(badgeNftId, userQualifyAirDropList, totalCount);

        //减少库存
        String stockId = badgeNftBaseService.getById(badgeNftId).getStockId();
        Stock stock = stockBaseService.getById(stockId);
        stock.setReserveCount(stock.getReserveCount() - totalCount);
        stockBaseService.updateById(stock);

    }


    public  void airDropBoxToUserSpecial(List<Map<String, Object>> userQualifyAirDropList, String blindBoxId,Integer totalCount){

        phoneListTransferToId(userQualifyAirDropList);


        airDropBox(blindBoxId,userQualifyAirDropList,totalCount);


    }

    /**
     * 把集合中的 手机号替换成 userId
     * @param userQualifyAirDropList
     */
    private void phoneListTransferToId(List<Map<String, Object>> userQualifyAirDropList){
        List<String> phoneList = new ArrayList<>();
        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList) {
            phoneList.add(userQualifyAirDrop.get("phone" ).toString());
        }

        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.phoneIn = phoneList;
        List<User> userList = UserFilterService.getListByFilter(userBaseService, userFilterMapper);

        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList) {
            String phone = userQualifyAirDrop.get("phone" ).toString();
            User user = userList.stream().filter(
                    item -> item.getPhone() !=null&& item.getPhone().equals(phone)
            ).findFirst().orElse(null);
            if (user==null) throw new BaseException("phone为"+phone+"的用户未注册");
            userQualifyAirDrop.remove("phone");
            userQualifyAirDrop.put("userId",user.getId());
        }

    }



    /**
     * 空投给用户的实际逻辑
     *
     * @param metaNftId
     * @param userQualifyAirDropList
     * @param totalUserCount
     */
    private void airDropNftAndBagde(String metaNftId, List<Map<String, Object>> userQualifyAirDropList, Integer totalUserCount) {
        //去仓库中找可以空投的，就是预留的那些
        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.metaNftId = metaNftId;
        wareHouseFilterMapper.isReserve = NftReserveEnum.IS_RESERVE.getKey();
        wareHouseFilterMapper.nftStatus = NftStatusEnum.NOT_SELL.getKey();
        List<WareHouse> wareHouseList = WareHouseFilterService.getListByFilter(wareHouseBaseService, wareHouseFilterMapper);

        if (wareHouseList.size() < totalUserCount) throw new BaseException("仓库总数量小于指定空投数量，请重新指定" );
        List<WareHouse> updateWareHouses = new ArrayList<>();
        List<ExchangeRecords> exchangeRecordsList = new ArrayList<>();
        //按顺序发给UserId
        int wareHouseSerial = 0;
        for (Map<String, Object> userQualifyAirDrop : userQualifyAirDropList) {
            for (int i = 0; i < Integer.parseInt(userQualifyAirDrop.get("airDropCount" ).toString()); i++) {
                WareHouse wareHouse = wareHouseList.get(wareHouseSerial);
                wareHouseSerial++;
                String userId = userQualifyAirDrop.get("userId" ).toString();

                wareHouse.setUserId(userId);
                wareHouse.setNftStatus(NftStatusEnum.DID_SELL.getKey());
                wareHouse.setUserStatus(UserStatusEnum.GOT.getKey());
                updateWareHouses.add(wareHouse);
                //写在交易记录中
                ExchangeRecords exchangeRecords = new ExchangeRecords();
                exchangeRecords.setHostUserId("root" );
                exchangeRecords.setGuestUserId(userId);
                exchangeRecords.setMetaNftId(metaNftId);
                exchangeRecords.setUuTag(wareHouse.getUuTag());
                exchangeRecords.setPrice(0.00);
                exchangeRecords.setType(ExchangeTypeEnum.AirDrop.getKey());
                exchangeRecordsList.add(exchangeRecords);
            }

        }

       /* for (Map.Entry<String,Object> userAirDrop : userQualifyAirDrop.entrySet()){
            for (int i = 0;i<userAirDrop.getValue();i++){
                WareHouse wareHouse = wareHouseList.get(wareHouseSerial);
                wareHouseSerial++;
                String userId = userAirDrop.getKey();

                wareHouse.setUserId(userId);
                wareHouse.setNftStatus(NftStatusEnum.DID_SELL.getKey());
                wareHouse.setUserStatus(UserStatusEnum.GOT.getKey());
                updateWareHouses.add(wareHouse);
                //写在交易记录中
                ExchangeRecords exchangeRecords = new ExchangeRecords();
                exchangeRecords.setHostUserId("root");
                exchangeRecords.setGuestUserId(userId);
                exchangeRecords.setMetaNftId(metaNftId);
                exchangeRecords.setUuTag(wareHouse.getUuTag());
                exchangeRecords.setPrice(0.00);
                exchangeRecords.setType(ExchangeTypeEnum.AirDrop.getKey());
                exchangeRecordsList.add(exchangeRecords);
            }

        }*/

       /* for (int i = 0; i < userIdList.size(); i++) {
            WareHouse wareHouse = wareHouseList.get(i);
            String userId = userIdList.get(i);
            wareHouse.setUserId(userId);
            wareHouse.setNftStatus(NftStatusEnum.DID_SELL.getKey());
            wareHouse.setUserStatus(UserStatusEnum.GOT.getKey());
            updateWareHouses.add(wareHouse);
            //写在交易记录中
            ExchangeRecords exchangeRecords = new ExchangeRecords();
            exchangeRecords.setHostUserId("root");
            exchangeRecords.setGuestUserId(userId);
            exchangeRecords.setMetaNftId(metaNftId);
            exchangeRecords.setUuTag(wareHouse.getUuTag());
            exchangeRecords.setPrice(0.00);
            exchangeRecords.setType(ExchangeTypeEnum.AirDrop.getKey());
            exchangeRecordsList.add(exchangeRecords);
        }*/

        wareHouseBaseService.updateBatchById(updateWareHouses);
        exchangeRecordsBaseService.saveBatch(exchangeRecordsList);
    }


}
