package com.yibingo.race.core.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yibingo.race.dal.entity.LotteryJoin;
import com.yibingo.race.dal.filterMapper.LotteryJoinFilterMapper;
import com.google.common.base.CaseFormat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抽奖参与人员;扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */

public class LotteryJoinFilterService{

    public static List<LotteryJoin> getListByFilter(LotteryJoinBaseService lotteryJoinBaseService,LotteryJoinFilterMapper filterMapper) {

        QueryWrapper<LotteryJoin> wrapper = new QueryWrapper<>();

        if (filterMapper.orderBy != null) {
            for (String orderBy : filterMapper.orderBy) {
                int desc = orderBy.indexOf("desc");
                int asc = orderBy.indexOf("asc");
                if (desc != -1 && asc == -1) {
                    wrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy.substring(0, desc - 1)));
                }
                if (desc == -1 && asc != -1) {
                    wrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy.substring(0, asc - 1)));
                }
            }
        } else {
            wrapper.orderByDesc("create_time");
        }

        if (filterMapper.updateTimeFrom != null) wrapper.ge("update_time" , new Date(filterMapper.updateTimeFrom));
        if (filterMapper.updateTimeTo != null) wrapper.le("update_time" , new Date(filterMapper.updateTimeTo));
        if (filterMapper.createTimeFrom != null) wrapper.ge("create_time" , new Date(filterMapper.createTimeFrom));
        if (filterMapper.createTimeTo != null) wrapper.le("create_time" , new Date(filterMapper.createTimeTo));
        if (filterMapper.userId !=null) wrapper.eq("user_id",filterMapper.userId);

        Long page = 1L;
        Long row = -1L;
        if (filterMapper.page != null) page = filterMapper.page;
        if (filterMapper.row != null) row = filterMapper.row;

        Page<LotteryJoin> markPage = new Page<>(page, row);

        Page<LotteryJoin> resultList = lotteryJoinBaseService.page(markPage, wrapper);

        return resultList.getRecords();
    }



}