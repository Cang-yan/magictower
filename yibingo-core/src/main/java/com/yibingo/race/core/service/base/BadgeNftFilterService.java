package com.yibingo.race.core.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.yibingo.race.dal.entity.BadgeNft;
import com.yibingo.race.dal.filterMapper.BadgeNftFilterMapper;

import java.util.Date;
import java.util.List;

/**
 * 徽章藏品扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-10 14:59:32
 */

public class BadgeNftFilterService {

    public static List<BadgeNft> getListByFilter(BadgeNftBaseService badgeNftBaseService, BadgeNftFilterMapper filterMapper) {

        QueryWrapper<BadgeNft> wrapper = new QueryWrapper<>();

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
        if (filterMapper.familyId != null) wrapper.eq("family_id", filterMapper.familyId);

        Long page = 1L;
        Long row = -1L;
        if (filterMapper.page != null) page = filterMapper.page;
        if (filterMapper.row != null) row = filterMapper.row;

        Page<BadgeNft> markPage = new Page<>(page, row);

        Page<BadgeNft> resultList = badgeNftBaseService.page(markPage, wrapper);

        return resultList.getRecords();
    }


}