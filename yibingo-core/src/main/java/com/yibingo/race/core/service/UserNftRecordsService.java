package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.detailMapper.ExchangeRecordsDetailMapper;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.ExchangeTypeEnum;
import com.yibingo.race.dal.filterMapper.*;
import com.yibingo.race.dal.putMapper.ExchangeBoxRecordsPutMapper;
import com.yibingo.race.dal.putMapper.ExchangeRecordsPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: 用户对藏品交易记录、邀请记录、盲盒购买记录、合成记录的查询、转赠商品
 * @author: Yang Xin
 * @time: 2022/6/28 15:31
 */
@Service
public class UserNftRecordsService {

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    @Autowired
    private PieceComRecordsBaseService pieceComRecordsBaseService;

    @Autowired
    private PieceComRuleBaseService pieceComRuleBaseService;

    @Autowired
    private ExchangeBoxRecordsBaseService exchangeBoxRecordsBaseService;

    @Autowired
    private ExchangeRecordsBaseService exchangeRecordsBaseService;

    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private BoxHouseBaseService boxHouseBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;

    @Autowired
    private UserBaseService userBaseService;

    /**
     * 查询转增给我的  转增记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectTransferInList(String userId) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        ExchangeRecordsFilterMapper exchangeRecordsFilterMapper = new ExchangeRecordsFilterMapper();
        exchangeRecordsFilterMapper.type = ExchangeTypeEnum.Donation.getKey();
        exchangeRecordsFilterMapper.hostUserId = userId;
        List<ExchangeRecords> exchangeRecordsList = ExchangeRecordsFilterService.getListByFilter(exchangeRecordsBaseService, exchangeRecordsFilterMapper);
        for (ExchangeRecords exchangeRecords : exchangeRecordsList) {
            Map<String, Object> data = ExchangeRecordsDetailMapper.buildMap(exchangeRecords);
            String url = "", nftName = "", familyName = "";
            data.put("guestUserHead", userBaseService.getById(exchangeRecords.getGuestUserId()).getHead());
            if (exchangeRecords.getType().equals(ExchangeTypeEnum.Donation.getKey())) {

                MetaNft metaNft = metaNftBaseService.getById(exchangeRecords.getMetaNftId());
                url = metaNft.getUrl();
                nftName = metaNft.getNickname();
                familyName = metaNft.getFamilyName();
            } else if (exchangeRecords.getType().equals(ExchangeTypeEnum.BadgeDonation.getKey())) {
                BadgeNft badgeNft = badgeNftBaseService.getById(exchangeRecords.getMetaNftId());
                url = badgeNft.getUrl();
                nftName = badgeNft.getNickname();
                familyName = badgeNft.getFamilyName();
            }
            data.put("url", url);
            data.put("nftName", nftName);
            data.put("familyName", familyName);
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 查询转增给别人的  转增记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectTransferOutList(String userId) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        ExchangeRecordsFilterMapper exchangeRecordsFilterMapper = new ExchangeRecordsFilterMapper();
        exchangeRecordsFilterMapper.type = ExchangeTypeEnum.Donation.getKey();
        exchangeRecordsFilterMapper.guestUserId = userId;
        List<ExchangeRecords> exchangeRecordsList = ExchangeRecordsFilterService.getListByFilter(exchangeRecordsBaseService, exchangeRecordsFilterMapper);
        for (ExchangeRecords exchangeRecords : exchangeRecordsList) {
            Map<String, Object> data = ExchangeRecordsDetailMapper.buildMap(exchangeRecords);
            String url = "", nftName = "", familyName = "";
            data.put("guestUserHead", userBaseService.getById(exchangeRecords.getGuestUserId()).getHead());
            if (exchangeRecords.getType().equals(ExchangeTypeEnum.Donation.getKey())) {

                MetaNft metaNft = metaNftBaseService.getById(exchangeRecords.getMetaNftId());
                url = metaNft.getUrl();
                nftName = metaNft.getNickname();
                familyName = metaNft.getFamilyName();
            } else if (exchangeRecords.getType().equals(ExchangeTypeEnum.BadgeDonation.getKey())) {
                BadgeNft badgeNft = badgeNftBaseService.getById(exchangeRecords.getMetaNftId());
                url = badgeNft.getUrl();
                nftName = badgeNft.getNickname();
                familyName = badgeNft.getFamilyName();
            }
            data.put("url", url);
            data.put("nftName", nftName);
            data.put("familyName", familyName);
            dataList.add(data);

        }
        return dataList;

    }


    /**
     * 根据userId查询藏品交易记录
     *
     * @param userId
     * @return
     */
    public Map<String, Object> selectExchangeList(String userId) {
        Map<String, Object> result = new HashMap<>();

        //ExchangeTypeEnum:购买、转赠、售出
        ExchangeTypeEnum[] values = ExchangeTypeEnum.values();
        for (ExchangeTypeEnum exchangeTypeEnum : values) {
            ExchangeRecordsFilterMapper exchangeRecordsFilterMapper = new ExchangeRecordsFilterMapper();

            //购买记录userId为guestUserId
            //转赠、售出记录userId为hostUserId
            if (Objects.equals(exchangeTypeEnum.getKey(), ExchangeTypeEnum.BUY.getKey())) {
                exchangeRecordsFilterMapper.guestUserId = userId;
            } else {
                exchangeRecordsFilterMapper.hostUserId = userId;
            }

            //筛选出不同类型的藏品交易记录
            exchangeRecordsFilterMapper.type = exchangeTypeEnum.getKey();
            result.put(exchangeTypeEnum.getType(), exchangeListByType(exchangeRecordsFilterMapper));
        }

        return result;
    }

