package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.NftStatusEnum;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.enums.PropTypeEnum;
import com.yibingo.race.dal.filterMapper.*;
import com.yibingo.race.dal.putMapper.WareHousePutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/6/28 15:28
 */
@Service
public class UserNftHouseService {


    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private BaseInfoService baseInfoUtil;

    @Autowired
    private PropHourseBaseService propHourseBaseService;

    @Autowired
    private PropBaseService propBaseService;

    @Autowired
    private BadgeHouseBaseService badgeHouseBaseService;

    @Autowired
    private PieceHouseBaseService pieceHouseBaseService;

    @Autowired
    private PieceBaseService pieceBaseService;

    @Autowired
    private BoxHouseBaseService boxHouseBaseService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;


    /**
     * 根据userId查询英雄藏品记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectNftHeroList(String userId) {

        //筛选符合userId的藏品记录
        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.userId = userId;
        wareHouseFilterMapper.nftStatus = NftStatusEnum.DID_SELL.getKey();
        wareHouseFilterMapper.type = NftTypeEnum.HERO.getKey();
        List<WareHouse> wareHouseList = WareHouseFilterService
                .getListByFilter(wareHouseBaseService, wareHouseFilterMapper);

        List<Map<String, Object>> heroList = new ArrayList<>();

        //将相关信息封装为map
        for (WareHouse wareHouse : wareHouseList) {

            Map<String, Object> data = baseInfoUtil.wareHouseBaseInfoByEntity(wareHouse);
            MetaNft nft = metaNftBaseService.getById(wareHouse.getMetaNftId());
            data.put("baseNft" , baseInfoUtil.nftInfoByEntity(nft));
            heroList.add(data);
           /* if (Objects.equals(wareHouse.getType(), NftTypeEnum.HERO.getKey())) {
                heroList.add(data);
            }*/
        }

        return heroList;
    }

    /**
     * 根据userId查询装备藏品记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectNftEquipList(String userId) {

        //筛选符合userId的藏品记录
        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.userId = userId;
        wareHouseFilterMapper.type = NftTypeEnum.EQUIP.getKey();
        wareHouseFilterMapper.nftStatus = NftStatusEnum.DID_SELL.getKey();
        List<WareHouse> wareHouseList = WareHouseFilterService
                .getListByFilter(wareHouseBaseService, wareHouseFilterMapper);

        List<Map<String, Object>> equipList = new ArrayList<>();

        //将相关信息封装为map
        for (WareHouse wareHouse : wareHouseList) {

            Map<String, Object> data = baseInfoUtil.wareHouseBaseInfoByEntity(wareHouse);
            MetaNft nft = metaNftBaseService.getById(wareHouse.getMetaNftId());
            data.put("baseNft" , baseInfoUtil.nftInfoByEntity(nft));
            equipList.add(data);
           /* if (Objects.equals(wareHouse.getType(), NftTypeEnum.EQUIP.getKey())) {
                equipList.add(data);
            }*/
        }

        return equipList;
    }


    /**
     * 根据userId查询盲盒记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectBoxList(String userId) {

        //筛选符合userId的盲盒记录
        BoxHouseFilterMapper boxHouseFilterMapper = new BoxHouseFilterMapper();
        boxHouseFilterMapper.userId = userId;
        List<BoxHouse> boxHouseList = BoxHouseFilterService
                .getListByFilter(boxHouseBaseService, boxHouseFilterMapper);

        //将相关信息封装成map
        List<Map<String, Object>> result = new ArrayList<>();
        for (BoxHouse boxHouse : boxHouseList) {
            Map<String, Object> data = baseInfoUtil.boxHouseBaseInfoByEntity(boxHouse);
            BlindBoxPool blindBox = blindBoxPoolBaseService.getById(boxHouse.getBlindBoxId());
            data.put("baseNft" , baseInfoUtil.blindBoxInfoByEntity(blindBox));

            result.add(data);
        }

        return result;

    }


    /**
     * 根据userId查询拥有的道具
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectPropList(String userId) {

        //筛选符合userId的道具仓库记录
        PropHourseFilterMapper propHourseFilterMapper = new PropHourseFilterMapper();
        propHourseFilterMapper.userId = userId;

        List<PropHourse> propHourseList = PropHourseFilterService
                .getListByFilter(propHourseBaseService, propHourseFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();

        //将每个道具信息封装成map
        for (PropHourse propHourse : propHourseList) {
            Map<String, Object> data = new HashMap<>();

            String propId = propHourse.getPropId();
            Prop prop = propBaseService.getById(propId);
            String name = prop.getName();
            String introduce = prop.getIntroduce();
            Integer type = prop.getType();
            String url = prop.getUrl();

            //道具信息包括count数量、name名称、introduce介绍、type类型、url图片
            data.put("createTime" , propHourse.getCreateTime());
            data.put("count" , propHourse.getCount());
            data.put("name" , name);
            data.put("introduce" , introduce);
            data.put("type" , PropTypeEnum.getTypeByKey(type));
            data.put("url" , url);

            result.add(data);
        }

        return result;
    }


    /**
     * 根据userId查询拥有的徽章
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectBadgeList(String userId) {

        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.userId = userId;
        wareHouseFilterMapper.nftStatus = NftStatusEnum.DID_SELL.getKey();
        wareHouseFilterMapper.type = NftTypeEnum.BADGE.getKey();
        List<WareHouse> wareHouseList = WareHouseFilterService.getListByFilter(wareHouseBaseService, wareHouseFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();

        for (WareHouse wareHouse : wareHouseList) {
            Map<String, Object> data = baseInfoUtil.wareHouseBaseInfoByEntity(wareHouse);
            data.put("baseNft" , baseInfoUtil.badgeInfoById(wareHouse.getMetaNftId()));
            result.add(data);
        }

        return result;
    }

    /**
     * 根据userId查询拥有的碎片
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectPieceList(String userId) {

        //筛选符合userId的碎片仓库的记录
        PieceHouseFilterMapper pieceHouseFilterMapper = new PieceHouseFilterMapper();
        pieceHouseFilterMapper.userId = userId;
        List<PieceHouse> pieceHouseList = PieceHouseFilterService
                .getListByFilter(pieceHouseBaseService, pieceHouseFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();

        //将碎片仓库的信息封装为map
        for (PieceHouse pieceHouse : pieceHouseList) {
            Map<String, Object> data = new HashMap<>();

            Integer count = pieceHouse.getCount();
            String pieceId = pieceHouse.getPieceId();
            Piece piece = pieceBaseService.getById(pieceId);
            Map<String, Object> metaNft = baseInfoUtil.nftBaseInfoById(piece.getMetaNftId());

            //封装的map信息包括count数量、对应的藏品metaNft
            data.put("createTime" , pieceHouse.getCreateTime());
            data.put("count" , count);
            data.put("metaNft" , metaNft);

            result.add(data);
        }

        return result;
    }

    /**
     * 根据wareId添加拥有的藏品
     * todo 逻辑有问题啊大噶
     *
     * @param wareId
     * @return
     */
    public boolean addWardHouse(String wareId, String userId) {

        //筛选符合userId的碎片仓库的记录
        MetaNftFilterMapper metaNftFilterMapper = new MetaNftFilterMapper();
        metaNftFilterMapper.id = wareId;
        MetaNft metaNft = MetaNftFilterService
                .getListByFilter(metaNftBaseService, metaNftFilterMapper).get(0);

        WareHousePutMapper wareHousePutMapper = new WareHousePutMapper();
        //uutag是个啥？
        wareHousePutMapper.setMetaNftId(metaNft.getId());
        wareHousePutMapper.setFamilyId(metaNft.getFamilyId());
        wareHousePutMapper.setUserStatus(0);//已获得
        //ntfStatus?
        //isReserve?
        //type?
        wareHousePutMapper.setUserId(userId);
        return wareHouseBaseService.save(WareHousePutMapper.convertToEntity(wareHousePutMapper));
    }

}
