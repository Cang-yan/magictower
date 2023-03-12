package com.yibingo.race.core.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yibingo.race.dal.entity.UserAuth;
import com.yibingo.race.dal.filterMapper.UserAuthFilterMapper;
import com.google.common.base.CaseFormat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户权限值扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-06-25 14:52:41
 */

public class UserAuthFilterService{

    public static List<UserAuth> getListByFilter(UserAuthBaseService userAuthBaseService,UserAuthFilterMapper filterMapper) {

        QueryWrapper<UserAuth> wrapper = new QueryWrapper<>();

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


        Long page = 1L;
        Long row = -1L;
        if (filterMapper.page != null) page = filterMapper.page;
        if (filterMapper.row != null) row = filterMapper.row;

        Page<UserAuth> markPage = new Page<>(page, row);

        Page<UserAuth> resultList = userAuthBaseService.page(markPage, wrapper);

        return resultList.getRecords();
    }



}