    /**
     * 转赠商品，即添加商品交易记录
     * todo 这儿有点问题
     *
     * @param exchangeRecordsPutMapper
     * @return
     */

    public boolean addExchangeRecordsPutMapper(ExchangeRecordsPutMapper exchangeRecordsPutMapper) {
        //修改藏品仓库
        WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
        wareHouseFilterMapper.userId = exchangeRecordsPutMapper.getHostUserId();
        wareHouseFilterMapper.metaNftId = exchangeRecordsPutMapper.getMetaNftId();
        WareHouse wareHouse = WareHouseFilterService.getListByFilter(wareHouseBaseService, wareHouseFilterMapper).get(0);
        wareHouse.setUserId(exchangeRecordsPutMapper.getGuestUserId());
        wareHouseBaseService.updateById(wareHouse);

        Integer price = new Integer(0);
        exchangeRecordsPutMapper.setPrice(price.doubleValue());
        exchangeRecordsPutMapper.setType(ExchangeTypeEnum.Donation.getKey());

        return exchangeRecordsBaseService.save(ExchangeRecordsPutMapper.convertToEntity(exchangeRecordsPutMapper));
    }

    /**
     * 根据userId查询邀请记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectInvitationList(String userId) {

        //筛选出本用户的邀请记录  被邀请人只有实名认证出来才行
        InvitationRecordsFilterMapper invitationRecordsFilterMapper = new InvitationRecordsFilterMapper();
        invitationRecordsFilterMapper.hostId = userId;
        invitationRecordsFilterMapper.isIdentified = 1;
        List<InvitationRecords> invitationRecordsList = InvitationRecordsFilterService
                .getListByFilter(invitationRecordsBaseService, invitationRecordsFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();
        for (InvitationRecords invitationRecords : invitationRecordsList) {

            //将邀请人和被邀请人封装为map
            Map<String, Object> data = new HashMap<>();
            String hostId = invitationRecords.getHostId();
            String invitedId = invitationRecords.getInvitedId();

            //获取邀请人和被邀请人的基本信息  邀请人是自己，用不着
            //Map<String, Object> hostMap = baseInfoService.userBaseInfoById(hostId);
            Map<String, Object> invitedMap = baseInfoService.userBaseInfoById(invitedId);

            data.put("invitationRecordsId", invitationRecords.getId());
            data.put("time", invitationRecords.getCreateTime());
            //data.put("host", hostMap);
            data.put("invited", invitedMap);
            result.add(data);
        }

        return result;

    }

    /**
     * 根据userId查询盲盒交易记录
     *
     * @param userId
     * @return
     */
    public Map<String, Object> selectExchangeBoxList(String userId) {
        Map<String, Object> result = new HashMap<>();

        //ExchangeTypeEnum:购买、转赠、售出
        ExchangeTypeEnum[] values = ExchangeTypeEnum.values();
        for (ExchangeTypeEnum exchangeTypeEnum : values) {
            ExchangeBoxRecordsFilterMapper exchangeBoxRecordsFilterMapper = new ExchangeBoxRecordsFilterMapper();

            //购买记录userId为guestUserId
            //转赠、售出记录userId为hostUserId
            if (Objects.equals(exchangeTypeEnum.getKey(), ExchangeTypeEnum.BUY.getKey())) {
                exchangeBoxRecordsFilterMapper.guestUserId = userId;
            } else {
                exchangeBoxRecordsFilterMapper.hostUserId = userId;
            }

            //筛选出不同类型的盲盒交易记录
            exchangeBoxRecordsFilterMapper.type = exchangeTypeEnum.getKey();
            result.put(exchangeTypeEnum.getType(), exchangeBoxListByType(exchangeBoxRecordsFilterMapper));
        }

        return result;
    }

