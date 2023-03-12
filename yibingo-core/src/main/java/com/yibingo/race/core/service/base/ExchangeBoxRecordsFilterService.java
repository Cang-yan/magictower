package com.yibingo.race.core.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yibingo.race.dal.entity.ExchangeBoxRecords;
import com.yibingo.race.dal.filterMapper.ExchangeBoxRecordsFilterMapper;
import com.google.common.base.CaseFormat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 盲盒购买和抽取记录扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-06-25 14:52:42
 */

public class ExchangeBoxRecordsFilterService {

    public static List<ExchangeBoxRecords> getListByFilter(ExchangeBoxRecordsBaseService exchangeBoxRecordsBaseService, ExchangeBoxRecordsFilterMapper filterMapper) {

        QueryWrapper<ExchangeBoxRecords> wrapper = new QueryWrapper<>();

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

        if (filterMapper.updateTimeFrom != null) wrapper.ge("update_time", new Date(filterMapper.updateTimeFrom));
        if (filterMapper.updateTimeTo != null) wrapper.le("update_time", new Date(filterMapper.updateTimeTo));
        if (filterMapper.createTimeFrom != null) wrapper.ge("create_time", new Date(filterMapper.createTimeFrom));
        if (filterMapper.createTimeTo != null) wrapper.le("create_time", new Date(filterMapper.createTimeTo));
        if (filterMapper.guestUserId != null) wrapper.eq("guest_user_id", filterMapper.guestUserId);
        if (filterMapper.hostUserId != null) wrapper.eq("host_user_id", filterMapper.hostUserId);
        if (filterMapper.type != null) wrapper.eq("type", filterMapper.type);


        Long page = 1L;
        Long row = -1L;
        if (filterMapper.page != null) page = filterMapper.page;
        if (filterMapper.row != null) row = filterMapper.row;

        Page<ExchangeBoxRecords> markPage = new Page<>(page, row);

        Page<ExchangeBoxRecords> resultList = exchangeBoxRecordsBaseService.page(markPage, wrapper);

        return resultList.getRecords();
    }


}