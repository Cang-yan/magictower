package com.yibingo.race.core.service;

import com.yibingo.race.common.validator.Assert;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.detailMapper.MarketDetailMapper;
import com.yibingo.race.dal.entity.BadgeNft;
import com.yibingo.race.dal.entity.BlindBoxPool;
import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.dal.entity.MetaNft;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.filterMapper.MarketFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Li XingHan
 * @date 2022-06-28 15:08
 */
@Service
public class ShopRetrieveService {

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private AuthorBaseService authorBaseService;

    @Autowired
    private FamilyBaseService familyBaseService;

    @Autowired
    private StockBaseService stockBaseService;

    @Autowired
    private RankSortBaseService rankSortBaseService;

    @Autowired
    private MarketBaseService marketBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;

    /**
     * 根据类型查询藏品
     * 默认nftType不为盲盒和徽章
     *
     * @param nftTypeEnum
     * @return
     */
    public List<Map<String, Object>> getMetaNftInShopByType(NftTypeEnum nftTypeEnum) {
        List<Map<String, Object>> dataMapList = new ArrayList<>();

        MarketFilterMapper marketFilterMapper = new MarketFilterMapper();
        marketFilterMapper.nftType = nftTypeEnum.getKey();
        List<Market> marketList = MarketFilterService.getListByFilter(marketBaseService, marketFilterMapper);
        if (marketList.isEmpty()) return new ArrayList<>();
        List<String> relationIdList = marketList.stream().map(Market::getRelationId).collect(Collectors.toList());
        List<MetaNft> metaNftList = metaNftBaseService.listByIds(relationIdList);
        for (Market market : marketList) {
            Map<String, Object> data = MarketDetailMapper.buildMap(market);
            data.put("createTime", market.getCreateTime());
            data.remove("version");
            data.remove("relationId");
            MetaNft metaNft = metaNftList.stream().filter(
                    item -> item.getId() != null && item.getId().equals(market.getRelationId())
            ).findFirst().orElse(new MetaNft());

            //nftBaseData只含metaNft和author信息，stock信息需另外加上
            Map<String, Object> nftBaseData = baseInfoService.nftInfoByEntity(metaNft);
            nftBaseData.put("stock", baseInfoService.stockBaseInfoById(metaNft.getStockId()));

            data.put("baseNft", nftBaseData);
            dataMapList.add(data);
        }
        return dataMapList;
    }


    /**
     * 上架的徽章
     *
     * @return
     */
    public List<Map<String, Object>> getBadgeInShop() {
        List<Map<String, Object>> dataMapList = new ArrayList<>();

        MarketFilterMapper marketFilterMapper = new MarketFilterMapper();
        marketFilterMapper.nftType = NftTypeEnum.BADGE.getKey();
        List<Market> marketList = MarketFilterService.getListByFilter(marketBaseService, marketFilterMapper);
        if (marketList.isEmpty()) return new ArrayList<>();

        List<String> relationIdList = marketList.stream().map(Market::getRelationId).collect(Collectors.toList());
        List<BadgeNft> badgeNftList = badgeNftBaseService.listByIds(relationIdList);
        for (Market market : marketList) {
            Map<String, Object> data = MarketDetailMapper.buildMap(market);
            data.put("createTime", market.getCreateTime());
            data.remove("version");
            data.remove("relationId");
            BadgeNft badgeNft = badgeNftList.stream().filter(
                    item -> item.getId() != null && item.getId().equals(market.getRelationId())
            ).findFirst().orElse(new BadgeNft());

            //badgeBaseData只含badge和author信息，stock信息需另外加上
            Map<String, Object> badgeBaseData = baseInfoService.badgeInfoByEntity(badgeNft);
            badgeBaseData.put("stock", badgeNft.getStockId());

            data.put("baseNft", badgeBaseData);
            dataMapList.add(data);
        }
        return dataMapList;

    }

    /**
     * 上架的盲盒
     *
     * @return
     */
    public List<Map<String, Object>> getBlindBoxInShop() {
        List<Map<String, Object>> dataMapList = new ArrayList<>();

        MarketFilterMapper marketFilterMapper = new MarketFilterMapper();
        marketFilterMapper.nftType = NftTypeEnum.BlindBox.getKey();
        List<Market> marketList = MarketFilterService.getListByFilter(marketBaseService, marketFilterMapper);
        if (marketList.isEmpty()) return new ArrayList<>();

        List<String> relationIdList = marketList.stream().map(Market::getRelationId).collect(Collectors.toList());
        List<BlindBoxPool> blindBoxPoolList = blindBoxPoolBaseService.listByIds(relationIdList);
        for (Market market : marketList) {
            Map<String, Object> data = MarketDetailMapper.buildMap(market);
            data.put("createTime", market.getCreateTime());
            data.remove("version");
            data.remove("relationId");

            BlindBoxPool blindBoxPool = blindBoxPoolList.stream().filter(
                    item -> item.getId() != null && item.getId().equals(market.getRelationId())
            ).findFirst().orElse(new BlindBoxPool());

            //blindBoxBaseData只含blindBox和author信息，stock信息需另外加上
            Map<String, Object> blindBoxBaseData = baseInfoService.blindBoxInfoByEntity(blindBoxPool);
            blindBoxBaseData.put("stock", baseInfoService.stockBaseInfoById(blindBoxPool.getStockId()));

            data.put("baseNft", blindBoxBaseData);
            dataMapList.add(data);
        }
        return dataMapList;
    }


    /**
     * 查询 英雄 徽章 装备 的详细信息
     *
     * @param marketId
     * @return
     */
    public Map<String, Object> getMetaNftInShopDetail(String marketId, NftTypeEnum nftTypeEnum) {
        Market market = marketBaseService.getById(marketId);
        Assert.isNull(market, "不存在此商品");
        Map<String, Object> data = MarketDetailMapper.buildMap(market);
        Map<String, Object> baseNft;
        String stockId;

        data.put("createTime", market.getCreateTime());
        data.remove("version");
        data.remove("relationId");
        if (nftTypeEnum.equals(NftTypeEnum.BlindBox)) {
            BlindBoxPool blindBoxPool = blindBoxPoolBaseService.getById(market.getRelationId());

            //blindBoxBaseData只含blindBox和author信息，stock信息需另外加上
            baseNft = baseInfoService.blindBoxInfoByEntity(blindBoxPool);
            stockId = blindBoxPool.getStockId();

        } else if (nftTypeEnum.equals(NftTypeEnum.BADGE)) {
            BadgeNft badgeNft = badgeNftBaseService.getById(market.getRelationId());
            //badgeBaseData只含badge和author信息，stock信息需另外加上
            baseNft = baseInfoService.badgeInfoByEntity(badgeNft);
            stockId = badgeNft.getStockId();

        } else {
            MetaNft metaNft = metaNftBaseService.getById(market.getRelationId());
            //nftBaseData只含metaNft和author信息，stock信息需另外加上
            baseNft = baseInfoService.nftInfoByEntity(metaNft);
            stockId = metaNft.getStockId();
        }
        baseNft.put("stock", baseInfoService.stockBaseInfoById(stockId));
        data.put("baseNft", baseNft);
        return data;

    }
}