    /**
     * 转赠盲盒，即添加盲盒交易记录
     *
     * @param exchangeBoxRecordsPutMapper
     * @return
     */

    public boolean addExchangeBoxRecordsPutMapper(ExchangeBoxRecordsPutMapper exchangeBoxRecordsPutMapper) {
        BoxHouseFilterMapper boxHouseFilterMapper = new BoxHouseFilterMapper();
        //修改赠送人盲盒仓库
        boxHouseFilterMapper.blindBoxId = exchangeBoxRecordsPutMapper.getBlindBoxId();
        boxHouseFilterMapper.userId = exchangeBoxRecordsPutMapper.getHostUserId();
        BoxHouse hostBoxHouse = BoxHouseFilterService.getListByFilter(boxHouseBaseService, boxHouseFilterMapper).get(0);
        hostBoxHouse.setCount(hostBoxHouse.getCount() - exchangeBoxRecordsPutMapper.getCount());
        boxHouseBaseService.updateById(hostBoxHouse);
        //TODO:: 如果剩下0个是否需要删除记录

        //查询受赠人仓库是否有这个盲盒
        boxHouseFilterMapper.userId = exchangeBoxRecordsPutMapper.getGuestUserId();
        BoxHouse guestBoxHouse = BoxHouseFilterService.getListByFilter(boxHouseBaseService, boxHouseFilterMapper).get(0);
        if (guestBoxHouse == null) {
            guestBoxHouse = new BoxHouse();
            guestBoxHouse.setBlindBoxId(exchangeBoxRecordsPutMapper.getBlindBoxId());
            guestBoxHouse.setUserId(exchangeBoxRecordsPutMapper.getGuestUserId());
            guestBoxHouse.setCount(exchangeBoxRecordsPutMapper.getCount());
            boxHouseBaseService.save(guestBoxHouse);
        } else {
            guestBoxHouse.setCount(guestBoxHouse.getCount() + exchangeBoxRecordsPutMapper.getCount());
            boxHouseBaseService.updateById(guestBoxHouse);
        }

        Integer price = new Integer(0);
        exchangeBoxRecordsPutMapper.setPrice(price.doubleValue());
        exchangeBoxRecordsPutMapper.setType(ExchangeTypeEnum.Donation.getKey());

        return exchangeBoxRecordsBaseService.save(ExchangeBoxRecordsPutMapper.convertToEntity(exchangeBoxRecordsPutMapper));
    }

