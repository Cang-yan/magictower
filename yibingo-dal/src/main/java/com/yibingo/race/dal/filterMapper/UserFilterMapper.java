package com.yibingo.race.dal.filterMapper;

import java.util.List;

/**
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-06-25 14:52:41
 */
public class UserFilterMapper{
    public Long updateTimeFrom = null;

    public Long updateTimeTo = null;


    public Long createTimeFrom = null;

    public Long createTimeTo = null;

    public List<String> orderBy = null;

    public Long page = null;

    public Long row = null;

    public String phone;

    public String accountId;

    public List<String> phoneIn;

    public String identity;
}