package com.yibingo.race.core.service;

import cn.hutool.core.util.StrUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.validator.Assert;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.detailMapper.*;
import com.yibingo.race.dal.entity.*;
import com.yibingo.race.dal.enums.NftTypeEnum;
import com.yibingo.race.dal.enums.ProfessionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-01 9:39
 */
@Service
public class BaseInfoService {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private MetaNftBaseService metaNftBaseService;

    @Autowired
    private FamilyBaseService familyBaseService;

    @Autowired
    private RankSortBaseService rankSortBaseService;

    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;

    @Autowired
    private AuthorBaseService authorBaseService;

    @Autowired
    private StockBaseService stockBaseService;

    @Autowired
    private BadgeNftBaseService badgeNftBaseService;


    /**
     * 根据userId获取用户的基本信息，账户id、姓名、头像、电话
     *
     * @param userId
     * @return
     */
    public Map<String, Object> userBaseInfoById(String userId) {
        Map<String, Object> data = new HashMap<>();

        User user = userBaseService.getById(userId);
        String accountId = user.getAccountId();
        String name = user.getName();
        String head = user.getHead();
        String phone = user.getPhone();
        String chainWallet = "";
        if (!StrUtil.isBlank(user.getChainwallet())) {
            chainWallet = user.getChainwallet().substring(0, 4) + "*****" + user.getChainwallet().substring(user.getChainwallet().length() - 4);
        } //String chainWallet = StrUtil.hide(user.getChainwallet(), 4, user.getChainwallet().length() - 4);
        //将账户id、姓名、头像、电话封装为map
        data.put("userId", userId);
        data.put("accountId", accountId);
        data.put("name", name);
        data.put("head", head);
        data.put("phone", phone);
        data.put("chainWallet", chainWallet);

        return data;
    }


    public Map<String, Object> userBaseInfoByEntity(User user) {
        Map<String, Object> data = new HashMap<>();


        String accountId = user.getAccountId();
        String name = user.getName();
        String head = user.getHead();
        String phone = user.getPhone();
        String chainWallet = "";
        if (!StrUtil.isBlank(user.getChainwallet())) {
            chainWallet = user.getChainwallet().substring(0, 4) + "*****" + user.getChainwallet().substring(user.getChainwallet().length() - 4);
        }

//        String chainWallet = StrUtil.hide(user.getChainwallet(), 4, user.getChainwallet().length() - 4);

        //将账户id、姓名、头像、电话封装为map
        data.put("userId", user.getId());
        data.put("accountId", accountId);
        data.put("name", name);
        data.put("head", head);
        data.put("phone", phone);
        data.put("chainWallet", chainWallet);

        //data.put("chainWallet", user.getChainwallet());

        return data;
    }

    /**
     * 根据nftId获取藏品的基本信息，名字、类型、图片url、系列名称、品质、合约地址、合约协议
     *
     * @param nftId
     * @return
     */
    public Map<String, Object> nftBaseInfoById(String nftId) {
        Map<String, Object> data = new HashMap<>();
        MetaNft metaNft = null;

        metaNft = metaNftBaseService.getById(nftId);
        Assert.isNull(metaNft, "不存在藏品信息");

        String nickname = metaNft.getNickname();
        String type = NftTypeEnum.getTypeByKey(metaNft.getType());
        String professionType = ProfessionTypeEnum.getTypeByKey(metaNft.getProfessionType());
        String url = metaNft.getUrl();
        String contractAddress = metaNft.getContractAddress();
        String contractAgreement = metaNft.getContractAgreement();
        String introduce = metaNft.getIntroduce();

        data.put("metaNftId", nftId);
        data.put("nickname", nickname);
        data.put("type", type);
        data.put("professionType", professionType);
        data.put("url", url);
        data.put("contractAddress", contractAddress);
        data.put("contractAgreement", contractAgreement);
        data.put("introduce", introduce);

        String familyTitle = "";
        //单图应该是不存在系列的
        if (metaNft.getFamilyId() != null) {
            familyTitle = familyBaseService.getById(metaNft.getFamilyId()).getTitle();

        }

        data.put("familyTitle", familyTitle);
        data.put("familyId", metaNft.getFamilyId());

        try {
            String rankName = rankSortBaseService.getById(metaNft.getRankId()).getName();
            data.put("rankName", rankName);
        } catch (Exception e) {
            throw new BaseException("不存在品质信息");
        }

        return data;
    }


    /**
     * 根据blindBox获取盲盒的基本信息：名称、图片url、系列名称
     *
     * @param blindBoxId
     * @return
     */
    public Map<String, Object> blindBoxInfoById(String blindBoxId) {
        Map<String, Object> data = new HashMap<>();

        BlindBoxPool blindBox = blindBoxPoolBaseService.getById(blindBoxId);

        String familyName = blindBox.getFamilyName();
        String blindBoxUrl = blindBox.getUrl();
        String nickname = blindBox.getNickname();
        String introduce = blindBox.getIntroduce();
        String contractAddress = blindBox.getContractAddress();
        String contractAgreement = blindBox.getContractAgreement();

        try {
            Author author = authorBaseService.getById(blindBox.getAuthorId());
            String authorId = author.getId();
            String authorName = author.getName();
            String authorHead = author.getHead();
            String authorIntroduce = author.getIntroduce();
            data.put("authorId", authorId);
            data.put("authorName", authorName);
            data.put("authorHead", authorHead);
            data.put("authorIntroduce", authorIntroduce);
        } catch (Exception e) {
            data.put("authorError", "作者信息不存在");
        }

        data.put("blindBoxId", blindBoxId);
        data.put("familyName", familyName);
        data.put("blindBoxUrl", blindBoxUrl);
        data.put("nickname", nickname);
        data.put("introduce", introduce);
        data.put("contractAddress", contractAddress);
        data.put("contractAgreement", contractAgreement);
        data.put("type", "");
        data.put("professionType", "");

        return data;

    }