    /**
     * 根据userId查询合成记录
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> selectPieceComList(String userId) {
        //删选出本用户的合成记录
        PieceComRecordsFilterMapper pieceComRecordsFilterMapper = new PieceComRecordsFilterMapper();
        pieceComRecordsFilterMapper.userId = userId;
        List<PieceComRecords> pieceComRecordsList = PieceComRecordsFilterService
                .getListByFilter(pieceComRecordsBaseService, pieceComRecordsFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();
        for (PieceComRecords pieceComRecords : pieceComRecordsList) {
            //将藏品信息和合成数量封装成map
            Map<String, Object> data = new HashMap<>();
            Integer count = pieceComRuleBaseService
                    .getById(pieceComRecords.getPieceRuleId())
                    .getCountNeed();

            //获取藏品的基本信息
            String metaNftId = pieceComRecords.getMetaNftId();
            Map<String, Object> nftMap = baseInfoService.nftBaseInfoById(metaNftId);

            data.put("pieceComRecordsId", pieceComRecords.getId());
            data.put("time", pieceComRecords.getCreateTime());
            data.put("count", count);
            data.put("metaNft", nftMap);

            result.add(data);

        }

        return result;
    }


    /**
     * 根据藏品交易类型查询记录并封装为map
     *
     * @param exchangeRecordsFilterMapper
     * @return
     */
    private List<Map<String, Object>> exchangeListByType(ExchangeRecordsFilterMapper exchangeRecordsFilterMapper) {
        List<ExchangeRecords> exchangeRecordsList = ExchangeRecordsFilterService
                .getListByFilter(exchangeRecordsBaseService, exchangeRecordsFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();
        for (ExchangeRecords exchangeRecords : exchangeRecordsList) {
            Map<String, Object> data = new HashMap<>();
            String metaNftId = exchangeRecords.getMetaNftId();
            String hostUserId = exchangeRecords.getHostUserId();
            String guestUserId = exchangeRecords.getGuestUserId();

            data.put("exchangeRecordsId", exchangeRecords.getId());
            data.put("time", exchangeRecords.getCreateTime());
            data.put("uuTag", exchangeRecords.getUuTag());
            data.put("metaNft", baseInfoService.nftBaseInfoById(metaNftId));
            data.put("host", baseInfoService.userBaseInfoById(hostUserId));
            data.put("guest", baseInfoService.userBaseInfoById(guestUserId));
            data.put("price", exchangeRecords.getPrice());

            result.add(data);
        }

        return result;
    }


    /**
     * 根据盲盒交易类型查询记录并封装为map
     *
     * @param exchangeBoxRecordsFilterMapper
     * @return
     */
    private List<Map<String, Object>> exchangeBoxListByType(ExchangeBoxRecordsFilterMapper exchangeBoxRecordsFilterMapper) {
        List<ExchangeBoxRecords> exchangeBoxRecordsList = ExchangeBoxRecordsFilterService
                .getListByFilter(exchangeBoxRecordsBaseService, exchangeBoxRecordsFilterMapper);

        List<Map<String, Object>> result = new ArrayList<>();
        for (ExchangeBoxRecords exchangeBoxRecords : exchangeBoxRecordsList) {
            Map<String, Object> data = new HashMap<>();
            String blindBoxId = exchangeBoxRecords.getBlindBoxId();
            String hostUserId = exchangeBoxRecords.getHostUserId();
            String guestUserId = exchangeBoxRecords.getGuestUserId();

            data.put("exchangeRecordsId", exchangeBoxRecords.getId());
            data.put("time", exchangeBoxRecords.getCreateTime());
            data.put("blindBox", baseInfoService.blindBoxInfoById(blindBoxId));
            data.put("host", baseInfoService.userBaseInfoById(hostUserId));
            data.put("guest", baseInfoService.userBaseInfoById(guestUserId));
            data.put("price", exchangeBoxRecords.getPrice());
            data.put("count", exchangeBoxRecords.getCount());

            result.add(data);
        }

        return result;
    }
}
