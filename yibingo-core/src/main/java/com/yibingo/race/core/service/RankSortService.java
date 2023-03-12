package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.RankSortBaseService;
import com.yibingo.race.core.service.base.RankSortFilterService;
import com.yibingo.race.dal.entity.RankSort;
import com.yibingo.race.dal.filterMapper.RankSortFilterMapper;
import com.yibingo.race.dal.putMapper.RankSortPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Li XingHan
 * @date 2022-07-04 10:19
 */
@Service
public class RankSortService {

    @Autowired
    private RankSortBaseService rankSortBaseService;

    /**
     * 查询所有的品质分级
     *
     * @return
     */
    public List<Map<String, Object>> selectRankSortList() {

        int count = rankSortBaseService.count();

        //查询所有的品质分级rank_sort列表并根据qualityCode进行排序
        List<RankSort> rankSortList = rankSortBaseService.list();
        rankSortList.sort(Comparator.comparing(RankSort::getQualityCode));

//        RankSortFilterMapper rankSortFilterMapper = new RankSortFilterMapper();
//        rankSortFilterMapper.orderBy = Collections.singletonList("quality_code asc");
//        List<RankSort> rankSortList1 = RankSortFilterService.getListByFilter(rankSortBaseService,rankSortFilterMapper);
//
        List<Map<String, Object>> result = new ArrayList<>();

        //将每个rank_sort封装成map，map中包含的信息有品质名字、品质代号
        for (RankSort rankSort : rankSortList) {
            Map<String, Object> data = new HashMap<>();

            data.put("id", rankSort.getId());
            data.put("name", rankSort.getName());
            data.put("qualityCode", rankSort.getQualityCode());

            result.add(data);
        }

        return result;
    }


    /**
     * 根据name和qualityCode创建品质分级
     *
     * @param rankSortPutMapper
     */
    public void createRankSort(RankSortPutMapper rankSortPutMapper) {
        RankSort rankSort = RankSortPutMapper.convertToEntity(rankSortPutMapper);

        rankSortBaseService.save(rankSort);
    }


    /**
     * 更新品质列表
     *
     * @param rankSortList
     */
    public void updateRankSort(List<Map<String, Object>> rankSortList){

        List<RankSort> newRankSort = new ArrayList<>();

        //传进来的参数是Map，提取其中的id、name、qualityCode
        for (Map<String, Object> data:rankSortList){
            String id = data.get("id").toString();
            String name = data.get("name").toString();
            Integer qualityCode = (Integer)data.get("qualityCode");

            RankSort rankSort = new RankSort();
            rankSort.setId(id);
            rankSort.setName(name);
            rankSort.setQualityCode(qualityCode);

            newRankSort.add(rankSort);
        }

        rankSortBaseService.updateBatchById(newRankSort);
    }


    /**
     * 品质分级的总数
     *
     * @return
     */
    public int countRankSort(){
        return rankSortBaseService.count();
    }
}
