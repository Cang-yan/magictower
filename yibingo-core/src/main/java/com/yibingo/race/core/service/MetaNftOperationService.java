package com.yibingo.race.core.service;

import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.detailMapper.StockDetailMapper;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.NftReserveEnum;
import com.yibingo.race.dal.enums.NftStatusEnum;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.enums.UserStatusEnum;
import com.yibingo.race.dal.filterMapper.*;
import com.yibingo.race.dal.putMapper.MetaNftPutMapper;
import com.yibingo.race.dal.putMapper.StockPutMapper;
import com.yibingo.race.dal.putMapper.WareHousePutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Li XingHan
 * @date 2022-06-28 15:37
 */
@Service
public class MetaNftOperationService {

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private StockBaseService stockBaseService;

    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private FamilyBaseService familyBaseService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;


    public List<Map<String, Object>> sarchFamilyName(String familyName, Integer nftType) {
        List<Map<String, Object>> dataMapList = new ArrayList<>();

        FamilyFilterMapper familyFilterMapper = new FamilyFilterMapper();
        familyFilterMapper.title = familyName;
        //按理说只能有一个嗷
        List<Family> familyList = FamilyFilterService.getListByFilter(familyBaseService, familyFilterMapper);
        if (familyList.isEmpty()) throw new BaseException("无该系列");
        String familyId = familyList.get(0).getId();

        if (nftType.equals(NftTypeEnum.BADGE.getKey())) {
            BadgeNftFilterMapper badgeNftFilterMapper = new BadgeNftFilterMapper();
            badgeNftFilterMapper.familyId = familyId;
            List<BadgeNft> badgeNftList = BadgeNftFilterService.getListByFilter(badgeNftBaseService, badgeNftFilterMapper);
            for (BadgeNft badgeNft : badgeNftList) {
                Map<String, Object> baseNftInfoMap = baseInfoService.badgeInfoByEntity(badgeNft);
                Stock stock = stockBaseService.getById(badgeNft.getStockId());
                baseNftInfoMap.put("stock", StockDetailMapper.buildMap(stock));
                dataMapList.add(baseNftInfoMap);
            }


        } else if (nftType.equals(NftTypeEnum.BlindBox.getKey())) {
            BlindBoxPoolFilterMapper blindBoxPoolFilterMapper = new BlindBoxPoolFilterMapper();
            blindBoxPoolFilterMapper.familyId = familyId;
            List<BlindBoxPool> blindBoxPoolList = BlindBoxPoolFilterService.getListByFilter(blindBoxPoolBaseService, blindBoxPoolFilterMapper);
            for (BlindBoxPool blindBoxPool : blindBoxPoolList) {
                Map<String, Object> baseNftInfoMap = baseInfoService.blindBoxInfoByEntity(blindBoxPool);
                Stock stock = stockBaseService.getById(blindBoxPool.getStockId());
                baseNftInfoMap.put("stock", StockDetailMapper.buildMap(stock));
                dataMapList.add(baseNftInfoMap);

            }
        } else if (nftType.equals(NftTypeEnum.EQUIP.getKey()) || nftType.equals(NftTypeEnum.HERO.getKey())) {
            MetaNftFilterMapper metaNftFilterMapper = new MetaNftFilterMapper();
            metaNftFilterMapper.familyId = familyId;
            metaNftFilterMapper.type = nftType;
            List<MetaNft> metaNftList = MetaNftFilterService.getListByFilter(metaNftBaseService, metaNftFilterMapper);
            for (MetaNft metaNft : metaNftList) {
                Map<String, Object> baseNftInfoMap = baseInfoService.nftInfoByEntity(metaNft);
                Stock stock = stockBaseService.getById(metaNft.getStockId());
                baseNftInfoMap.put("stock", StockDetailMapper.buildMap(stock));
                dataMapList.add(baseNftInfoMap);
            }
        }

        return dataMapList;

    }


    /**
     * 创建新的藏品
     * 设置库存（总库存和预留库存）
     * 生成藏品编号uu_tag
     *
     * @param metaNftPutMapper
     * @param totalCount
     * @param reverseCount
     */
    public void createNewMetaNft(MetaNftPutMapper metaNftPutMapper, Integer totalCount, Integer reverseCount) {

        //根据metaNftPutMapper中的信息创建新的藏品，并设置库存
        MetaNft metaNft = MetaNftPutMapper.convertToEntity(metaNftPutMapper);
        setStock(metaNft, totalCount, reverseCount);
        metaNftBaseService.save(metaNft);


        //设置藏品仓库中与此藏品相关的信息
        WareHousePutMapper wareHousePutMapper = new WareHousePutMapper();
        wareHousePutMapper.setMetaNftId(metaNft.getId());
        wareHousePutMapper.setFamilyId(metaNft.getFamilyId());
        wareHousePutMapper.setType(metaNft.getType());
        wareHousePutMapper.setNftStatus(NftStatusEnum.NOT_SELL.getKey());
        wareHousePutMapper.setUserStatus(UserStatusEnum.MEANINGLESS.getKey());
        wareHousePutMapper.setUserId(null);
        wareHousePutMapper.setUuTag(null);

        List<WareHouse> wareHouseList = new ArrayList<>();

        //为藏品仓库中每一条记录设置uu_tag
        for (int uuTag = 1; uuTag <= totalCount; uuTag++) {
            WareHouse wareHouse = WareHousePutMapper.convertToEntity(wareHousePutMapper);

            //预留的藏品编号在前，售卖的藏品编号在后
            if (uuTag <= reverseCount) {
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


    /**
     * 根据藏品id修改产品的相关信息
     *
     * @param metaNftPutMapper
     * @param metaNftId
     */
    public void updateMetaNft(MetaNftPutMapper metaNftPutMapper, String metaNftId) {

        //更新藏品记录的相关信息
        MetaNft oldMetaNft = metaNftBaseService.getById(metaNftId);
        MetaNft newMetaNft = MetaNftPutMapper.convertToEntity(metaNftPutMapper);
        newMetaNft.setId(metaNftId);
        metaNftBaseService.updateById(newMetaNft);


        //如果藏品信息类型type或者系列family发生修改，藏品仓库中相应信息也需修改
        if (!Objects.equals(oldMetaNft.getType(), newMetaNft.getType())
                || !Objects.equals(oldMetaNft.getFamilyId(), newMetaNft.getFamilyId())) {

            //查询此藏品对应藏品仓库的所有记录
            WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
            wareHouseFilterMapper.metaNftId = metaNftId;
            List<WareHouse> listByNftId = WareHouseFilterService
                    .getListByFilter(wareHouseBaseService, wareHouseFilterMapper);

            //修改type和family字段
            for (WareHouse wareHouse : listByNftId) {
                wareHouse.setFamilyId(newMetaNft.getFamilyId());
                wareHouse.setType(newMetaNft.getType());
            }

            wareHouseBaseService.updateBatchById(listByNftId);
        }


    }


    /**
     * 设置新增藏品的库存
     *
     * @param metaNft
     * @param totalCount
     * @param reverseCount
     */
    private void setStock(MetaNft metaNft, Integer totalCount, Integer reverseCount) {
        //根据count设置库存初始值
        StockPutMapper stockPutMapper = new StockPutMapper();
        stockPutMapper.setPreCount(totalCount);
        stockPutMapper.setReserveCount(reverseCount);
        stockPutMapper.setNowCount(totalCount - reverseCount);

        //将库存与藏品关联
        Stock stock = StockPutMapper.convertToEntity(stockPutMapper);
        stockBaseService.save(stock);
        metaNft.setStockId(stock.getId());
    }
}
