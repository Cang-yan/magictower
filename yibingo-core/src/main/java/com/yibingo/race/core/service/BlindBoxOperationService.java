package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.BlindBoxPool;
import com.yibingo.race.dal.entity.BoxHouse;
import com.yibingo.race.dal.entity.BoxMeta;
import com.yibingo.race.dal.entity.Stock;
import com.yibingo.race.dal.filterMapper.BoxHouseFilterMapper;
import com.yibingo.race.dal.putMapper.BlindBoxPoolPutMapper;
import com.yibingo.race.dal.putMapper.BoxMetaPutMapper;
import com.yibingo.race.dal.putMapper.StockPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/6/28 16:32
 */

@Service
public class BlindBoxOperationService {

    @Autowired
    BoxMetaBaseService boxMetaBaseService;
    @Autowired
    UserNftHouseService userNftHouseService;
    @Autowired
    BoxHouseBaseService boxHouseBaseService;
    @Autowired
    private BlindBoxPoolBaseService blindBoxPoolBaseService;
    @Autowired
    private StockBaseService stockBaseService;
    @Autowired
    private MetaNftBaseService metaNftBaseService;

    public class boxPoint {
        private Double ntfPoint;
        private String ntfId;

        public boxPoint(Double ntfPoint, String ntfId) {
            this.ntfId = ntfId;
            this.ntfPoint = ntfPoint;
        }
    }

    /**
     * 开启盲盒
     *
     * @param blindBoxId, userId
     * @return
     */
    public void openBlindBox(String blindBoxId, String userId) {
        Double tempPoint = 0d;
        List<boxPoint> pointList = new ArrayList<>();

        //查询要开启的盲盒
        List<BoxMeta> boxMetaList = new ArrayList<>();
        List<BoxMeta> allBoxMetaList = new ArrayList<>();
        allBoxMetaList = boxMetaBaseService.list();
        for (BoxMeta b : allBoxMetaList) {
            if (Objects.equals(b.getBlindBoxId(), blindBoxId)) {
                boxMetaList.add(b);
            }
        }
        //构造抽奖区间
        for (BoxMeta b : boxMetaList) {
            tempPoint += b.getRealPossibility();
            pointList.add(new boxPoint(tempPoint, b.getMetaNftId()));
        }
        //生成随机数来抽奖
        String resultNtfId = null;//   这里可能有报空的风险
        boolean stockReserve = false;
        while(!stockReserve) {
            Random r = new Random();
            Double d = r.nextDouble() * tempPoint;

            while (d == 0d) {
                d = r.nextDouble() * tempPoint;
            }
            for (boxPoint b : pointList) {
                if (b.ntfPoint >= d) {
                    resultNtfId = b.ntfId;
                }
            }
            //检查是否有库存
            Stock stock = stockBaseService.getById(metaNftBaseService.getById(resultNtfId));
            if(stock.getNowCount() != 0)
            {
                //修改盲盒库存
                stock.setNowCount(stock.getPreCount() - 1);
                stockReserve = true;
            }
        }
        //下发盲盒奖品
        userNftHouseService.addWardHouse(resultNtfId, userId);
        //修改盲盒状态
        BoxHouseFilterMapper boxHouseFilterMapper = new BoxHouseFilterMapper();
        boxHouseFilterMapper.blindBoxId = blindBoxId;
        BoxHouse boxHouse = BoxHouseFilterService.getListByFilter(boxHouseBaseService, boxHouseFilterMapper).get(0);
        //boxHouse.setIsOpen(1);//1是已开吗？
        boxHouseBaseService.updateById(boxHouse);
    }


    //TODO 上传盲盒
    public void createBlindBox(BlindBoxPoolPutMapper blindBoxPoolPutMapper, Integer totalCount, Integer reverseCount) {
        BlindBoxPool blindBoxPool = BlindBoxPoolPutMapper.convertToEntity(blindBoxPoolPutMapper);

        setStock(blindBoxPool, totalCount, reverseCount);
        blindBoxPoolBaseService.save(blindBoxPool);



    }


    /**
     * 设置新增盲盒的库存
     *
     * @param blindBoxPool
     * @param totalCount
     * @param reverseCount
     */
    private void setStock(BlindBoxPool blindBoxPool, Integer totalCount, Integer reverseCount) {
        //根据count设置库存初始值
        StockPutMapper stockPutMapper = new StockPutMapper();
        stockPutMapper.setPreCount(totalCount);
        stockPutMapper.setReserveCount(reverseCount);
        stockPutMapper.setNowCount(totalCount - reverseCount);

        //将库存与藏品关联
        Stock stock = StockPutMapper.convertToEntity(stockPutMapper);
        stockBaseService.save(stock);
        blindBoxPool.setStockId(stock.getId());
    }

}