    /**
     * 根据badgeId获取徽章的基本信息：名称、图片url、系列名称
     *
     * @param badgeId
     * @return
     */
    public Map<String, Object> badgeInfoById(String badgeId) {
        BadgeNft badgeNft = badgeNftBaseService.getById(badgeId);
        return badgeInfoByEntity(badgeNft);

    }

    /**
     * 根据authorId获取作者的基本信息：名字、头像、介绍
     *
     * @param authorId
     * @return
     */
    public Map<String, Object> authorInfoById(String authorId) {
        Map<String, Object> data = new HashMap<>();
        try {
            Author author = authorBaseService.getById(authorId);

            String name = author.getName();
            String head = author.getHead();
            String introduce = author.getIntroduce();

            data.put("authorId", authorId);
            data.put("name", name);
            data.put("head", head);
            data.put("introduce", introduce);
        } catch (Exception e) {
            data.put("authorError", "作者信息不存在");
        }

        return data;

    }

    /**
     * 根据实体类获取藏品的基本信息，名字、类型、图片url、系列名称、品质、合约地址、合约协议  作者  库存
     *
     * @param metaNft
     * @return
     */
    public Map<String, Object> nftInfoByEntity(MetaNft metaNft) {
        Map<String, Object> data = MetaNftDetailMapper.buildMap(metaNft);
        //bulidMap的id是boxId,为统一命名，需要将其更换成id
        data.remove("metaNftId");
        data.put("id", metaNft.getId());

        //type也要更换
        data.remove("metaNftType");
        data.put("type", NftTypeEnum.getTypeByKey(metaNft.getType()));
        data.remove("professionType", ProfessionTypeEnum.getTypeByKey(metaNft.getProfessionType()));

        //合约地址脱敏
        data.replace("contractAddress", StrUtil.hide(metaNft.getContractAddress(), 4, metaNft.getContractAddress().length() - 4));

        data.remove("authorId");
        if (!metaNft.getAuthorId().equals("root")) {
            Author author = authorBaseService.getById(metaNft.getAuthorId());
            data.put("author", AuthorDetailMapper.buildMap(author));
        } else {
            data.put("author", new Author());
        }

        data.remove("stockId");

        return data;

    }


    /**
     * 根据实体类获取盲盒的基本信息，名字、类型、图片url、系列名称、品质、合约地址、合约协议  作者  库存
     *
     * @param blindBoxPool
     * @return
     */
    public Map<String, Object> blindBoxInfoByEntity(BlindBoxPool blindBoxPool) {
        Map<String, Object> data = BlindBoxPoolDetailMapper.buildMap(blindBoxPool);
        //bulidMap的id是boxId,为统一命名，需要将其更换成id
        data.remove("blindBoxPoolId");
        data.put("id", blindBoxPool.getId());

        data.put("type", null);
        data.put("professionType", null);
        data.put("rankId", null);

        //合约地址脱敏
        data.replace("contractAddress", StrUtil.hide(blindBoxPool.getContractAddress(), 4, blindBoxPool.getContractAddress().length() - 4));

        data.remove("authorId");
        if (!blindBoxPool.getAuthorId().equals("root")) {
            Author author = authorBaseService.getById(blindBoxPool.getAuthorId());
            data.put("author", AuthorDetailMapper.buildMap(author));
        } else {
            data.put("author", new Author());
        }

        data.remove("stockId");

        return data;
    }

    /**
     * 根据实体类获取徽章的基本信息
     *
     * @param badgeNft
     * @return
     */
    public Map<String, Object> badgeInfoByEntity(BadgeNft badgeNft) {
        Map<String, Object> data = BadgeNftDetailMapper.buildMap(badgeNft);
        //bulidMap的id是boxId,为统一命名，需要将其更换成id
        data.remove("badgeNftId");
        data.put("id", badgeNft.getId());

        data.put("type", null);
        data.put("professionType", null);
        data.put("rankId", null);

        //合约地址脱敏
        data.replace("contractAddress", StrUtil.hide(badgeNft.getContractAddress(), 4, badgeNft.getContractAddress().length() - 4));

        data.remove("authorId");
        if (!badgeNft.getAuthorId().equals("root")) {
            Author author = authorBaseService.getById(badgeNft.getAuthorId());

            data.put("author", AuthorDetailMapper.buildMap(author));
        } else {
            data.put("author", AuthorDetailMapper.buildMap(new Author()));
        }

        data.remove("stockId");

        return data;

    }


    /**
     * 根据藏品仓库实体类获取仓库的基本信息
     *
     * @param wareHouse
     * @return
     */
    public Map<String, Object> wareHouseBaseInfoByEntity(WareHouse wareHouse) {
        Map<String, Object> data = WareHouseDetailMapper.buildMap(wareHouse);
        data.remove("metaNftId");
        data.put("updateTime", wareHouse.getUpdateTime());
        return data;
    }

    /**
     * 根据盲盒仓库实体类获取仓库的基本信息
     *
     * @param boxHouse
     * @return
     */
    public Map<String, Object> boxHouseBaseInfoByEntity(BoxHouse boxHouse) {
        Map<String, Object> data = BoxHouseDetailMapper.buildMap(boxHouse);
        data.remove("blindBoxId");
        data.put("updateTime", boxHouse.getUpdateTime());
        return data;
    }


    /**
     * 根据stockId获得stock的基本信息
     *
     * @param stockId
     * @return
     */
    public Map<String, Object> stockBaseInfoById(String stockId) {
        Stock stock = stockBaseService.getById(stockId);
        return StockDetailMapper.buildMap(stock);
    }


}
