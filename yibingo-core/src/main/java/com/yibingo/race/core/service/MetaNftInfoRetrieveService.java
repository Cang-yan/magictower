package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.NftStatusEnum;
import com.yibingo.race.dal.filterMapper.WareHouseFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li XingHan
 * @description 查询藏品的相关信息
 * @date 2022-06-27 9:58
 */
@Service
public class MetaNftInfoRetrieveService {


    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private UserBaseService userBaseService;

    public List<Map<String, Object>> getHoldDetail(String metaNftId) {

        //TODO 根据藏品id查询此藏品在藏品仓库的信息
        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.metaNftId = metaNftId;
        List<WareHouse> listByNftId = WareHouseFilterService
                .getListByFilter(wareHouseBaseService, wareHouseFilterMapper);


        List<Map<String, Object>> result = new ArrayList<>();

        return result;
    }

    private List<Map<String, Object>> kk(List<WareHouse> list) {

        List<Map<String, Object>> result = new ArrayList<>();

        for (WareHouse wareHouse : list) {
            Map<String, Object> data = new HashMap<>();

            //藏品仓库中此藏品的基本信息 nftStatus,uuTag
            Integer nftStatus = wareHouse.getNftStatus();
            Integer uuTag = wareHouse.getUuTag();

            data.put("nftStatus", NftStatusEnum.getStatusByKey(nftStatus));
            data.put("uuTag", uuTag);

            //藏品仓库中此藏品对应的用户信息
            //如userId为空则不插入
            String userId = wareHouse.getUserId();
            if (userId == null) {

            }
            User user = userBaseService.getById(userId);
            String accountId = user.getAccountId();
            String name = user.getName();
            String head = user.getHead();

            data.put("accountId", accountId);
            data.put("name", name);
            data.put("head", head);

            result.add(data);
        }
        return result;
    }

}
